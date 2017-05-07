package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Procesador;

import com.mycompany.myapp.repository.ProcesadorRepository;
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
 * REST controller for managing Procesador.
 */
@RestController
@RequestMapping("/api")
public class ProcesadorResource {

    private final Logger log = LoggerFactory.getLogger(ProcesadorResource.class);

    private static final String ENTITY_NAME = "procesador";
        
    private final ProcesadorRepository procesadorRepository;

    public ProcesadorResource(ProcesadorRepository procesadorRepository) {
        this.procesadorRepository = procesadorRepository;
    }

    /**
     * POST  /procesadors : Create a new procesador.
     *
     * @param procesador the procesador to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procesador, or with status 400 (Bad Request) if the procesador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/procesadors")
    @Timed
    public ResponseEntity<Procesador> createProcesador(@RequestBody Procesador procesador) throws URISyntaxException {
        log.debug("REST request to save Procesador : {}", procesador);
        if (procesador.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new procesador cannot already have an ID")).body(null);
        }
        Procesador result = procesadorRepository.save(procesador);
        return ResponseEntity.created(new URI("/api/procesadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /procesadors : Updates an existing procesador.
     *
     * @param procesador the procesador to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated procesador,
     * or with status 400 (Bad Request) if the procesador is not valid,
     * or with status 500 (Internal Server Error) if the procesador couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/procesadors")
    @Timed
    public ResponseEntity<Procesador> updateProcesador(@RequestBody Procesador procesador) throws URISyntaxException {
        log.debug("REST request to update Procesador : {}", procesador);
        if (procesador.getId() == null) {
            return createProcesador(procesador);
        }
        Procesador result = procesadorRepository.save(procesador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, procesador.getId().toString()))
            .body(result);
    }

    /**
     * GET  /procesadors : get all the procesadors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of procesadors in body
     */
    @GetMapping("/procesadors")
    @Timed
    public List<Procesador> getAllProcesadors() {
        log.debug("REST request to get all Procesadors");
        List<Procesador> procesadors = procesadorRepository.findAll();
        return procesadors;
    }

    /**
     * GET  /procesadors/:id : get the "id" procesador.
     *
     * @param id the id of the procesador to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the procesador, or with status 404 (Not Found)
     */
    @GetMapping("/procesadors/{id}")
    @Timed
    public ResponseEntity<Procesador> getProcesador(@PathVariable Long id) {
        log.debug("REST request to get Procesador : {}", id);
        Procesador procesador = procesadorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(procesador));
    }

    /**
     * DELETE  /procesadors/:id : delete the "id" procesador.
     *
     * @param id the id of the procesador to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/procesadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteProcesador(@PathVariable Long id) {
        log.debug("REST request to delete Procesador : {}", id);
        procesadorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
