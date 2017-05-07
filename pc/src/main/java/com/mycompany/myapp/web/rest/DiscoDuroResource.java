package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.DiscoDuro;

import com.mycompany.myapp.repository.DiscoDuroRepository;
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
 * REST controller for managing DiscoDuro.
 */
@RestController
@RequestMapping("/api")
public class DiscoDuroResource {

    private final Logger log = LoggerFactory.getLogger(DiscoDuroResource.class);

    private static final String ENTITY_NAME = "discoDuro";
        
    private final DiscoDuroRepository discoDuroRepository;

    public DiscoDuroResource(DiscoDuroRepository discoDuroRepository) {
        this.discoDuroRepository = discoDuroRepository;
    }

    /**
     * POST  /disco-duros : Create a new discoDuro.
     *
     * @param discoDuro the discoDuro to create
     * @return the ResponseEntity with status 201 (Created) and with body the new discoDuro, or with status 400 (Bad Request) if the discoDuro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/disco-duros")
    @Timed
    public ResponseEntity<DiscoDuro> createDiscoDuro(@RequestBody DiscoDuro discoDuro) throws URISyntaxException {
        log.debug("REST request to save DiscoDuro : {}", discoDuro);
        if (discoDuro.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new discoDuro cannot already have an ID")).body(null);
        }
        DiscoDuro result = discoDuroRepository.save(discoDuro);
        return ResponseEntity.created(new URI("/api/disco-duros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /disco-duros : Updates an existing discoDuro.
     *
     * @param discoDuro the discoDuro to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated discoDuro,
     * or with status 400 (Bad Request) if the discoDuro is not valid,
     * or with status 500 (Internal Server Error) if the discoDuro couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/disco-duros")
    @Timed
    public ResponseEntity<DiscoDuro> updateDiscoDuro(@RequestBody DiscoDuro discoDuro) throws URISyntaxException {
        log.debug("REST request to update DiscoDuro : {}", discoDuro);
        if (discoDuro.getId() == null) {
            return createDiscoDuro(discoDuro);
        }
        DiscoDuro result = discoDuroRepository.save(discoDuro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, discoDuro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /disco-duros : get all the discoDuros.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of discoDuros in body
     */
    @GetMapping("/disco-duros")
    @Timed
    public List<DiscoDuro> getAllDiscoDuros() {
        log.debug("REST request to get all DiscoDuros");
        List<DiscoDuro> discoDuros = discoDuroRepository.findAll();
        return discoDuros;
    }

    /**
     * GET  /disco-duros/:id : get the "id" discoDuro.
     *
     * @param id the id of the discoDuro to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the discoDuro, or with status 404 (Not Found)
     */
    @GetMapping("/disco-duros/{id}")
    @Timed
    public ResponseEntity<DiscoDuro> getDiscoDuro(@PathVariable Long id) {
        log.debug("REST request to get DiscoDuro : {}", id);
        DiscoDuro discoDuro = discoDuroRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(discoDuro));
    }

    /**
     * DELETE  /disco-duros/:id : delete the "id" discoDuro.
     *
     * @param id the id of the discoDuro to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/disco-duros/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiscoDuro(@PathVariable Long id) {
        log.debug("REST request to delete DiscoDuro : {}", id);
        discoDuroRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
