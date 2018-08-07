package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Notes;
import io.github.jhipster.application.repository.NotesRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Notes.
 */
@RestController
@RequestMapping("/api")
public class NotesResource {

    private final Logger log = LoggerFactory.getLogger(NotesResource.class);

    private static final String ENTITY_NAME = "notes";

    private final NotesRepository notesRepository;

    public NotesResource(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    /**
     * POST  /notes : Create a new notes.
     *
     * @param notes the notes to create
     * @return the ResponseEntity with status 201 (Created) and with body the new notes, or with status 400 (Bad Request) if the notes has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/notes")
    @Timed
    public ResponseEntity<Notes> createNotes(@RequestBody Notes notes) throws URISyntaxException {
        log.debug("REST request to save Notes : {}", notes);
        if (notes.getId() != null) {
            throw new BadRequestAlertException("A new notes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Notes result = notesRepository.save(notes);
        return ResponseEntity.created(new URI("/api/notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notes : Updates an existing notes.
     *
     * @param notes the notes to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated notes,
     * or with status 400 (Bad Request) if the notes is not valid,
     * or with status 500 (Internal Server Error) if the notes couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/notes")
    @Timed
    public ResponseEntity<Notes> updateNotes(@RequestBody Notes notes) throws URISyntaxException {
        log.debug("REST request to update Notes : {}", notes);
        if (notes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Notes result = notesRepository.save(notes);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, notes.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notes : get all the notes.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of notes in body
     */
    @GetMapping("/notes")
    @Timed
    public List<Notes> getAllNotes(@RequestParam(required = false) String filter) {
        if ("recipe-is-null".equals(filter)) {
            log.debug("REST request to get all Notess where recipe is null");
            return StreamSupport
                .stream(notesRepository.findAll().spliterator(), false)
                .filter(notes -> notes.getRecipe() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Notes");
        return notesRepository.findAll();
    }

    /**
     * GET  /notes/:id : get the "id" notes.
     *
     * @param id the id of the notes to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the notes, or with status 404 (Not Found)
     */
    @GetMapping("/notes/{id}")
    @Timed
    public ResponseEntity<Notes> getNotes(@PathVariable Long id) {
        log.debug("REST request to get Notes : {}", id);
        Optional<Notes> notes = notesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(notes);
    }

    /**
     * DELETE  /notes/:id : delete the "id" notes.
     *
     * @param id the id of the notes to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/notes/{id}")
    @Timed
    public ResponseEntity<Void> deleteNotes(@PathVariable Long id) {
        log.debug("REST request to delete Notes : {}", id);

        notesRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
