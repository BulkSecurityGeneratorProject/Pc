package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PcApp;

import com.mycompany.myapp.domain.Optico;
import com.mycompany.myapp.repository.OpticoRepository;
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

/**
 * Test class for the OpticoResource REST controller.
 *
 * @see OpticoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PcApp.class)
public class OpticoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_VELOCIDAD_ESCRITURA = "AAAAAAAAAA";
    private static final String UPDATED_VELOCIDAD_ESCRITURA = "BBBBBBBBBB";

    private static final String DEFAULT_VELOCIDAD_LECTURA = "AAAAAAAAAA";
    private static final String UPDATED_VELOCIDAD_LECTURA = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    @Autowired
    private OpticoRepository opticoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOpticoMockMvc;

    private Optico optico;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OpticoResource opticoResource = new OpticoResource(opticoRepository);
        this.restOpticoMockMvc = MockMvcBuilders.standaloneSetup(opticoResource)
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
    public static Optico createEntity(EntityManager em) {
        Optico optico = new Optico()
            .nombre(DEFAULT_NOMBRE)
            .velocidadEscritura(DEFAULT_VELOCIDAD_ESCRITURA)
            .velocidadLectura(DEFAULT_VELOCIDAD_LECTURA)
            .precio(DEFAULT_PRECIO);
        return optico;
    }

    @Before
    public void initTest() {
        optico = createEntity(em);
    }

    @Test
    @Transactional
    public void createOptico() throws Exception {
        int databaseSizeBeforeCreate = opticoRepository.findAll().size();

        // Create the Optico
        restOpticoMockMvc.perform(post("/api/opticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optico)))
            .andExpect(status().isCreated());

        // Validate the Optico in the database
        List<Optico> opticoList = opticoRepository.findAll();
        assertThat(opticoList).hasSize(databaseSizeBeforeCreate + 1);
        Optico testOptico = opticoList.get(opticoList.size() - 1);
        assertThat(testOptico.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testOptico.getVelocidadEscritura()).isEqualTo(DEFAULT_VELOCIDAD_ESCRITURA);
        assertThat(testOptico.getVelocidadLectura()).isEqualTo(DEFAULT_VELOCIDAD_LECTURA);
        assertThat(testOptico.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createOpticoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = opticoRepository.findAll().size();

        // Create the Optico with an existing ID
        optico.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOpticoMockMvc.perform(post("/api/opticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optico)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Optico> opticoList = opticoRepository.findAll();
        assertThat(opticoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOpticos() throws Exception {
        // Initialize the database
        opticoRepository.saveAndFlush(optico);

        // Get all the opticoList
        restOpticoMockMvc.perform(get("/api/opticos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optico.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].velocidadEscritura").value(hasItem(DEFAULT_VELOCIDAD_ESCRITURA.toString())))
            .andExpect(jsonPath("$.[*].velocidadLectura").value(hasItem(DEFAULT_VELOCIDAD_LECTURA.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    public void getOptico() throws Exception {
        // Initialize the database
        opticoRepository.saveAndFlush(optico);

        // Get the optico
        restOpticoMockMvc.perform(get("/api/opticos/{id}", optico.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(optico.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.velocidadEscritura").value(DEFAULT_VELOCIDAD_ESCRITURA.toString()))
            .andExpect(jsonPath("$.velocidadLectura").value(DEFAULT_VELOCIDAD_LECTURA.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOptico() throws Exception {
        // Get the optico
        restOpticoMockMvc.perform(get("/api/opticos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOptico() throws Exception {
        // Initialize the database
        opticoRepository.saveAndFlush(optico);
        int databaseSizeBeforeUpdate = opticoRepository.findAll().size();

        // Update the optico
        Optico updatedOptico = opticoRepository.findOne(optico.getId());
        updatedOptico
            .nombre(UPDATED_NOMBRE)
            .velocidadEscritura(UPDATED_VELOCIDAD_ESCRITURA)
            .velocidadLectura(UPDATED_VELOCIDAD_LECTURA)
            .precio(UPDATED_PRECIO);

        restOpticoMockMvc.perform(put("/api/opticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOptico)))
            .andExpect(status().isOk());

        // Validate the Optico in the database
        List<Optico> opticoList = opticoRepository.findAll();
        assertThat(opticoList).hasSize(databaseSizeBeforeUpdate);
        Optico testOptico = opticoList.get(opticoList.size() - 1);
        assertThat(testOptico.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testOptico.getVelocidadEscritura()).isEqualTo(UPDATED_VELOCIDAD_ESCRITURA);
        assertThat(testOptico.getVelocidadLectura()).isEqualTo(UPDATED_VELOCIDAD_LECTURA);
        assertThat(testOptico.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingOptico() throws Exception {
        int databaseSizeBeforeUpdate = opticoRepository.findAll().size();

        // Create the Optico

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOpticoMockMvc.perform(put("/api/opticos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(optico)))
            .andExpect(status().isCreated());

        // Validate the Optico in the database
        List<Optico> opticoList = opticoRepository.findAll();
        assertThat(opticoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOptico() throws Exception {
        // Initialize the database
        opticoRepository.saveAndFlush(optico);
        int databaseSizeBeforeDelete = opticoRepository.findAll().size();

        // Get the optico
        restOpticoMockMvc.perform(delete("/api/opticos/{id}", optico.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Optico> opticoList = opticoRepository.findAll();
        assertThat(opticoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Optico.class);
    }
}
