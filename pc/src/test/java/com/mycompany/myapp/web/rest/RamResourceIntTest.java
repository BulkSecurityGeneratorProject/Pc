package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PcApp;

import com.mycompany.myapp.domain.Ram;
import com.mycompany.myapp.repository.RamRepository;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Zocalo;
/**
 * Test class for the RamResource REST controller.
 *
 * @see RamResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PcApp.class)
public class RamResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CAPACIDAD = "AAAAAAAAAA";
    private static final String UPDATED_CAPACIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_ANCHO_DANDA = "AAAAAAAAAA";
    private static final String UPDATED_ANCHO_DANDA = "BBBBBBBBBB";

    private static final String DEFAULT_FRECUENCIA = "AAAAAAAAAA";
    private static final String UPDATED_FRECUENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_TIEMPO_ACCESO = "AAAAAAAAAA";
    private static final String UPDATED_TIEMPO_ACCESO = "BBBBBBBBBB";

    private static final String DEFAULT_LATENCIA = "AAAAAAAAAA";
    private static final String UPDATED_LATENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_CONECTORES = "AAAAAAAAAA";
    private static final String UPDATED_CONECTORES = "BBBBBBBBBB";

    private static final Zocalo DEFAULT_ZOCALO = Zocalo.DIP;
    private static final Zocalo UPDATED_ZOCALO = Zocalo.SIPP;

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    @Autowired
    private RamRepository ramRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRamMockMvc;

    private Ram ram;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        RamResource ramResource = new RamResource(ramRepository);
        this.restRamMockMvc = MockMvcBuilders.standaloneSetup(ramResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ram createEntity(EntityManager em) {
        Ram ram = new Ram()
            .nombre(DEFAULT_NOMBRE)
            .capacidad(DEFAULT_CAPACIDAD)
            .anchoDanda(DEFAULT_ANCHO_DANDA)
            .frecuencia(DEFAULT_FRECUENCIA)
            .tiempoAcceso(DEFAULT_TIEMPO_ACCESO)
            .latencia(DEFAULT_LATENCIA)
            .conectores(DEFAULT_CONECTORES)
            .zocalo(DEFAULT_ZOCALO)
            .precio(DEFAULT_PRECIO);
        return ram;
    }

    @Before
    public void initTest() {
        ram = createEntity(em);
    }

    @Test
    @Transactional
    public void createRam() throws Exception {
        int databaseSizeBeforeCreate = ramRepository.findAll().size();

        // Create the Ram
        restRamMockMvc.perform(post("/api/rams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ram)))
            .andExpect(status().isCreated());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeCreate + 1);
        Ram testRam = ramList.get(ramList.size() - 1);
        assertThat(testRam.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testRam.getCapacidad()).isEqualTo(DEFAULT_CAPACIDAD);
        assertThat(testRam.getAnchoDanda()).isEqualTo(DEFAULT_ANCHO_DANDA);
        assertThat(testRam.getFrecuencia()).isEqualTo(DEFAULT_FRECUENCIA);
        assertThat(testRam.getTiempoAcceso()).isEqualTo(DEFAULT_TIEMPO_ACCESO);
        assertThat(testRam.getLatencia()).isEqualTo(DEFAULT_LATENCIA);
        assertThat(testRam.getConectores()).isEqualTo(DEFAULT_CONECTORES);
        assertThat(testRam.getZocalo()).isEqualTo(DEFAULT_ZOCALO);
        assertThat(testRam.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createRamWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ramRepository.findAll().size();

        // Create the Ram with an existing ID
        ram.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRamMockMvc.perform(post("/api/rams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ram)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRams() throws Exception {
        // Initialize the database
        ramRepository.saveAndFlush(ram);

        // Get all the ramList
        restRamMockMvc.perform(get("/api/rams?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ram.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].capacidad").value(hasItem(DEFAULT_CAPACIDAD.toString())))
            .andExpect(jsonPath("$.[*].anchoDanda").value(hasItem(DEFAULT_ANCHO_DANDA.toString())))
            .andExpect(jsonPath("$.[*].frecuencia").value(hasItem(DEFAULT_FRECUENCIA.toString())))
            .andExpect(jsonPath("$.[*].tiempoAcceso").value(hasItem(DEFAULT_TIEMPO_ACCESO.toString())))
            .andExpect(jsonPath("$.[*].latencia").value(hasItem(DEFAULT_LATENCIA.toString())))
            .andExpect(jsonPath("$.[*].conectores").value(hasItem(DEFAULT_CONECTORES.toString())))
            .andExpect(jsonPath("$.[*].zocalo").value(hasItem(DEFAULT_ZOCALO.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    public void getRam() throws Exception {
        // Initialize the database
        ramRepository.saveAndFlush(ram);

        // Get the ram
        restRamMockMvc.perform(get("/api/rams/{id}", ram.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ram.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.capacidad").value(DEFAULT_CAPACIDAD.toString()))
            .andExpect(jsonPath("$.anchoDanda").value(DEFAULT_ANCHO_DANDA.toString()))
            .andExpect(jsonPath("$.frecuencia").value(DEFAULT_FRECUENCIA.toString()))
            .andExpect(jsonPath("$.tiempoAcceso").value(DEFAULT_TIEMPO_ACCESO.toString()))
            .andExpect(jsonPath("$.latencia").value(DEFAULT_LATENCIA.toString()))
            .andExpect(jsonPath("$.conectores").value(DEFAULT_CONECTORES.toString()))
            .andExpect(jsonPath("$.zocalo").value(DEFAULT_ZOCALO.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRam() throws Exception {
        // Get the ram
        restRamMockMvc.perform(get("/api/rams/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRam() throws Exception {
        // Initialize the database
        ramRepository.saveAndFlush(ram);
        int databaseSizeBeforeUpdate = ramRepository.findAll().size();

        // Update the ram
        Ram updatedRam = ramRepository.findOne(ram.getId());
        updatedRam
            .nombre(UPDATED_NOMBRE)
            .capacidad(UPDATED_CAPACIDAD)
            .anchoDanda(UPDATED_ANCHO_DANDA)
            .frecuencia(UPDATED_FRECUENCIA)
            .tiempoAcceso(UPDATED_TIEMPO_ACCESO)
            .latencia(UPDATED_LATENCIA)
            .conectores(UPDATED_CONECTORES)
            .zocalo(UPDATED_ZOCALO)
            .precio(UPDATED_PRECIO);

        restRamMockMvc.perform(put("/api/rams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRam)))
            .andExpect(status().isOk());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeUpdate);
        Ram testRam = ramList.get(ramList.size() - 1);
        assertThat(testRam.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testRam.getCapacidad()).isEqualTo(UPDATED_CAPACIDAD);
        assertThat(testRam.getAnchoDanda()).isEqualTo(UPDATED_ANCHO_DANDA);
        assertThat(testRam.getFrecuencia()).isEqualTo(UPDATED_FRECUENCIA);
        assertThat(testRam.getTiempoAcceso()).isEqualTo(UPDATED_TIEMPO_ACCESO);
        assertThat(testRam.getLatencia()).isEqualTo(UPDATED_LATENCIA);
        assertThat(testRam.getConectores()).isEqualTo(UPDATED_CONECTORES);
        assertThat(testRam.getZocalo()).isEqualTo(UPDATED_ZOCALO);
        assertThat(testRam.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingRam() throws Exception {
        int databaseSizeBeforeUpdate = ramRepository.findAll().size();

        // Create the Ram

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRamMockMvc.perform(put("/api/rams")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ram)))
            .andExpect(status().isCreated());

        // Validate the Ram in the database
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRam() throws Exception {
        // Initialize the database
        ramRepository.saveAndFlush(ram);
        int databaseSizeBeforeDelete = ramRepository.findAll().size();

        // Get the ram
        restRamMockMvc.perform(delete("/api/rams/{id}", ram.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ram> ramList = ramRepository.findAll();
        assertThat(ramList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ram.class);
    }
}
