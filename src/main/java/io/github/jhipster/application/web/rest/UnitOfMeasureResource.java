package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.UnitOfMeasure;
import io.github.jhipster.application.repository.UnitOfMeasureRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
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
 * REST controller for managing UnitOfMeasure.
 */
@RestController
@RequestMapping("/api")
public class UnitOfMeasureResource {

    private final Logger log = LoggerFactory.getLogger(UnitOfMeasureResource.class);

    private static final String ENTITY_NAME = "unitOfMeasure";

    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public UnitOfMeasureResource(UnitOfMeasureRepository unitOfMeasureRepository) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    /**
     * POST  /unit-of-measures : Create a new unitOfMeasure.
     *
     * @param unitOfMeasure the unitOfMeasure to create
     * @return the ResponseEntity with status 201 (Created) and with body the new unitOfMeasure, or with status 400 (Bad Request) if the unitOfMeasure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/unit-of-measures")
    @Timed
    public ResponseEntity<UnitOfMeasure> createUnitOfMeasure(@RequestBody UnitOfMeasure unitOfMeasure) throws URISyntaxException {
        log.debug("REST request to save UnitOfMeasure : {}", unitOfMeasure);
        if (unitOfMeasure.getId() != null) {
            throw new BadRequestAlertException("A new unitOfMeasure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnitOfMeasure result = unitOfMeasureRepository.save(unitOfMeasure);
        return ResponseEntity.created(new URI("/api/unit-of-measures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /unit-of-measures : Updates an existing unitOfMeasure.
     *
     * @param unitOfMeasure the unitOfMeasure to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated unitOfMeasure,
     * or with status 400 (Bad Request) if the unitOfMeasure is not valid,
     * or with status 500 (Internal Server Error) if the unitOfMeasure couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/unit-of-measures")
    @Timed
    public ResponseEntity<UnitOfMeasure> updateUnitOfMeasure(@RequestBody UnitOfMeasure unitOfMeasure) throws URISyntaxException {
        log.debug("REST request to update UnitOfMeasure : {}", unitOfMeasure);
        if (unitOfMeasure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnitOfMeasure result = unitOfMeasureRepository.save(unitOfMeasure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, unitOfMeasure.getId().toString()))
            .body(result);
    }

    /**
     * GET  /unit-of-measures : get all the unitOfMeasures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of unitOfMeasures in body
     */
    @GetMapping("/unit-of-measures")
    @Timed
    public List<UnitOfMeasure> getAllUnitOfMeasures() {
        log.debug("REST request to get all UnitOfMeasures");
        return unitOfMeasureRepository.findAll();
    }

    /**
     * GET  /unit-of-measures/:id : get the "id" unitOfMeasure.
     *
     * @param id the id of the unitOfMeasure to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the unitOfMeasure, or with status 404 (Not Found)
     */
    @GetMapping("/unit-of-measures/{id}")
    @Timed
    public ResponseEntity<UnitOfMeasure> getUnitOfMeasure(@PathVariable Long id) {
        log.debug("REST request to get UnitOfMeasure : {}", id);
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(unitOfMeasure);
    }

    /**
     * DELETE  /unit-of-measures/:id : delete the "id" unitOfMeasure.
     *
     * @param id the id of the unitOfMeasure to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/unit-of-measures/{id}")
    @Timed
    public ResponseEntity<Void> deleteUnitOfMeasure(@PathVariable Long id) {
        log.debug("REST request to delete UnitOfMeasure : {}", id);

        unitOfMeasureRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
