package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Fabricante;

import com.mycompany.myapp.repository.FabricanteRepository;
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
 * REST controller for managing Fabricante.
 */
@RestController
@RequestMapping("/api")
public class FabricanteResource {

    private final Logger log = LoggerFactory.getLogger(FabricanteResource.class);

    private static final String ENTITY_NAME = "fabricante";
        
    private final FabricanteRepository fabricanteRepository;

    public FabricanteResource(FabricanteRepository fabricanteRepository) {
        this.fabricanteRepository = fabricanteRepository;
    }

    /**
     * POST  /fabricantes : Create a new fabricante.
     *
     * @param fabricante the fabricante to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fabricante, or with status 400 (Bad Request) if the fabricante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fabricantes")
    @Timed
    public ResponseEntity<Fabricante> createFabricante(@RequestBody Fabricante fabricante) throws URISyntaxException {
        log.debug("REST request to save Fabricante : {}", fabricante);
        if (fabricante.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new fabricante cannot already have an ID")).body(null);
        }
        Fabricante result = fabricanteRepository.save(fabricante);
        return ResponseEntity.created(new URI("/api/fabricantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fabricantes : Updates an existing fabricante.
     *
     * @param fabricante the fabricante to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fabricante,
     * or with status 400 (Bad Request) if the fabricante is not valid,
     * or with status 500 (Internal Server Error) if the fabricante couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fabricantes")
    @Timed
    public ResponseEntity<Fabricante> updateFabricante(@RequestBody Fabricante fabricante) throws URISyntaxException {
        log.debug("REST request to update Fabricante : {}", fabricante);
        if (fabricante.getId() == null) {
            return createFabricante(fabricante);
        }
        Fabricante result = fabricanteRepository.save(fabricante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, fabricante.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fabricantes : get all the fabricantes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of fabricantes in body
     */
    @GetMapping("/fabricantes")
    @Timed
    public List<Fabricante> getAllFabricantes() {
        log.debug("REST request to get all Fabricantes");
        List<Fabricante> fabricantes = fabricanteRepository.findAll();
        return fabricantes;
    }

    /**
     * GET  /fabricantes/:id : get the "id" fabricante.
     *
     * @param id the id of the fabricante to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fabricante, or with status 404 (Not Found)
     */
    @GetMapping("/fabricantes/{id}")
    @Timed
    public ResponseEntity<Fabricante> getFabricante(@PathVariable Long id) {
        log.debug("REST request to get Fabricante : {}", id);
        Fabricante fabricante = fabricanteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(fabricante));
    }

    /**
     * DELETE  /fabricantes/:id : delete the "id" fabricante.
     *
     * @param id the id of the fabricante to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fabricantes/{id}")
    @Timed
    public ResponseEntity<Void> deleteFabricante(@PathVariable Long id) {
        log.debug("REST request to delete Fabricante : {}", id);
        fabricanteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
