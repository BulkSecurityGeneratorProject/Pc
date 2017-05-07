package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PcApp;

import com.mycompany.myapp.domain.Ordenador;
import com.mycompany.myapp.repository.OrdenadorRepository;
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
 * Test class for the OrdenadorResource REST controller.
 *
 * @see OrdenadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PcApp.class)
public class OrdenadorResourceIntTest {

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    @Autowired
    private OrdenadorRepository ordenadorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrdenadorMockMvc;

    private Ordenador ordenador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        OrdenadorResource ordenadorResource = new OrdenadorResource(ordenadorRepository);
        this.restOrdenadorMockMvc = MockMvcBuilders.standaloneSetup(ordenadorResource)
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
    public static Ordenador createEntity(EntityManager em) {
        Ordenador ordenador = new Ordenador()
            .observaciones(DEFAULT_OBSERVACIONES)
            .precio(DEFAULT_PRECIO);
        return ordenador;
    }

    @Before
    public void initTest() {
        ordenador = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrdenador() throws Exception {
        int databaseSizeBeforeCreate = ordenadorRepository.findAll().size();

        // Create the Ordenador
        restOrdenadorMockMvc.perform(post("/api/ordenadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenador)))
            .andExpect(status().isCreated());

        // Validate the Ordenador in the database
        List<Ordenador> ordenadorList = ordenadorRepository.findAll();
        assertThat(ordenadorList).hasSize(databaseSizeBeforeCreate + 1);
        Ordenador testOrdenador = ordenadorList.get(ordenadorList.size() - 1);
        assertThat(testOrdenador.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testOrdenador.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createOrdenadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ordenadorRepository.findAll().size();

        // Create the Ordenador with an existing ID
        ordenador.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrdenadorMockMvc.perform(post("/api/ordenadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenador)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ordenador> ordenadorList = ordenadorRepository.findAll();
        assertThat(ordenadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrdenadors() throws Exception {
        // Initialize the database
        ordenadorRepository.saveAndFlush(ordenador);

        // Get all the ordenadorList
        restOrdenadorMockMvc.perform(get("/api/ordenadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ordenador.getId().intValue())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    public void getOrdenador() throws Exception {
        // Initialize the database
        ordenadorRepository.saveAndFlush(ordenador);

        // Get the ordenador
        restOrdenadorMockMvc.perform(get("/api/ordenadors/{id}", ordenador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ordenador.getId().intValue()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrdenador() throws Exception {
        // Get the ordenador
        restOrdenadorMockMvc.perform(get("/api/ordenadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrdenador() throws Exception {
        // Initialize the database
        ordenadorRepository.saveAndFlush(ordenador);
        int databaseSizeBeforeUpdate = ordenadorRepository.findAll().size();

        // Update the ordenador
        Ordenador updatedOrdenador = ordenadorRepository.findOne(ordenador.getId());
        updatedOrdenador
            .observaciones(UPDATED_OBSERVACIONES)
            .precio(UPDATED_PRECIO);

        restOrdenadorMockMvc.perform(put("/api/ordenadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrdenador)))
            .andExpect(status().isOk());

        // Validate the Ordenador in the database
        List<Ordenador> ordenadorList = ordenadorRepository.findAll();
        assertThat(ordenadorList).hasSize(databaseSizeBeforeUpdate);
        Ordenador testOrdenador = ordenadorList.get(ordenadorList.size() - 1);
        assertThat(testOrdenador.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testOrdenador.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingOrdenador() throws Exception {
        int databaseSizeBeforeUpdate = ordenadorRepository.findAll().size();

        // Create the Ordenador

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrdenadorMockMvc.perform(put("/api/ordenadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ordenador)))
            .andExpect(status().isCreated());

        // Validate the Ordenador in the database
        List<Ordenador> ordenadorList = ordenadorRepository.findAll();
        assertThat(ordenadorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrdenador() throws Exception {
        // Initialize the database
        ordenadorRepository.saveAndFlush(ordenador);
        int databaseSizeBeforeDelete = ordenadorRepository.findAll().size();

        // Get the ordenador
        restOrdenadorMockMvc.perform(delete("/api/ordenadors/{id}", ordenador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ordenador> ordenadorList = ordenadorRepository.findAll();
        assertThat(ordenadorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ordenador.class);
    }
}
