package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Ssd;

import com.mycompany.myapp.repository.SsdRepository;
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
 * REST controller for managing Ssd.
 */
@RestController
@RequestMapping("/api")
public class SsdResource {

    private final Logger log = LoggerFactory.getLogger(SsdResource.class);

    private static final String ENTITY_NAME = "ssd";
        
    private final SsdRepository ssdRepository;

    public SsdResource(SsdRepository ssdRepository) {
        this.ssdRepository = ssdRepository;
    }

    /**
     * POST  /ssds : Create a new ssd.
     *
     * @param ssd the ssd to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ssd, or with status 400 (Bad Request) if the ssd has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ssds")
    @Timed
    public ResponseEntity<Ssd> createSsd(@RequestBody Ssd ssd) throws URISyntaxException {
        log.debug("REST request to save Ssd : {}", ssd);
        if (ssd.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new ssd cannot already have an ID")).body(null);
        }
        Ssd result = ssdRepository.save(ssd);
        return ResponseEntity.created(new URI("/api/ssds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ssds : Updates an existing ssd.
     *
     * @param ssd the ssd to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ssd,
     * or with status 400 (Bad Request) if the ssd is not valid,
     * or with status 500 (Internal Server Error) if the ssd couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ssds")
    @Timed
    public ResponseEntity<Ssd> updateSsd(@RequestBody Ssd ssd) throws URISyntaxException {
        log.debug("REST request to update Ssd : {}", ssd);
        if (ssd.getId() == null) {
            return createSsd(ssd);
        }
        Ssd result = ssdRepository.save(ssd);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ssd.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ssds : get all the ssds.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ssds in body
     */
    @GetMapping("/ssds")
    @Timed
    public List<Ssd> getAllSsds() {
        log.debug("REST request to get all Ssds");
        List<Ssd> ssds = ssdRepository.findAll();
        return ssds;
    }

    /**
     * GET  /ssds/:id : get the "id" ssd.
     *
     * @param id the id of the ssd to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ssd, or with status 404 (Not Found)
     */
    @GetMapping("/ssds/{id}")
    @Timed
    public ResponseEntity<Ssd> getSsd(@PathVariable Long id) {
        log.debug("REST request to get Ssd : {}", id);
        Ssd ssd = ssdRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ssd));
    }

    /**
     * DELETE  /ssds/:id : delete the "id" ssd.
     *
     * @param id the id of the ssd to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ssds/{id}")
    @Timed
    public ResponseEntity<Void> deleteSsd(@PathVariable Long id) {
        log.debug("REST request to delete Ssd : {}", id);
        ssdRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
