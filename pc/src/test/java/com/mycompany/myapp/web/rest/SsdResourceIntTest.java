package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PcApp;

import com.mycompany.myapp.domain.Ssd;
import com.mycompany.myapp.repository.SsdRepository;
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
 * Test class for the SsdResource REST controller.
 *
 * @see SsdResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PcApp.class)
public class SsdResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_CAPACIDAD = "AAAAAAAAAA";
    private static final String UPDATED_CAPACIDAD = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    @Autowired
    private SsdRepository ssdRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSsdMockMvc;

    private Ssd ssd;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SsdResource ssdResource = new SsdResource(ssdRepository);
        this.restSsdMockMvc = MockMvcBuilders.standaloneSetup(ssdResource)
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
    public static Ssd createEntity(EntityManager em) {
        Ssd ssd = new Ssd()
            .nombre(DEFAULT_NOMBRE)
            .capacidad(DEFAULT_CAPACIDAD)
            .precio(DEFAULT_PRECIO);
        return ssd;
    }

    @Before
    public void initTest() {
        ssd = createEntity(em);
    }

    @Test
    @Transactional
    public void createSsd() throws Exception {
        int databaseSizeBeforeCreate = ssdRepository.findAll().size();

        // Create the Ssd
        restSsdMockMvc.perform(post("/api/ssds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ssd)))
            .andExpect(status().isCreated());

        // Validate the Ssd in the database
        List<Ssd> ssdList = ssdRepository.findAll();
        assertThat(ssdList).hasSize(databaseSizeBeforeCreate + 1);
        Ssd testSsd = ssdList.get(ssdList.size() - 1);
        assertThat(testSsd.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testSsd.getCapacidad()).isEqualTo(DEFAULT_CAPACIDAD);
        assertThat(testSsd.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createSsdWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ssdRepository.findAll().size();

        // Create the Ssd with an existing ID
        ssd.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSsdMockMvc.perform(post("/api/ssds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ssd)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Ssd> ssdList = ssdRepository.findAll();
        assertThat(ssdList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSsds() throws Exception {
        // Initialize the database
        ssdRepository.saveAndFlush(ssd);

        // Get all the ssdList
        restSsdMockMvc.perform(get("/api/ssds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ssd.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].capacidad").value(hasItem(DEFAULT_CAPACIDAD.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    public void getSsd() throws Exception {
        // Initialize the database
        ssdRepository.saveAndFlush(ssd);

        // Get the ssd
        restSsdMockMvc.perform(get("/api/ssds/{id}", ssd.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ssd.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.capacidad").value(DEFAULT_CAPACIDAD.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSsd() throws Exception {
        // Get the ssd
        restSsdMockMvc.perform(get("/api/ssds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSsd() throws Exception {
        // Initialize the database
        ssdRepository.saveAndFlush(ssd);
        int databaseSizeBeforeUpdate = ssdRepository.findAll().size();

        // Update the ssd
        Ssd updatedSsd = ssdRepository.findOne(ssd.getId());
        updatedSsd
            .nombre(UPDATED_NOMBRE)
            .capacidad(UPDATED_CAPACIDAD)
            .precio(UPDATED_PRECIO);

        restSsdMockMvc.perform(put("/api/ssds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSsd)))
            .andExpect(status().isOk());

        // Validate the Ssd in the database
        List<Ssd> ssdList = ssdRepository.findAll();
        assertThat(ssdList).hasSize(databaseSizeBeforeUpdate);
        Ssd testSsd = ssdList.get(ssdList.size() - 1);
        assertThat(testSsd.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testSsd.getCapacidad()).isEqualTo(UPDATED_CAPACIDAD);
        assertThat(testSsd.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingSsd() throws Exception {
        int databaseSizeBeforeUpdate = ssdRepository.findAll().size();

        // Create the Ssd

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSsdMockMvc.perform(put("/api/ssds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ssd)))
            .andExpect(status().isCreated());

        // Validate the Ssd in the database
        List<Ssd> ssdList = ssdRepository.findAll();
        assertThat(ssdList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSsd() throws Exception {
        // Initialize the database
        ssdRepository.saveAndFlush(ssd);
        int databaseSizeBeforeDelete = ssdRepository.findAll().size();

        // Get the ssd
        restSsdMockMvc.perform(delete("/api/ssds/{id}", ssd.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ssd> ssdList = ssdRepository.findAll();
        assertThat(ssdList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ssd.class);
    }
}
