package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Graficas;

import com.mycompany.myapp.repository.GraficasRepository;
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
 * REST controller for managing Graficas.
 */
@RestController
@RequestMapping("/api")
public class GraficasResource {

    private final Logger log = LoggerFactory.getLogger(GraficasResource.class);

    private static final String ENTITY_NAME = "graficas";
        
    private final GraficasRepository graficasRepository;

    public GraficasResource(GraficasRepository graficasRepository) {
        this.graficasRepository = graficasRepository;
    }

    /**
     * POST  /graficas : Create a new graficas.
     *
     * @param graficas the graficas to create
     * @return the ResponseEntity with status 201 (Created) and with body the new graficas, or with status 400 (Bad Request) if the graficas has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/graficas")
    @Timed
    public ResponseEntity<Graficas> createGraficas(@RequestBody Graficas graficas) throws URISyntaxException {
        log.debug("REST request to save Graficas : {}", graficas);
        if (graficas.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new graficas cannot already have an ID")).body(null);
        }
        Graficas result = graficasRepository.save(graficas);
        return ResponseEntity.created(new URI("/api/graficas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /graficas : Updates an existing graficas.
     *
     * @param graficas the graficas to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated graficas,
     * or with status 400 (Bad Request) if the graficas is not valid,
     * or with status 500 (Internal Server Error) if the graficas couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/graficas")
    @Timed
    public ResponseEntity<Graficas> updateGraficas(@RequestBody Graficas graficas) throws URISyntaxException {
        log.debug("REST request to update Graficas : {}", graficas);
        if (graficas.getId() == null) {
            return createGraficas(graficas);
        }
        Graficas result = graficasRepository.save(graficas);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, graficas.getId().toString()))
            .body(result);
    }

    /**
     * GET  /graficas : get all the graficas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of graficas in body
     */
    @GetMapping("/graficas")
    @Timed
    public List<Graficas> getAllGraficas() {
        log.debug("REST request to get all Graficas");
        List<Graficas> graficas = graficasRepository.findAll();
        return graficas;
    }

    /**
     * GET  /graficas/:id : get the "id" graficas.
     *
     * @param id the id of the graficas to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the graficas, or with status 404 (Not Found)
     */
    @GetMapping("/graficas/{id}")
    @Timed
    public ResponseEntity<Graficas> getGraficas(@PathVariable Long id) {
        log.debug("REST request to get Graficas : {}", id);
        Graficas graficas = graficasRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(graficas));
    }

    /**
     * DELETE  /graficas/:id : delete the "id" graficas.
     *
     * @param id the id of the graficas to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/graficas/{id}")
    @Timed
    public ResponseEntity<Void> deleteGraficas(@PathVariable Long id) {
        log.debug("REST request to delete Graficas : {}", id);
        graficasRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
