package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PcApp;

import com.mycompany.myapp.domain.Alimentacion;
import com.mycompany.myapp.repository.AlimentacionRepository;
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
 * Test class for the AlimentacionResource REST controller.
 *
 * @see AlimentacionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PcApp.class)
public class AlimentacionResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_POTENCIA = "AAAAAAAAAA";
    private static final String UPDATED_POTENCIA = "BBBBBBBBBB";

    private static final String DEFAULT_FLUJO_AIRE = "AAAAAAAAAA";
    private static final String UPDATED_FLUJO_AIRE = "BBBBBBBBBB";

    private static final String DEFAULT_CONECTORES = "AAAAAAAAAA";
    private static final String UPDATED_CONECTORES = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    @Autowired
    private AlimentacionRepository alimentacionRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAlimentacionMockMvc;

    private Alimentacion alimentacion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AlimentacionResource alimentacionResource = new AlimentacionResource(alimentacionRepository);
        this.restAlimentacionMockMvc = MockMvcBuilders.standaloneSetup(alimentacionResource)
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
    public static Alimentacion createEntity(EntityManager em) {
        Alimentacion alimentacion = new Alimentacion()
            .nombre(DEFAULT_NOMBRE)
            .potencia(DEFAULT_POTENCIA)
            .flujoAire(DEFAULT_FLUJO_AIRE)
            .conectores(DEFAULT_CONECTORES)
            .precio(DEFAULT_PRECIO);
        return alimentacion;
    }

    @Before
    public void initTest() {
        alimentacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlimentacion() throws Exception {
        int databaseSizeBeforeCreate = alimentacionRepository.findAll().size();

        // Create the Alimentacion
        restAlimentacionMockMvc.perform(post("/api/alimentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alimentacion)))
            .andExpect(status().isCreated());

        // Validate the Alimentacion in the database
        List<Alimentacion> alimentacionList = alimentacionRepository.findAll();
        assertThat(alimentacionList).hasSize(databaseSizeBeforeCreate + 1);
        Alimentacion testAlimentacion = alimentacionList.get(alimentacionList.size() - 1);
        assertThat(testAlimentacion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testAlimentacion.getPotencia()).isEqualTo(DEFAULT_POTENCIA);
        assertThat(testAlimentacion.getFlujoAire()).isEqualTo(DEFAULT_FLUJO_AIRE);
        assertThat(testAlimentacion.getConectores()).isEqualTo(DEFAULT_CONECTORES);
        assertThat(testAlimentacion.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createAlimentacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alimentacionRepository.findAll().size();

        // Create the Alimentacion with an existing ID
        alimentacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlimentacionMockMvc.perform(post("/api/alimentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alimentacion)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Alimentacion> alimentacionList = alimentacionRepository.findAll();
        assertThat(alimentacionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAlimentacions() throws Exception {
        // Initialize the database
        alimentacionRepository.saveAndFlush(alimentacion);

        // Get all the alimentacionList
        restAlimentacionMockMvc.perform(get("/api/alimentacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alimentacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].potencia").value(hasItem(DEFAULT_POTENCIA.toString())))
            .andExpect(jsonPath("$.[*].flujoAire").value(hasItem(DEFAULT_FLUJO_AIRE.toString())))
            .andExpect(jsonPath("$.[*].conectores").value(hasItem(DEFAULT_CONECTORES.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    public void getAlimentacion() throws Exception {
        // Initialize the database
        alimentacionRepository.saveAndFlush(alimentacion);

        // Get the alimentacion
        restAlimentacionMockMvc.perform(get("/api/alimentacions/{id}", alimentacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alimentacion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.potencia").value(DEFAULT_POTENCIA.toString()))
            .andExpect(jsonPath("$.flujoAire").value(DEFAULT_FLUJO_AIRE.toString()))
            .andExpect(jsonPath("$.conectores").value(DEFAULT_CONECTORES.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAlimentacion() throws Exception {
        // Get the alimentacion
        restAlimentacionMockMvc.perform(get("/api/alimentacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlimentacion() throws Exception {
        // Initialize the database
        alimentacionRepository.saveAndFlush(alimentacion);
        int databaseSizeBeforeUpdate = alimentacionRepository.findAll().size();

        // Update the alimentacion
        Alimentacion updatedAlimentacion = alimentacionRepository.findOne(alimentacion.getId());
        updatedAlimentacion
            .nombre(UPDATED_NOMBRE)
            .potencia(UPDATED_POTENCIA)
            .flujoAire(UPDATED_FLUJO_AIRE)
            .conectores(UPDATED_CONECTORES)
            .precio(UPDATED_PRECIO);

        restAlimentacionMockMvc.perform(put("/api/alimentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlimentacion)))
            .andExpect(status().isOk());

        // Validate the Alimentacion in the database
        List<Alimentacion> alimentacionList = alimentacionRepository.findAll();
        assertThat(alimentacionList).hasSize(databaseSizeBeforeUpdate);
        Alimentacion testAlimentacion = alimentacionList.get(alimentacionList.size() - 1);
        assertThat(testAlimentacion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testAlimentacion.getPotencia()).isEqualTo(UPDATED_POTENCIA);
        assertThat(testAlimentacion.getFlujoAire()).isEqualTo(UPDATED_FLUJO_AIRE);
        assertThat(testAlimentacion.getConectores()).isEqualTo(UPDATED_CONECTORES);
        assertThat(testAlimentacion.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingAlimentacion() throws Exception {
        int databaseSizeBeforeUpdate = alimentacionRepository.findAll().size();

        // Create the Alimentacion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlimentacionMockMvc.perform(put("/api/alimentacions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alimentacion)))
            .andExpect(status().isCreated());

        // Validate the Alimentacion in the database
        List<Alimentacion> alimentacionList = alimentacionRepository.findAll();
        assertThat(alimentacionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAlimentacion() throws Exception {
        // Initialize the database
        alimentacionRepository.saveAndFlush(alimentacion);
        int databaseSizeBeforeDelete = alimentacionRepository.findAll().size();

        // Get the alimentacion
        restAlimentacionMockMvc.perform(delete("/api/alimentacions/{id}", alimentacion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Alimentacion> alimentacionList = alimentacionRepository.findAll();
        assertThat(alimentacionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alimentacion.class);
    }
}
