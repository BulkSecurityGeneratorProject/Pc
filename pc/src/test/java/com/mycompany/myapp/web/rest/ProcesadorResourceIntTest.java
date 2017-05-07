package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PcApp;

import com.mycompany.myapp.domain.Procesador;
import com.mycompany.myapp.repository.ProcesadorRepository;
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

import com.mycompany.myapp.domain.enumeration.Socket;
/**
 * Test class for the ProcesadorResource REST controller.
 *
 * @see ProcesadorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PcApp.class)
public class ProcesadorResourceIntTest {

    private static final String DEFAULT_NOMBRE_PROCESADOR = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_PROCESADOR = "BBBBBBBBBB";

    private static final String DEFAULT_MEMORIA = "AAAAAAAAAA";
    private static final String UPDATED_MEMORIA = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUCLEOS = 1;
    private static final Integer UPDATED_NUCLEOS = 2;

    private static final String DEFAULT_GHZ = "AAAAAAAAAA";
    private static final String UPDATED_GHZ = "BBBBBBBBBB";

    private static final String DEFAULT_VELOCIDAD = "AAAAAAAAAA";
    private static final String UPDATED_VELOCIDAD = "BBBBBBBBBB";

    private static final Socket DEFAULT_SOCKET = Socket.LGA1156;
    private static final Socket UPDATED_SOCKET = Socket.LGA1366;

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    @Autowired
    private ProcesadorRepository procesadorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProcesadorMockMvc;

    private Procesador procesador;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProcesadorResource procesadorResource = new ProcesadorResource(procesadorRepository);
        this.restProcesadorMockMvc = MockMvcBuilders.standaloneSetup(procesadorResource)
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
    public static Procesador createEntity(EntityManager em) {
        Procesador procesador = new Procesador()
            .nombreProcesador(DEFAULT_NOMBRE_PROCESADOR)
            .memoria(DEFAULT_MEMORIA)
            .nucleos(DEFAULT_NUCLEOS)
            .ghz(DEFAULT_GHZ)
            .velocidad(DEFAULT_VELOCIDAD)
            .socket(DEFAULT_SOCKET)
            .precio(DEFAULT_PRECIO);
        return procesador;
    }

    @Before
    public void initTest() {
        procesador = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcesador() throws Exception {
        int databaseSizeBeforeCreate = procesadorRepository.findAll().size();

        // Create the Procesador
        restProcesadorMockMvc.perform(post("/api/procesadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procesador)))
            .andExpect(status().isCreated());

        // Validate the Procesador in the database
        List<Procesador> procesadorList = procesadorRepository.findAll();
        assertThat(procesadorList).hasSize(databaseSizeBeforeCreate + 1);
        Procesador testProcesador = procesadorList.get(procesadorList.size() - 1);
        assertThat(testProcesador.getNombreProcesador()).isEqualTo(DEFAULT_NOMBRE_PROCESADOR);
        assertThat(testProcesador.getMemoria()).isEqualTo(DEFAULT_MEMORIA);
        assertThat(testProcesador.getNucleos()).isEqualTo(DEFAULT_NUCLEOS);
        assertThat(testProcesador.getGhz()).isEqualTo(DEFAULT_GHZ);
        assertThat(testProcesador.getVelocidad()).isEqualTo(DEFAULT_VELOCIDAD);
        assertThat(testProcesador.getSocket()).isEqualTo(DEFAULT_SOCKET);
        assertThat(testProcesador.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createProcesadorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = procesadorRepository.findAll().size();

        // Create the Procesador with an existing ID
        procesador.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcesadorMockMvc.perform(post("/api/procesadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procesador)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Procesador> procesadorList = procesadorRepository.findAll();
        assertThat(procesadorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllProcesadors() throws Exception {
        // Initialize the database
        procesadorRepository.saveAndFlush(procesador);

        // Get all the procesadorList
        restProcesadorMockMvc.perform(get("/api/procesadors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procesador.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreProcesador").value(hasItem(DEFAULT_NOMBRE_PROCESADOR.toString())))
            .andExpect(jsonPath("$.[*].memoria").value(hasItem(DEFAULT_MEMORIA.toString())))
            .andExpect(jsonPath("$.[*].nucleos").value(hasItem(DEFAULT_NUCLEOS)))
            .andExpect(jsonPath("$.[*].ghz").value(hasItem(DEFAULT_GHZ.toString())))
            .andExpect(jsonPath("$.[*].velocidad").value(hasItem(DEFAULT_VELOCIDAD.toString())))
            .andExpect(jsonPath("$.[*].socket").value(hasItem(DEFAULT_SOCKET.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    public void getProcesador() throws Exception {
        // Initialize the database
        procesadorRepository.saveAndFlush(procesador);

        // Get the procesador
        restProcesadorMockMvc.perform(get("/api/procesadors/{id}", procesador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(procesador.getId().intValue()))
            .andExpect(jsonPath("$.nombreProcesador").value(DEFAULT_NOMBRE_PROCESADOR.toString()))
            .andExpect(jsonPath("$.memoria").value(DEFAULT_MEMORIA.toString()))
            .andExpect(jsonPath("$.nucleos").value(DEFAULT_NUCLEOS))
            .andExpect(jsonPath("$.ghz").value(DEFAULT_GHZ.toString()))
            .andExpect(jsonPath("$.velocidad").value(DEFAULT_VELOCIDAD.toString()))
            .andExpect(jsonPath("$.socket").value(DEFAULT_SOCKET.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingProcesador() throws Exception {
        // Get the procesador
        restProcesadorMockMvc.perform(get("/api/procesadors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcesador() throws Exception {
        // Initialize the database
        procesadorRepository.saveAndFlush(procesador);
        int databaseSizeBeforeUpdate = procesadorRepository.findAll().size();

        // Update the procesador
        Procesador updatedProcesador = procesadorRepository.findOne(procesador.getId());
        updatedProcesador
            .nombreProcesador(UPDATED_NOMBRE_PROCESADOR)
            .memoria(UPDATED_MEMORIA)
            .nucleos(UPDATED_NUCLEOS)
            .ghz(UPDATED_GHZ)
            .velocidad(UPDATED_VELOCIDAD)
            .socket(UPDATED_SOCKET)
            .precio(UPDATED_PRECIO);

        restProcesadorMockMvc.perform(put("/api/procesadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcesador)))
            .andExpect(status().isOk());

        // Validate the Procesador in the database
        List<Procesador> procesadorList = procesadorRepository.findAll();
        assertThat(procesadorList).hasSize(databaseSizeBeforeUpdate);
        Procesador testProcesador = procesadorList.get(procesadorList.size() - 1);
        assertThat(testProcesador.getNombreProcesador()).isEqualTo(UPDATED_NOMBRE_PROCESADOR);
        assertThat(testProcesador.getMemoria()).isEqualTo(UPDATED_MEMORIA);
        assertThat(testProcesador.getNucleos()).isEqualTo(UPDATED_NUCLEOS);
        assertThat(testProcesador.getGhz()).isEqualTo(UPDATED_GHZ);
        assertThat(testProcesador.getVelocidad()).isEqualTo(UPDATED_VELOCIDAD);
        assertThat(testProcesador.getSocket()).isEqualTo(UPDATED_SOCKET);
        assertThat(testProcesador.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingProcesador() throws Exception {
        int databaseSizeBeforeUpdate = procesadorRepository.findAll().size();

        // Create the Procesador

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProcesadorMockMvc.perform(put("/api/procesadors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procesador)))
            .andExpect(status().isCreated());

        // Validate the Procesador in the database
        List<Procesador> procesadorList = procesadorRepository.findAll();
        assertThat(procesadorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProcesador() throws Exception {
        // Initialize the database
        procesadorRepository.saveAndFlush(procesador);
        int databaseSizeBeforeDelete = procesadorRepository.findAll().size();

        // Get the procesador
        restProcesadorMockMvc.perform(delete("/api/procesadors/{id}", procesador.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Procesador> procesadorList = procesadorRepository.findAll();
        assertThat(procesadorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Procesador.class);
    }
}
