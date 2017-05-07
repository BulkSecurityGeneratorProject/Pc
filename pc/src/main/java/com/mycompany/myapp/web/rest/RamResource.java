package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Ram;

import com.mycompany.myapp.repository.RamRepository;
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
 * REST controller for managing Ram.
 */
@RestController
@RequestMapping("/api")
public class RamResource {

    private final Logger log = LoggerFactory.getLogger(RamResource.class);

    private static final String ENTITY_NAME = "ram";
        
    private final RamRepository ramRepository;

    public RamResource(RamRepository ramRepository) {
        this.ramRepository = ramRepository;
    }

    /**
     * POST  /rams : Create a new ram.
     *
     * @param ram the ram to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ram, or with status 400 (Bad Request) if the ram has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rams")
    @Timed
    public ResponseEntity<Ram> createRam(@RequestBody Ram ram) throws URISyntaxException {
        log.debug("REST request to save Ram : {}", ram);
        if (ram.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ram cannot already have an ID")).body(null);
        }
        Ram result = ramRepository.save(ram);
        return ResponseEntity.created(new URI("/api/rams/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rams : Updates an existing ram.
     *
     * @param ram the ram to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ram,
     * or with status 400 (Bad Request) if the ram is not valid,
     * or with status 500 (Internal Server Error) if the ram couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rams")
    @Timed
    public ResponseEntity<Ram> updateRam(@RequestBody Ram ram) throws URISyntaxException {
        log.debug("REST request to update Ram : {}", ram);
        if (ram.getId() == null) {
            return createRam(ram);
        }
        Ram result = ramRepository.save(ram);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ram.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rams : get all the rams.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rams in body
     */
    @GetMapping("/rams")
    @Timed
    public List<Ram> getAllRams() {
        log.debug("REST request to get all Rams");
        List<Ram> rams = ramRepository.findAll();
        return rams;
    }

    /**
     * GET  /rams/:id : get the "id" ram.
     *
     * @param id the id of the ram to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ram, or with status 404 (Not Found)
     */
    @GetMapping("/rams/{id}")
    @Timed
    public ResponseEntity<Ram> getRam(@PathVariable Long id) {
        log.debug("REST request to get Ram : {}", id);
        Ram ram = ramRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ram));
    }

    /**
     * DELETE  /rams/:id : delete the "id" ram.
     *
     * @param id the id of the ram to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rams/{id}")
    @Timed
    public ResponseEntity<Void> deleteRam(@PathVariable Long id) {
        log.debug("REST request to delete Ram : {}", id);
        ramRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
