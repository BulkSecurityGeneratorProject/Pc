package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Optico;

import com.mycompany.myapp.repository.OpticoRepository;
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
 * REST controller for managing Optico.
 */
@RestController
@RequestMapping("/api")
public class OpticoResource {

    private final Logger log = LoggerFactory.getLogger(OpticoResource.class);

    private static final String ENTITY_NAME = "optico";
        
    private final OpticoRepository opticoRepository;

    public OpticoResource(OpticoRepository opticoRepository) {
        this.opticoRepository = opticoRepository;
    }

    /**
     * POST  /opticos : Create a new optico.
     *
     * @param optico the optico to create
     * @return the ResponseEntity with status 201 (Created) and with body the new optico, or with status 400 (Bad Request) if the optico has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/opticos")
    @Timed
    public ResponseEntity<Optico> createOptico(@RequestBody Optico optico) throws URISyntaxException {
        log.debug("REST request to save Optico : {}", optico);
        if (optico.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new optico cannot already have an ID")).body(null);
        }
        Optico result = opticoRepository.save(optico);
        return ResponseEntity.created(new URI("/api/opticos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /opticos : Updates an existing optico.
     *
     * @param optico the optico to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated optico,
     * or with status 400 (Bad Request) if the optico is not valid,
     * or with status 500 (Internal Server Error) if the optico couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/opticos")
    @Timed
    public ResponseEntity<Optico> updateOptico(@RequestBody Optico optico) throws URISyntaxException {
        log.debug("REST request to update Optico : {}", optico);
        if (optico.getId() == null) {
            return createOptico(optico);
        }
        Optico result = opticoRepository.save(optico);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, optico.getId().toString()))
            .body(result);
    }

    /**
     * GET  /opticos : get all the opticos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of opticos in body
     */
    @GetMapping("/opticos")
    @Timed
    public List<Optico> getAllOpticos() {
        log.debug("REST request to get all Opticos");
        List<Optico> opticos = opticoRepository.findAll();
        return opticos;
    }

    /**
     * GET  /opticos/:id : get the "id" optico.
     *
     * @param id the id of the optico to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the optico, or with status 404 (Not Found)
     */
    @GetMapping("/opticos/{id}")
    @Timed
    public ResponseEntity<Optico> getOptico(@PathVariable Long id) {
        log.debug("REST request to get Optico : {}", id);
        Optico optico = opticoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(optico));
    }

    /**
     * DELETE  /opticos/:id : delete the "id" optico.
     *
     * @param id the id of the optico to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/opticos/{id}")
    @Timed
    public ResponseEntity<Void> deleteOptico(@PathVariable Long id) {
        log.debug("REST request to delete Optico : {}", id);
        opticoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
