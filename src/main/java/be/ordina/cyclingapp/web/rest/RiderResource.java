package be.ordina.cyclingapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import be.ordina.cyclingapp.domain.Rider;

import be.ordina.cyclingapp.repository.RiderRepository;
import be.ordina.cyclingapp.web.rest.errors.BadRequestAlertException;
import be.ordina.cyclingapp.web.rest.util.HeaderUtil;
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
 * REST controller for managing Rider.
 */
@RestController
@RequestMapping("/api")
public class RiderResource {

    private final Logger log = LoggerFactory.getLogger(RiderResource.class);

    private static final String ENTITY_NAME = "rider";

    private final RiderRepository riderRepository;

    public RiderResource(RiderRepository riderRepository) {
        this.riderRepository = riderRepository;
    }

    /**
     * POST  /riders : Create a new rider.
     *
     * @param rider the rider to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rider, or with status 400 (Bad Request) if the rider has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/riders")
    @Timed
    public ResponseEntity<Rider> createRider(@RequestBody Rider rider) throws URISyntaxException {
        log.debug("REST request to save Rider : {}", rider);
        if (rider.getId() != null) {
            throw new BadRequestAlertException("A new rider cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rider result = riderRepository.save(rider);
        return ResponseEntity.created(new URI("/api/riders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /riders : Updates an existing rider.
     *
     * @param rider the rider to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rider,
     * or with status 400 (Bad Request) if the rider is not valid,
     * or with status 500 (Internal Server Error) if the rider couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/riders")
    @Timed
    public ResponseEntity<Rider> updateRider(@RequestBody Rider rider) throws URISyntaxException {
        log.debug("REST request to update Rider : {}", rider);
        if (rider.getId() == null) {
            return createRider(rider);
        }
        Rider result = riderRepository.save(rider);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rider.getId().toString()))
            .body(result);
    }

    /**
     * GET  /riders : get all the riders.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of riders in body
     */
    @GetMapping("/riders")
    @Timed
    public List<Rider> getAllRiders() {
        log.debug("REST request to get all Riders");
        return riderRepository.findAll();
        }

    /**
     * GET  /riders/:id : get the "id" rider.
     *
     * @param id the id of the rider to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rider, or with status 404 (Not Found)
     */
    @GetMapping("/riders/{id}")
    @Timed
    public ResponseEntity<Rider> getRider(@PathVariable Long id) {
        log.debug("REST request to get Rider : {}", id);
        Rider rider = riderRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(rider));
    }

    /**
     * DELETE  /riders/:id : delete the "id" rider.
     *
     * @param id the id of the rider to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/riders/{id}")
    @Timed
    public ResponseEntity<Void> deleteRider(@PathVariable Long id) {
        log.debug("REST request to delete Rider : {}", id);
        riderRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
