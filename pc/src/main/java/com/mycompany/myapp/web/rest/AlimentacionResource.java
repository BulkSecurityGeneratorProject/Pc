package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Alimentacion;

import com.mycompany.myapp.repository.AlimentacionRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Alimentacion.
 */
@RestController
@RequestMapping("/api")
public class AlimentacionResource {

    private final Logger log = LoggerFactory.getLogger(AlimentacionResource.class);

    private static final String ENTITY_NAME = "alimentacion";
        
    private final AlimentacionRepository alimentacionRepository;

    public AlimentacionResource(AlimentacionRepository alimentacionRepository) {
        this.alimentacionRepository = alimentacionRepository;
    }

    /**
     * POST  /alimentacions : Create a new alimentacion.
     *
     * @param alimentacion the alimentacion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alimentacion, or with status 400 (Bad Request) if the alimentacion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alimentacions")
    @Timed
    public ResponseEntity<Alimentacion> createAlimentacion(@RequestBody Alimentacion alimentacion) throws URISyntaxException {
        log.debug("REST request to save Alimentacion : {}", alimentacion);
        if (alimentacion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new alimentacion cannot already have an ID")).body(null);
        }
        Alimentacion result = alimentacionRepository.save(alimentacion);
        return ResponseEntity.created(new URI("/api/alimentacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alimentacions : Updates an existing alimentacion.
     *
     * @param alimentacion the alimentacion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alimentacion,
     * or with status 400 (Bad Request) if the alimentacion is not valid,
     * or with status 500 (Internal Server Error) if the alimentacion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alimentacions")
    @Timed
    public ResponseEntity<Alimentacion> updateAlimentacion(@RequestBody Alimentacion alimentacion) throws URISyntaxException {
        log.debug("REST request to update Alimentacion : {}", alimentacion);
        if (alimentacion.getId() == null) {
            return createAlimentacion(alimentacion);
        }
        Alimentacion result = alimentacionRepository.save(alimentacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, alimentacion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alimentacions : get all the alimentacions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of alimentacions in body
     */
    @GetMapping("/alimentacions")
    @Timed
    public List<Alimentacion> getAllAlimentacions() {
        log.debug("REST request to get all Alimentacions");
        List<Alimentacion> alimentacions = alimentacionRepository.findAll();
        return alimentacions;
    }

    /**
     * GET  /alimentacions/:id : get the "id" alimentacion.
     *
     * @param id the id of the alimentacion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alimentacion, or with status 404 (Not Found)
     */
    @GetMapping("/alimentacions/{id}")
    @Timed
    public ResponseEntity<Alimentacion> getAlimentacion(@PathVariable Long id) {
        log.debug("REST request to get Alimentacion : {}", id);
        Alimentacion alimentacion = alimentacionRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(alimentacion));
    }

    /**
     * DELETE  /alimentacions/:id : delete the "id" alimentacion.
     *
     * @param id the id of the alimentacion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alimentacions/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlimentacion(@PathVariable Long id) {
        log.debug("REST request to delete Alimentacion : {}", id);
        alimentacionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
