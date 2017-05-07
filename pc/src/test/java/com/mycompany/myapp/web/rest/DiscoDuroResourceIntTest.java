package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PcApp;

import com.mycompany.myapp.domain.DiscoDuro;
import com.mycompany.myapp.repository.DiscoDuroRepository;
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
 * Test class for the DiscoDuroResource REST controller.
 *
 * @see DiscoDuroResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PcApp.class)
public class DiscoDuroResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CAPACIDAD = "AAAAAAAAAA";
    private static final String UPDATED_CAPACIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_TAMANO = "AAAAAAAAAA";
    private static final String UPDATED_TAMANO = "BBBBBBBBBB";

    private static final String DEFAULT_TIEMPO_ACCESO = "AAAAAAAAAA";
    private static final String UPDATED_TIEMPO_ACCESO = "BBBBBBBBBB";

    private static final String DEFAULT_TASA_TRANSFERENCIA = "AAAAAAAAAA";
    private static final String UPDATED_TASA_TRANSFERENCIA = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    @Autowired
    private DiscoDuroRepository discoDuroRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDiscoDuroMockMvc;

    private DiscoDuro discoDuro;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DiscoDuroResource discoDuroResource = new DiscoDuroResource(discoDuroRepository);
        this.restDiscoDuroMockMvc = MockMvcBuilders.standaloneSetup(discoDuroResource)
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
    public static DiscoDuro createEntity(EntityManager em) {
        DiscoDuro discoDuro = new DiscoDuro()
            .nombre(DEFAULT_NOMBRE)
            .capacidad(DEFAULT_CAPACIDAD)
            .tamano(DEFAULT_TAMANO)
            .tiempoAcceso(DEFAULT_TIEMPO_ACCESO)
            .tasaTransferencia(DEFAULT_TASA_TRANSFERENCIA)
            .precio(DEFAULT_PRECIO);
        return discoDuro;
    }

    @Before
    public void initTest() {
        discoDuro = createEntity(em);
    }

    @Test
    @Transactional
    public void createDiscoDuro() throws Exception {
        int databaseSizeBeforeCreate = discoDuroRepository.findAll().size();

        // Create the DiscoDuro
        restDiscoDuroMockMvc.perform(post("/api/disco-duros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discoDuro)))
            .andExpect(status().isCreated());

        // Validate the DiscoDuro in the database
        List<DiscoDuro> discoDuroList = discoDuroRepository.findAll();
        assertThat(discoDuroList).hasSize(databaseSizeBeforeCreate + 1);
        DiscoDuro testDiscoDuro = discoDuroList.get(discoDuroList.size() - 1);
        assertThat(testDiscoDuro.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testDiscoDuro.getCapacidad()).isEqualTo(DEFAULT_CAPACIDAD);
        assertThat(testDiscoDuro.getTamano()).isEqualTo(DEFAULT_TAMANO);
        assertThat(testDiscoDuro.getTiempoAcceso()).isEqualTo(DEFAULT_TIEMPO_ACCESO);
        assertThat(testDiscoDuro.getTasaTransferencia()).isEqualTo(DEFAULT_TASA_TRANSFERENCIA);
        assertThat(testDiscoDuro.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createDiscoDuroWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = discoDuroRepository.findAll().size();

        // Create the DiscoDuro with an existing ID
        discoDuro.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDiscoDuroMockMvc.perform(post("/api/disco-duros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discoDuro)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DiscoDuro> discoDuroList = discoDuroRepository.findAll();
        assertThat(discoDuroList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDiscoDuros() throws Exception {
        // Initialize the database
        discoDuroRepository.saveAndFlush(discoDuro);

        // Get all the discoDuroList
        restDiscoDuroMockMvc.perform(get("/api/disco-duros?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(discoDuro.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].capacidad").value(hasItem(DEFAULT_CAPACIDAD.toString())))
            .andExpect(jsonPath("$.[*].tamano").value(hasItem(DEFAULT_TAMANO.toString())))
            .andExpect(jsonPath("$.[*].tiempoAcceso").value(hasItem(DEFAULT_TIEMPO_ACCESO.toString())))
            .andExpect(jsonPath("$.[*].tasaTransferencia").value(hasItem(DEFAULT_TASA_TRANSFERENCIA.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    public void getDiscoDuro() throws Exception {
        // Initialize the database
        discoDuroRepository.saveAndFlush(discoDuro);

        // Get the discoDuro
        restDiscoDuroMockMvc.perform(get("/api/disco-duros/{id}", discoDuro.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(discoDuro.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.capacidad").value(DEFAULT_CAPACIDAD.toString()))
            .andExpect(jsonPath("$.tamano").value(DEFAULT_TAMANO.toString()))
            .andExpect(jsonPath("$.tiempoAcceso").value(DEFAULT_TIEMPO_ACCESO.toString()))
            .andExpect(jsonPath("$.tasaTransferencia").value(DEFAULT_TASA_TRANSFERENCIA.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDiscoDuro() throws Exception {
        // Get the discoDuro
        restDiscoDuroMockMvc.perform(get("/api/disco-duros/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDiscoDuro() throws Exception {
        // Initialize the database
        discoDuroRepository.saveAndFlush(discoDuro);
        int databaseSizeBeforeUpdate = discoDuroRepository.findAll().size();

        // Update the discoDuro
        DiscoDuro updatedDiscoDuro = discoDuroRepository.findOne(discoDuro.getId());
        updatedDiscoDuro
            .nombre(UPDATED_NOMBRE)
            .capacidad(UPDATED_CAPACIDAD)
            .tamano(UPDATED_TAMANO)
            .tiempoAcceso(UPDATED_TIEMPO_ACCESO)
            .tasaTransferencia(UPDATED_TASA_TRANSFERENCIA)
            .precio(UPDATED_PRECIO);

        restDiscoDuroMockMvc.perform(put("/api/disco-duros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDiscoDuro)))
            .andExpect(status().isOk());

        // Validate the DiscoDuro in the database
        List<DiscoDuro> discoDuroList = discoDuroRepository.findAll();
        assertThat(discoDuroList).hasSize(databaseSizeBeforeUpdate);
        DiscoDuro testDiscoDuro = discoDuroList.get(discoDuroList.size() - 1);
        assertThat(testDiscoDuro.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testDiscoDuro.getCapacidad()).isEqualTo(UPDATED_CAPACIDAD);
        assertThat(testDiscoDuro.getTamano()).isEqualTo(UPDATED_TAMANO);
        assertThat(testDiscoDuro.getTiempoAcceso()).isEqualTo(UPDATED_TIEMPO_ACCESO);
        assertThat(testDiscoDuro.getTasaTransferencia()).isEqualTo(UPDATED_TASA_TRANSFERENCIA);
        assertThat(testDiscoDuro.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingDiscoDuro() throws Exception {
        int databaseSizeBeforeUpdate = discoDuroRepository.findAll().size();

        // Create the DiscoDuro

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDiscoDuroMockMvc.perform(put("/api/disco-duros")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(discoDuro)))
            .andExpect(status().isCreated());

        // Validate the DiscoDuro in the database
        List<DiscoDuro> discoDuroList = discoDuroRepository.findAll();
        assertThat(discoDuroList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDiscoDuro() throws Exception {
        // Initialize the database
        discoDuroRepository.saveAndFlush(discoDuro);
        int databaseSizeBeforeDelete = discoDuroRepository.findAll().size();

        // Get the discoDuro
        restDiscoDuroMockMvc.perform(delete("/api/disco-duros/{id}", discoDuro.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DiscoDuro> discoDuroList = discoDuroRepository.findAll();
        assertThat(discoDuroList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DiscoDuro.class);
    }
}
