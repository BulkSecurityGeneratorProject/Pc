package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.PcApp;

import com.mycompany.myapp.domain.Graficas;
import com.mycompany.myapp.repository.GraficasRepository;
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
 * Test class for the GraficasResource REST controller.
 *
 * @see GraficasResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PcApp.class)
public class GraficasResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_BUS = "AAAAAAAAAA";
    private static final String UPDATED_BUS = "BBBBBBBBBB";

    private static final String DEFAULT_SALIDAS = "AAAAAAAAAA";
    private static final String UPDATED_SALIDAS = "BBBBBBBBBB";

    private static final String DEFAULT_REFRIGERACION = "AAAAAAAAAA";
    private static final String UPDATED_REFRIGERACION = "BBBBBBBBBB";

    private static final String DEFAULT_COMPATIBILIDAD = "AAAAAAAAAA";
    private static final String UPDATED_COMPATIBILIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_CHIPSET = "AAAAAAAAAA";
    private static final String UPDATED_CHIPSET = "BBBBBBBBBB";

    private static final String DEFAULT_VALOCIDAD = "AAAAAAAAAA";
    private static final String UPDATED_VALOCIDAD = "BBBBBBBBBB";

    private static final String DEFAULT_MEMORIA = "AAAAAAAAAA";
    private static final String UPDATED_MEMORIA = "BBBBBBBBBB";

    private static final String DEFAULT_RAMDAC = "AAAAAAAAAA";
    private static final String UPDATED_RAMDAC = "BBBBBBBBBB";

    private static final String DEFAULT_STREAM_PROCESOR = "AAAAAAAAAA";
    private static final String UPDATED_STREAM_PROCESOR = "BBBBBBBBBB";

    private static final String DEFAULT_SHADER_CLOCK = "AAAAAAAAAA";
    private static final String UPDATED_SHADER_CLOCK = "BBBBBBBBBB";

    private static final String DEFAULT_PIXEL_RATE = "AAAAAAAAAA";
    private static final String UPDATED_PIXEL_RATE = "BBBBBBBBBB";

    private static final String DEFAULT_SHADER_MODEL = "AAAAAAAAAA";
    private static final String UPDATED_SHADER_MODEL = "BBBBBBBBBB";

    private static final Double DEFAULT_PRECIO = 1D;
    private static final Double UPDATED_PRECIO = 2D;

    @Autowired
    private GraficasRepository graficasRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGraficasMockMvc;

    private Graficas graficas;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        GraficasResource graficasResource = new GraficasResource(graficasRepository);
        this.restGraficasMockMvc = MockMvcBuilders.standaloneSetup(graficasResource)
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
    public static Graficas createEntity(EntityManager em) {
        Graficas graficas = new Graficas()
            .nombre(DEFAULT_NOMBRE)
            .bus(DEFAULT_BUS)
            .salidas(DEFAULT_SALIDAS)
            .refrigeracion(DEFAULT_REFRIGERACION)
            .compatibilidad(DEFAULT_COMPATIBILIDAD)
            .chipset(DEFAULT_CHIPSET)
            .valocidad(DEFAULT_VALOCIDAD)
            .memoria(DEFAULT_MEMORIA)
            .ramdac(DEFAULT_RAMDAC)
            .streamProcesor(DEFAULT_STREAM_PROCESOR)
            .shaderClock(DEFAULT_SHADER_CLOCK)
            .pixelRate(DEFAULT_PIXEL_RATE)
            .shaderModel(DEFAULT_SHADER_MODEL)
            .precio(DEFAULT_PRECIO);
        return graficas;
    }

    @Before
    public void initTest() {
        graficas = createEntity(em);
    }

    @Test
    @Transactional
    public void createGraficas() throws Exception {
        int databaseSizeBeforeCreate = graficasRepository.findAll().size();

        // Create the Graficas
        restGraficasMockMvc.perform(post("/api/graficas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(graficas)))
            .andExpect(status().isCreated());

        // Validate the Graficas in the database
        List<Graficas> graficasList = graficasRepository.findAll();
        assertThat(graficasList).hasSize(databaseSizeBeforeCreate + 1);
        Graficas testGraficas = graficasList.get(graficasList.size() - 1);
        assertThat(testGraficas.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testGraficas.getBus()).isEqualTo(DEFAULT_BUS);
        assertThat(testGraficas.getSalidas()).isEqualTo(DEFAULT_SALIDAS);
        assertThat(testGraficas.getRefrigeracion()).isEqualTo(DEFAULT_REFRIGERACION);
        assertThat(testGraficas.getCompatibilidad()).isEqualTo(DEFAULT_COMPATIBILIDAD);
        assertThat(testGraficas.getChipset()).isEqualTo(DEFAULT_CHIPSET);
        assertThat(testGraficas.getValocidad()).isEqualTo(DEFAULT_VALOCIDAD);
        assertThat(testGraficas.getMemoria()).isEqualTo(DEFAULT_MEMORIA);
        assertThat(testGraficas.getRamdac()).isEqualTo(DEFAULT_RAMDAC);
        assertThat(testGraficas.getStreamProcesor()).isEqualTo(DEFAULT_STREAM_PROCESOR);
        assertThat(testGraficas.getShaderClock()).isEqualTo(DEFAULT_SHADER_CLOCK);
        assertThat(testGraficas.getPixelRate()).isEqualTo(DEFAULT_PIXEL_RATE);
        assertThat(testGraficas.getShaderModel()).isEqualTo(DEFAULT_SHADER_MODEL);
        assertThat(testGraficas.getPrecio()).isEqualTo(DEFAULT_PRECIO);
    }

    @Test
    @Transactional
    public void createGraficasWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = graficasRepository.findAll().size();

        // Create the Graficas with an existing ID
        graficas.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGraficasMockMvc.perform(post("/api/graficas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(graficas)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Graficas> graficasList = graficasRepository.findAll();
        assertThat(graficasList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGraficas() throws Exception {
        // Initialize the database
        graficasRepository.saveAndFlush(graficas);

        // Get all the graficasList
        restGraficasMockMvc.perform(get("/api/graficas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(graficas.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].bus").value(hasItem(DEFAULT_BUS.toString())))
            .andExpect(jsonPath("$.[*].salidas").value(hasItem(DEFAULT_SALIDAS.toString())))
            .andExpect(jsonPath("$.[*].refrigeracion").value(hasItem(DEFAULT_REFRIGERACION.toString())))
            .andExpect(jsonPath("$.[*].compatibilidad").value(hasItem(DEFAULT_COMPATIBILIDAD.toString())))
            .andExpect(jsonPath("$.[*].chipset").value(hasItem(DEFAULT_CHIPSET.toString())))
            .andExpect(jsonPath("$.[*].valocidad").value(hasItem(DEFAULT_VALOCIDAD.toString())))
            .andExpect(jsonPath("$.[*].memoria").value(hasItem(DEFAULT_MEMORIA.toString())))
            .andExpect(jsonPath("$.[*].ramdac").value(hasItem(DEFAULT_RAMDAC.toString())))
            .andExpect(jsonPath("$.[*].streamProcesor").value(hasItem(DEFAULT_STREAM_PROCESOR.toString())))
            .andExpect(jsonPath("$.[*].shaderClock").value(hasItem(DEFAULT_SHADER_CLOCK.toString())))
            .andExpect(jsonPath("$.[*].pixelRate").value(hasItem(DEFAULT_PIXEL_RATE.toString())))
            .andExpect(jsonPath("$.[*].shaderModel").value(hasItem(DEFAULT_SHADER_MODEL.toString())))
            .andExpect(jsonPath("$.[*].precio").value(hasItem(DEFAULT_PRECIO.doubleValue())));
    }

    @Test
    @Transactional
    public void getGraficas() throws Exception {
        // Initialize the database
        graficasRepository.saveAndFlush(graficas);

        // Get the graficas
        restGraficasMockMvc.perform(get("/api/graficas/{id}", graficas.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(graficas.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.bus").value(DEFAULT_BUS.toString()))
            .andExpect(jsonPath("$.salidas").value(DEFAULT_SALIDAS.toString()))
            .andExpect(jsonPath("$.refrigeracion").value(DEFAULT_REFRIGERACION.toString()))
            .andExpect(jsonPath("$.compatibilidad").value(DEFAULT_COMPATIBILIDAD.toString()))
            .andExpect(jsonPath("$.chipset").value(DEFAULT_CHIPSET.toString()))
            .andExpect(jsonPath("$.valocidad").value(DEFAULT_VALOCIDAD.toString()))
            .andExpect(jsonPath("$.memoria").value(DEFAULT_MEMORIA.toString()))
            .andExpect(jsonPath("$.ramdac").value(DEFAULT_RAMDAC.toString()))
            .andExpect(jsonPath("$.streamProcesor").value(DEFAULT_STREAM_PROCESOR.toString()))
            .andExpect(jsonPath("$.shaderClock").value(DEFAULT_SHADER_CLOCK.toString()))
            .andExpect(jsonPath("$.pixelRate").value(DEFAULT_PIXEL_RATE.toString()))
            .andExpect(jsonPath("$.shaderModel").value(DEFAULT_SHADER_MODEL.toString()))
            .andExpect(jsonPath("$.precio").value(DEFAULT_PRECIO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGraficas() throws Exception {
        // Get the graficas
        restGraficasMockMvc.perform(get("/api/graficas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGraficas() throws Exception {
        // Initialize the database
        graficasRepository.saveAndFlush(graficas);
        int databaseSizeBeforeUpdate = graficasRepository.findAll().size();

        // Update the graficas
        Graficas updatedGraficas = graficasRepository.findOne(graficas.getId());
        updatedGraficas
            .nombre(UPDATED_NOMBRE)
            .bus(UPDATED_BUS)
            .salidas(UPDATED_SALIDAS)
            .refrigeracion(UPDATED_REFRIGERACION)
            .compatibilidad(UPDATED_COMPATIBILIDAD)
            .chipset(UPDATED_CHIPSET)
            .valocidad(UPDATED_VALOCIDAD)
            .memoria(UPDATED_MEMORIA)
            .ramdac(UPDATED_RAMDAC)
            .streamProcesor(UPDATED_STREAM_PROCESOR)
            .shaderClock(UPDATED_SHADER_CLOCK)
            .pixelRate(UPDATED_PIXEL_RATE)
            .shaderModel(UPDATED_SHADER_MODEL)
            .precio(UPDATED_PRECIO);

        restGraficasMockMvc.perform(put("/api/graficas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGraficas)))
            .andExpect(status().isOk());

        // Validate the Graficas in the database
        List<Graficas> graficasList = graficasRepository.findAll();
        assertThat(graficasList).hasSize(databaseSizeBeforeUpdate);
        Graficas testGraficas = graficasList.get(graficasList.size() - 1);
        assertThat(testGraficas.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testGraficas.getBus()).isEqualTo(UPDATED_BUS);
        assertThat(testGraficas.getSalidas()).isEqualTo(UPDATED_SALIDAS);
        assertThat(testGraficas.getRefrigeracion()).isEqualTo(UPDATED_REFRIGERACION);
        assertThat(testGraficas.getCompatibilidad()).isEqualTo(UPDATED_COMPATIBILIDAD);
        assertThat(testGraficas.getChipset()).isEqualTo(UPDATED_CHIPSET);
        assertThat(testGraficas.getValocidad()).isEqualTo(UPDATED_VALOCIDAD);
        assertThat(testGraficas.getMemoria()).isEqualTo(UPDATED_MEMORIA);
        assertThat(testGraficas.getRamdac()).isEqualTo(UPDATED_RAMDAC);
        assertThat(testGraficas.getStreamProcesor()).isEqualTo(UPDATED_STREAM_PROCESOR);
        assertThat(testGraficas.getShaderClock()).isEqualTo(UPDATED_SHADER_CLOCK);
        assertThat(testGraficas.getPixelRate()).isEqualTo(UPDATED_PIXEL_RATE);
        assertThat(testGraficas.getShaderModel()).isEqualTo(UPDATED_SHADER_MODEL);
        assertThat(testGraficas.getPrecio()).isEqualTo(UPDATED_PRECIO);
    }

    @Test
    @Transactional
    public void updateNonExistingGraficas() throws Exception {
        int databaseSizeBeforeUpdate = graficasRepository.findAll().size();

        // Create the Graficas

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGraficasMockMvc.perform(put("/api/graficas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(graficas)))
            .andExpect(status().isCreated());

        // Validate the Graficas in the database
        List<Graficas> graficasList = graficasRepository.findAll();
        assertThat(graficasList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGraficas() throws Exception {
        // Initialize the database
        graficasRepository.saveAndFlush(graficas);
        int databaseSizeBeforeDelete = graficasRepository.findAll().size();

        // Get the graficas
        restGraficasMockMvc.perform(delete("/api/graficas/{id}", graficas.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Graficas> graficasList = graficasRepository.findAll();
        assertThat(graficasList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Graficas.class);
    }
}
