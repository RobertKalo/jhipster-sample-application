package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Notes;
import io.github.jhipster.application.repository.NotesRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NotesResource REST controller.
 *
 * @see NotesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class NotesResourceIntTest {

    private static final String DEFAULT_RECIPE_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_RECIPE_NOTES = "BBBBBBBBBB";

    @Autowired
    private NotesRepository notesRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restNotesMockMvc;

    private Notes notes;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NotesResource notesResource = new NotesResource(notesRepository);
        this.restNotesMockMvc = MockMvcBuilders.standaloneSetup(notesResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Notes createEntity(EntityManager em) {
        Notes notes = new Notes()
            .recipeNotes(DEFAULT_RECIPE_NOTES);
        return notes;
    }

    @Before
    public void initTest() {
        notes = createEntity(em);
    }

    @Test
    @Transactional
    public void createNotes() throws Exception {
        int databaseSizeBeforeCreate = notesRepository.findAll().size();

        // Create the Notes
        restNotesMockMvc.perform(post("/api/notes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notes)))
            .andExpect(status().isCreated());

        // Validate the Notes in the database
        List<Notes> notesList = notesRepository.findAll();
        assertThat(notesList).hasSize(databaseSizeBeforeCreate + 1);
        Notes testNotes = notesList.get(notesList.size() - 1);
        assertThat(testNotes.getRecipeNotes()).isEqualTo(DEFAULT_RECIPE_NOTES);
    }

    @Test
    @Transactional
    public void createNotesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = notesRepository.findAll().size();

        // Create the Notes with an existing ID
        notes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNotesMockMvc.perform(post("/api/notes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notes)))
            .andExpect(status().isBadRequest());

        // Validate the Notes in the database
        List<Notes> notesList = notesRepository.findAll();
        assertThat(notesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNotes() throws Exception {
        // Initialize the database
        notesRepository.saveAndFlush(notes);

        // Get all the notesList
        restNotesMockMvc.perform(get("/api/notes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(notes.getId().intValue())))
            .andExpect(jsonPath("$.[*].recipeNotes").value(hasItem(DEFAULT_RECIPE_NOTES.toString())));
    }
    

    @Test
    @Transactional
    public void getNotes() throws Exception {
        // Initialize the database
        notesRepository.saveAndFlush(notes);

        // Get the notes
        restNotesMockMvc.perform(get("/api/notes/{id}", notes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(notes.getId().intValue()))
            .andExpect(jsonPath("$.recipeNotes").value(DEFAULT_RECIPE_NOTES.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingNotes() throws Exception {
        // Get the notes
        restNotesMockMvc.perform(get("/api/notes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNotes() throws Exception {
        // Initialize the database
        notesRepository.saveAndFlush(notes);

        int databaseSizeBeforeUpdate = notesRepository.findAll().size();

        // Update the notes
        Notes updatedNotes = notesRepository.findById(notes.getId()).get();
        // Disconnect from session so that the updates on updatedNotes are not directly saved in db
        em.detach(updatedNotes);
        updatedNotes
            .recipeNotes(UPDATED_RECIPE_NOTES);

        restNotesMockMvc.perform(put("/api/notes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNotes)))
            .andExpect(status().isOk());

        // Validate the Notes in the database
        List<Notes> notesList = notesRepository.findAll();
        assertThat(notesList).hasSize(databaseSizeBeforeUpdate);
        Notes testNotes = notesList.get(notesList.size() - 1);
        assertThat(testNotes.getRecipeNotes()).isEqualTo(UPDATED_RECIPE_NOTES);
    }

    @Test
    @Transactional
    public void updateNonExistingNotes() throws Exception {
        int databaseSizeBeforeUpdate = notesRepository.findAll().size();

        // Create the Notes

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restNotesMockMvc.perform(put("/api/notes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(notes)))
            .andExpect(status().isBadRequest());

        // Validate the Notes in the database
        List<Notes> notesList = notesRepository.findAll();
        assertThat(notesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNotes() throws Exception {
        // Initialize the database
        notesRepository.saveAndFlush(notes);

        int databaseSizeBeforeDelete = notesRepository.findAll().size();

        // Get the notes
        restNotesMockMvc.perform(delete("/api/notes/{id}", notes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Notes> notesList = notesRepository.findAll();
        assertThat(notesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notes.class);
        Notes notes1 = new Notes();
        notes1.setId(1L);
        Notes notes2 = new Notes();
        notes2.setId(notes1.getId());
        assertThat(notes1).isEqualTo(notes2);
        notes2.setId(2L);
        assertThat(notes1).isNotEqualTo(notes2);
        notes1.setId(null);
        assertThat(notes1).isNotEqualTo(notes2);
    }
}
