package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Placa;

import com.mycompany.myapp.repository.PlacaRepository;
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
 * REST controller for managing Placa.
 */
@RestController
@RequestMapping("/api")
public class PlacaResource {

    private final Logger log = LoggerFactory.getLogger(PlacaResource.class);

    private static final String ENTITY_NAME = "placa";
        
    private final PlacaRepository placaRepository;

    public PlacaResource(PlacaRepository placaRepository) {
        this.placaRepository = placaRepository;
    }

    /**
     * POST  /placas : Create a new placa.
     *
     * @param placa the placa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new placa, or with status 400 (Bad Request) if the placa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/placas")
    @Timed
    public ResponseEntity<Placa> createPlaca(@RequestBody Placa placa) throws URISyntaxException {
        log.debug("REST request to save Placa : {}", placa);
        if (placa.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new placa cannot already have an ID")).body(null);
        }
        Placa result = placaRepository.save(placa);
        return ResponseEntity.created(new URI("/api/placas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /placas : Updates an existing placa.
     *
     * @param placa the placa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated placa,
     * or with status 400 (Bad Request) if the placa is not valid,
     * or with status 500 (Internal Server Error) if the placa couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/placas")
    @Timed
    public ResponseEntity<Placa> updatePlaca(@RequestBody Placa placa) throws URISyntaxException {
        log.debug("REST request to update Placa : {}", placa);
        if (placa.getId() == null) {
            return createPlaca(placa);
        }
        Placa result = placaRepository.save(placa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, placa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /placas : get all the placas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of placas in body
     */
    @GetMapping("/placas")
    @Timed
    public List<Placa> getAllPlacas() {
        log.debug("REST request to get all Placas");
        List<Placa> placas = placaRepository.findAll();
        return placas;
    }

    /**
     * GET  /placas/:id : get the "id" placa.
     *
     * @param id the id of the placa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the placa, or with status 404 (Not Found)
     */
    @GetMapping("/placas/{id}")
    @Timed
    public ResponseEntity<Placa> getPlaca(@PathVariable Long id) {
        log.debug("REST request to get Placa : {}", id);
        Placa placa = placaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(placa));
    }

    /**
     * DELETE  /placas/:id : delete the "id" placa.
     *
     * @param id the id of the placa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/placas/{id}")
    @Timed
    public ResponseEntity<Void> deletePlaca(@PathVariable Long id) {
        log.debug("REST request to delete Placa : {}", id);
        placaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
