package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PcApp;

import com.mycompany.myapp.domain.Fabricante;
import com.mycompany.myapp.repository.FabricanteRepository;
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
 * Test class for the FabricanteResource REST controller.
 *
 * @see FabricanteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PcApp.class)
public class FabricanteResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private FabricanteRepository fabricanteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFabricanteMockMvc;

    private Fabricante fabricante;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FabricanteResource fabricanteResource = new FabricanteResource(fabricanteRepository);
        this.restFabricanteMockMvc = MockMvcBuilders.standaloneSetup(fabricanteResource)
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
    public static Fabricante createEntity(EntityManager em) {
        Fabricante fabricante = new Fabricante()
            .nombre(DEFAULT_NOMBRE);
        return fabricante;
    }

    @Before
    public void initTest() {
        fabricante = createEntity(em);
    }

    @Test
    @Transactional
    public void createFabricante() throws Exception {
        int databaseSizeBeforeCreate = fabricanteRepository.findAll().size();

        // Create the Fabricante
        restFabricanteMockMvc.perform(post("/api/fabricantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fabricante)))
            .andExpect(status().isCreated());

        // Validate the Fabricante in the database
        List<Fabricante> fabricanteList = fabricanteRepository.findAll();
        assertThat(fabricanteList).hasSize(databaseSizeBeforeCreate + 1);
        Fabricante testFabricante = fabricanteList.get(fabricanteList.size() - 1);
        assertThat(testFabricante.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createFabricanteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fabricanteRepository.findAll().size();

        // Create the Fabricante with an existing ID
        fabricante.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFabricanteMockMvc.perform(post("/api/fabricantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fabricante)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Fabricante> fabricanteList = fabricanteRepository.findAll();
        assertThat(fabricanteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFabricantes() throws Exception {
        // Initialize the database
        fabricanteRepository.saveAndFlush(fabricante);

        // Get all the fabricanteList
        restFabricanteMockMvc.perform(get("/api/fabricantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fabricante.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }

    @Test
    @Transactional
    public void getFabricante() throws Exception {
        // Initialize the database
        fabricanteRepository.saveAndFlush(fabricante);

        // Get the fabricante
        restFabricanteMockMvc.perform(get("/api/fabricantes/{id}", fabricante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fabricante.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFabricante() throws Exception {
        // Get the fabricante
        restFabricanteMockMvc.perform(get("/api/fabricantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFabricante() throws Exception {
        // Initialize the database
        fabricanteRepository.saveAndFlush(fabricante);
        int databaseSizeBeforeUpdate = fabricanteRepository.findAll().size();

        // Update the fabricante
        Fabricante updatedFabricante = fabricanteRepository.findOne(fabricante.getId());
        updatedFabricante
            .nombre(UPDATED_NOMBRE);

        restFabricanteMockMvc.perform(put("/api/fabricantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFabricante)))
            .andExpect(status().isOk());

        // Validate the Fabricante in the database
        List<Fabricante> fabricanteList = fabricanteRepository.findAll();
        assertThat(fabricanteList).hasSize(databaseSizeBeforeUpdate);
        Fabricante testFabricante = fabricanteList.get(fabricanteList.size() - 1);
        assertThat(testFabricante.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingFabricante() throws Exception {
        int databaseSizeBeforeUpdate = fabricanteRepository.findAll().size();

        // Create the Fabricante

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFabricanteMockMvc.perform(put("/api/fabricantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fabricante)))
            .andExpect(status().isCreated());

        // Validate the Fabricante in the database
        List<Fabricante> fabricanteList = fabricanteRepository.findAll();
        assertThat(fabricanteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFabricante() throws Exception {
        // Initialize the database
        fabricanteRepository.saveAndFlush(fabricante);
        int databaseSizeBeforeDelete = fabricanteRepository.findAll().size();

        // Get the fabricante
        restFabricanteMockMvc.perform(delete("/api/fabricantes/{id}", fabricante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Fabricante> fabricanteList = fabricanteRepository.findAll();
        assertThat(fabricanteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fabricante.class);
    }
}
