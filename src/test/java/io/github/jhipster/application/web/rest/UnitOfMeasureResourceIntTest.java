package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.UnitOfMeasure;
import io.github.jhipster.application.repository.UnitOfMeasureRepository;
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
 * Test class for the UnitOfMeasureResource REST controller.
 *
 * @see UnitOfMeasureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class UnitOfMeasureResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;


    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUnitOfMeasureMockMvc;

    private UnitOfMeasure unitOfMeasure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnitOfMeasureResource unitOfMeasureResource = new UnitOfMeasureResource(unitOfMeasureRepository);
        this.restUnitOfMeasureMockMvc = MockMvcBuilders.standaloneSetup(unitOfMeasureResource)
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
    public static UnitOfMeasure createEntity(EntityManager em) {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure()
            .description(DEFAULT_DESCRIPTION);
        return unitOfMeasure;
    }

    @Before
    public void initTest() {
        unitOfMeasure = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnitOfMeasure() throws Exception {
        int databaseSizeBeforeCreate = unitOfMeasureRepository.findAll().size();

        // Create the UnitOfMeasure
        restUnitOfMeasureMockMvc.perform(post("/api/unit-of-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitOfMeasure)))
            .andExpect(status().isCreated());

        // Validate the UnitOfMeasure in the database
        List<UnitOfMeasure> unitOfMeasureList = unitOfMeasureRepository.findAll();
        assertThat(unitOfMeasureList).hasSize(databaseSizeBeforeCreate + 1);
        UnitOfMeasure testUnitOfMeasure = unitOfMeasureList.get(unitOfMeasureList.size() - 1);
        assertThat(testUnitOfMeasure.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createUnitOfMeasureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unitOfMeasureRepository.findAll().size();

        // Create the UnitOfMeasure with an existing ID
        unitOfMeasure.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnitOfMeasureMockMvc.perform(post("/api/unit-of-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitOfMeasure)))
            .andExpect(status().isBadRequest());

        // Validate the UnitOfMeasure in the database
        List<UnitOfMeasure> unitOfMeasureList = unitOfMeasureRepository.findAll();
        assertThat(unitOfMeasureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUnitOfMeasures() throws Exception {
        // Initialize the database
        unitOfMeasureRepository.saveAndFlush(unitOfMeasure);

        // Get all the unitOfMeasureList
        restUnitOfMeasureMockMvc.perform(get("/api/unit-of-measures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unitOfMeasure.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    

    @Test
    @Transactional
    public void getUnitOfMeasure() throws Exception {
        // Initialize the database
        unitOfMeasureRepository.saveAndFlush(unitOfMeasure);

        // Get the unitOfMeasure
        restUnitOfMeasureMockMvc.perform(get("/api/unit-of-measures/{id}", unitOfMeasure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unitOfMeasure.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUnitOfMeasure() throws Exception {
        // Get the unitOfMeasure
        restUnitOfMeasureMockMvc.perform(get("/api/unit-of-measures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnitOfMeasure() throws Exception {
        // Initialize the database
        unitOfMeasureRepository.saveAndFlush(unitOfMeasure);

        int databaseSizeBeforeUpdate = unitOfMeasureRepository.findAll().size();

        // Update the unitOfMeasure
        UnitOfMeasure updatedUnitOfMeasure = unitOfMeasureRepository.findById(unitOfMeasure.getId()).get();
        // Disconnect from session so that the updates on updatedUnitOfMeasure are not directly saved in db
        em.detach(updatedUnitOfMeasure);
        updatedUnitOfMeasure
            .description(UPDATED_DESCRIPTION);

        restUnitOfMeasureMockMvc.perform(put("/api/unit-of-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnitOfMeasure)))
            .andExpect(status().isOk());

        // Validate the UnitOfMeasure in the database
        List<UnitOfMeasure> unitOfMeasureList = unitOfMeasureRepository.findAll();
        assertThat(unitOfMeasureList).hasSize(databaseSizeBeforeUpdate);
        UnitOfMeasure testUnitOfMeasure = unitOfMeasureList.get(unitOfMeasureList.size() - 1);
        assertThat(testUnitOfMeasure.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingUnitOfMeasure() throws Exception {
        int databaseSizeBeforeUpdate = unitOfMeasureRepository.findAll().size();

        // Create the UnitOfMeasure

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUnitOfMeasureMockMvc.perform(put("/api/unit-of-measures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unitOfMeasure)))
            .andExpect(status().isBadRequest());

        // Validate the UnitOfMeasure in the database
        List<UnitOfMeasure> unitOfMeasureList = unitOfMeasureRepository.findAll();
        assertThat(unitOfMeasureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUnitOfMeasure() throws Exception {
        // Initialize the database
        unitOfMeasureRepository.saveAndFlush(unitOfMeasure);

        int databaseSizeBeforeDelete = unitOfMeasureRepository.findAll().size();

        // Get the unitOfMeasure
        restUnitOfMeasureMockMvc.perform(delete("/api/unit-of-measures/{id}", unitOfMeasure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UnitOfMeasure> unitOfMeasureList = unitOfMeasureRepository.findAll();
        assertThat(unitOfMeasureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnitOfMeasure.class);
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId(1L);
        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setId(unitOfMeasure1.getId());
        assertThat(unitOfMeasure1).isEqualTo(unitOfMeasure2);
        unitOfMeasure2.setId(2L);
        assertThat(unitOfMeasure1).isNotEqualTo(unitOfMeasure2);
        unitOfMeasure1.setId(null);
        assertThat(unitOfMeasure1).isNotEqualTo(unitOfMeasure2);
    }
}
