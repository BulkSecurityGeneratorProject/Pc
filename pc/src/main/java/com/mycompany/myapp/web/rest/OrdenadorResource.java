package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Ordenador;

import com.mycompany.myapp.repository.OrdenadorRepository;
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
 * REST controller for managing Ordenador.
 */
@RestController
@RequestMapping("/api")
public class OrdenadorResource {

    private final Logger log = LoggerFactory.getLogger(OrdenadorResource.class);

    private static final String ENTITY_NAME = "ordenador";
        
    private final OrdenadorRepository ordenadorRepository;

    public OrdenadorResource(OrdenadorRepository ordenadorRepository) {
        this.ordenadorRepository = ordenadorRepository;
    }

    /**
     * POST  /ordenadors : Create a new ordenador.
     *
     * @param ordenador the ordenador to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ordenador, or with status 400 (Bad Request) if the ordenador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ordenadors")
    @Timed
    public ResponseEntity<Ordenador> createOrdenador(@RequestBody Ordenador ordenador) throws URISyntaxException {
        log.debug("REST request to save Ordenador : {}", ordenador);
        if (ordenador.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ordenador cannot already have an ID")).body(null);
        }
        Ordenador result = ordenadorRepository.save(ordenador);
        return ResponseEntity.created(new URI("/api/ordenadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ordenadors : Updates an existing ordenador.
     *
     * @param ordenador the ordenador to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ordenador,
     * or with status 400 (Bad Request) if the ordenador is not valid,
     * or with status 500 (Internal Server Error) if the ordenador couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ordenadors")
    @Timed
    public ResponseEntity<Ordenador> updateOrdenador(@RequestBody Ordenador ordenador) throws URISyntaxException {
        log.debug("REST request to update Ordenador : {}", ordenador);
        if (ordenador.getId() == null) {
            return createOrdenador(ordenador);
        }
        Ordenador result = ordenadorRepository.save(ordenador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ordenador.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ordenadors : get all the ordenadors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ordenadors in body
     */
    @GetMapping("/ordenadors")
    @Timed
    public List<Ordenador> getAllOrdenadors() {
        log.debug("REST request to get all Ordenadors");
        List<Ordenador> ordenadors = ordenadorRepository.findAll();
        return ordenadors;
    }

    /**
     * GET  /ordenadors/:id : get the "id" ordenador.
     *
     * @param id the id of the ordenador to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ordenador, or with status 404 (Not Found)
     */
    @GetMapping("/ordenadors/{id}")
    @Timed
    public ResponseEntity<Ordenador> getOrdenador(@PathVariable Long id) {
        log.debug("REST request to get Ordenador : {}", id);
        Ordenador ordenador = ordenadorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ordenador));
    }

    /**
     * DELETE  /ordenadors/:id : delete the "id" ordenador.
     *
     * @param id the id of the ordenador to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ordenadors/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrdenador(@PathVariable Long id) {
        log.debug("REST request to delete Ordenador : {}", id);
        ordenadorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
