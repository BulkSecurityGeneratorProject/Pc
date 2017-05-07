package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PcApp;

import com.mycompany.myapp.domain.Placa;
import com.mycompany.myapp.repository.PlacaRepository;
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

import com.mycompany.myapp.domain.enumeration.Tipo;
import com.mycompany.myapp.domain.enumeration.Socket;
/**
 * Test class for the PlacaResource REST controller.
 *
 * @see PlacaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PcApp.class)
public class PlacaResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final Tipo DEFAULT_TIPO = Tipo.AX;
    private static final Tipo UPDATED_TIPO = Tipo.AT;

    private static final String DEFAULT_PROCESADORES = "AAAAAAAAAA";
    private static final String UPDATED_PROCESADORES = "BBBBBBBBBB";

    private static final String DEFAULT_MEMORIA = "AAAAAAAAAA";
    private static final String UPDATED_MEMORIA = "BBBBBBBBBB";

    private static final String DEFAULT_PCI = "AAAAAAAAAA";
    private static final String UPDATED_PCI = "BBBBBBBBBB";

    private static final Socket DEFAULT_SOCKET = Socket.LGA1156;
    private static final Socket UPDATED_SOCKET = Socket.LGA1366;

    private static final String DEFAULT_ALMACENAMIENTO = "AAAAAAAAAA";
    private static final String UPDATED_ALMACENAMIENTO = "BBBBBBBBBB";

    private static final String DEFAULT_BIOS = "AAAAAAAAAA";
    private static final String UPDATED_BIOS = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    @Autowired
    private PlacaRepository placaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPlacaMockMvc;

    private Placa placa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PlacaResource placaResource = new PlacaResource(placaRepository);
        this.restPlacaMockMvc = MockMvcBuilders.standaloneSetup(placaResource)
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
    public static Placa createEntity(EntityManager em) {
        Placa placa = new Placa()
            .nombre(DEFAULT_NOMBRE)
            .tipo(DEFAULT_TIPO)
            .procesadores(DEFAULT_PROCESADORES)
            .memoria(DEFAULT_MEMORIA)
            .pci(DEFAULT_PCI)
            .socket(DEFAULT_SOCKET)
            .almacenamiento(DEFAULT_ALMACENAMIENTO)
            .bios(DEFAULT_BIOS)
            .precio(DEFAULT_PRECIO);
        return placa;
    }

    @Before
    public void initTest() {
        placa = createEntity(em);
    }

    @Test
    @Transactional
    public void createPlaca() throws Exception {
        int databaseSizeBeforeCreate = placaRepository.findAll().size();

        // Create the Placa
        restPlacaMockMvc.perform(post("/api/placas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placa)))
            .andExpect(status().isCreated());

        // Validate the Placa in the database
        List<Placa> placaList = placaRepository.findAll();
        assertThat(placaList).hasSize(databaseSizeBeforeCreate + 1);
        Placa testPlaca = placaList.get(placaList.size() - 1);
        assertThat(testPlaca.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testPlaca.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testPlaca.getProcesadores()).isEqualTo(DEFAULT_PROCESADORES);
        assertThat(testPlaca.getMemoria()).isEqualTo(DEFAULT_MEMORIA);
        assertThat(testPlaca.getPci()).isEqualTo(DEFAULT_PCI);
        assertThat(testPlaca.getSocket()).isEqualTo(DEFAULT_SOCKET);
        assertThat(testPlaca.getAlmacenamiento()).isEqualTo(DEFAULT_ALMACENAMIENTO);
        assertThat(testPlaca.getBios()).isEqualTo(DEFAULT_BIOS);
        assertThat(testPlaca.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createPlacaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = placaRepository.findAll().size();

        // Create the Placa with an existing ID
        placa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlacaMockMvc.perform(post("/api/placas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placa)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Placa> placaList = placaRepository.findAll();
        assertThat(placaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPlacas() throws Exception {
        // Initialize the database
        placaRepository.saveAndFlush(placa);

        // Get all the placaList
        restPlacaMockMvc.perform(get("/api/placas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(placa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].procesadores").value(hasItem(DEFAULT_PROCESADORES.toString())))
            .andExpect(jsonPath("$.[*].memoria").value(hasItem(DEFAULT_MEMORIA.toString())))
            .andExpect(jsonPath("$.[*].pci").value(hasItem(DEFAULT_PCI.toString())))
            .andExpect(jsonPath("$.[*].socket").value(hasItem(DEFAULT_SOCKET.toString())))
            .andExpect(jsonPath("$.[*].almacenamiento").value(hasItem(DEFAULT_ALMACENAMIENTO.toString())))
            .andExpect(jsonPath("$.[*].bios").value(hasItem(DEFAULT_BIOS.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    public void getPlaca() throws Exception {
        // Initialize the database
        placaRepository.saveAndFlush(placa);

        // Get the placa
        restPlacaMockMvc.perform(get("/api/placas/{id}", placa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(placa.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.procesadores").value(DEFAULT_PROCESADORES.toString()))
            .andExpect(jsonPath("$.memoria").value(DEFAULT_MEMORIA.toString()))
            .andExpect(jsonPath("$.pci").value(DEFAULT_PCI.toString()))
            .andExpect(jsonPath("$.socket").value(DEFAULT_SOCKET.toString()))
            .andExpect(jsonPath("$.almacenamiento").value(DEFAULT_ALMACENAMIENTO.toString()))
            .andExpect(jsonPath("$.bios").value(DEFAULT_BIOS.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPlaca() throws Exception {
        // Get the placa
        restPlacaMockMvc.perform(get("/api/placas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePlaca() throws Exception {
        // Initialize the database
        placaRepository.saveAndFlush(placa);
        int databaseSizeBeforeUpdate = placaRepository.findAll().size();

        // Update the placa
        Placa updatedPlaca = placaRepository.findOne(placa.getId());
        updatedPlaca
            .nombre(UPDATED_NOMBRE)
            .tipo(UPDATED_TIPO)
            .procesadores(UPDATED_PROCESADORES)
            .memoria(UPDATED_MEMORIA)
            .pci(UPDATED_PCI)
            .socket(UPDATED_SOCKET)
            .almacenamiento(UPDATED_ALMACENAMIENTO)
            .bios(UPDATED_BIOS)
            .precio(UPDATED_PRECIO);

        restPlacaMockMvc.perform(put("/api/placas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPlaca)))
            .andExpect(status().isOk());

        // Validate the Placa in the database
        List<Placa> placaList = placaRepository.findAll();
        assertThat(placaList).hasSize(databaseSizeBeforeUpdate);
        Placa testPlaca = placaList.get(placaList.size() - 1);
        assertThat(testPlaca.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testPlaca.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testPlaca.getProcesadores()).isEqualTo(UPDATED_PROCESADORES);
        assertThat(testPlaca.getMemoria()).isEqualTo(UPDATED_MEMORIA);
        assertThat(testPlaca.getPci()).isEqualTo(UPDATED_PCI);
        assertThat(testPlaca.getSocket()).isEqualTo(UPDATED_SOCKET);
        assertThat(testPlaca.getAlmacenamiento()).isEqualTo(UPDATED_ALMACENAMIENTO);
        assertThat(testPlaca.getBios()).isEqualTo(UPDATED_BIOS);
        assertThat(testPlaca.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingPlaca() throws Exception {
        int databaseSizeBeforeUpdate = placaRepository.findAll().size();

        // Create the Placa

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPlacaMockMvc.perform(put("/api/placas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(placa)))
            .andExpect(status().isCreated());

        // Validate the Placa in the database
        List<Placa> placaList = placaRepository.findAll();
        assertThat(placaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePlaca() throws Exception {
        // Initialize the database
        placaRepository.saveAndFlush(placa);
        int databaseSizeBeforeDelete = placaRepository.findAll().size();

        // Get the placa
        restPlacaMockMvc.perform(delete("/api/placas/{id}", placa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Placa> placaList = placaRepository.findAll();
        assertThat(placaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Placa.class);
    }
}
