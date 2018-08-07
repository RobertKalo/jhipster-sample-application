package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.JhipsterSampleApplicationApp;

import io.github.jhipster.application.domain.Recipe;
import io.github.jhipster.application.repository.RecipeRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.Difficulty;
/**
 * Test class for the RecipeResource REST controller.
 *
 * @see RecipeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
public class RecipeResourceIntTest {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_PREP_TIME = 1;
    private static final Integer UPDATED_PREP_TIME = 2;

    private static final Integer DEFAULT_COOK_TIME = 1;
    private static final Integer UPDATED_COOK_TIME = 2;

    private static final Integer DEFAULT_SERVINGS = 1;
    private static final Integer UPDATED_SERVINGS = 2;

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECTIONS = "AAAAAAAAAA";
    private static final String UPDATED_DIRECTIONS = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Difficulty DEFAULT_DIFFICULTY = Difficulty.EASY;
    private static final Difficulty UPDATED_DIFFICULTY = Difficulty.MEDIUM;

    @Autowired
    private RecipeRepository recipeRepository;
    @Mock
    private RecipeRepository recipeRepositoryMock;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRecipeMockMvc;

    private Recipe recipe;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RecipeResource recipeResource = new RecipeResource(recipeRepository);
        this.restRecipeMockMvc = MockMvcBuilders.standaloneSetup(recipeResource)
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
    public static Recipe createEntity(EntityManager em) {
        Recipe recipe = new Recipe()
            .description(DEFAULT_DESCRIPTION)
            .prepTime(DEFAULT_PREP_TIME)
            .cookTime(DEFAULT_COOK_TIME)
            .servings(DEFAULT_SERVINGS)
            .source(DEFAULT_SOURCE)
            .url(DEFAULT_URL)
            .directions(DEFAULT_DIRECTIONS)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .difficulty(DEFAULT_DIFFICULTY);
        return recipe;
    }

    @Before
    public void initTest() {
        recipe = createEntity(em);
    }

    @Test
    @Transactional
    public void createRecipe() throws Exception {
        int databaseSizeBeforeCreate = recipeRepository.findAll().size();

        // Create the Recipe
        restRecipeMockMvc.perform(post("/api/recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipe)))
            .andExpect(status().isCreated());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeCreate + 1);
        Recipe testRecipe = recipeList.get(recipeList.size() - 1);
        assertThat(testRecipe.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testRecipe.getPrepTime()).isEqualTo(DEFAULT_PREP_TIME);
        assertThat(testRecipe.getCookTime()).isEqualTo(DEFAULT_COOK_TIME);
        assertThat(testRecipe.getServings()).isEqualTo(DEFAULT_SERVINGS);
        assertThat(testRecipe.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testRecipe.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testRecipe.getDirections()).isEqualTo(DEFAULT_DIRECTIONS);
        assertThat(testRecipe.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testRecipe.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
        assertThat(testRecipe.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
    }

    @Test
    @Transactional
    public void createRecipeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = recipeRepository.findAll().size();

        // Create the Recipe with an existing ID
        recipe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRecipeMockMvc.perform(post("/api/recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipe)))
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRecipes() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get all the recipeList
        restRecipeMockMvc.perform(get("/api/recipes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(recipe.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].prepTime").value(hasItem(DEFAULT_PREP_TIME)))
            .andExpect(jsonPath("$.[*].cookTime").value(hasItem(DEFAULT_COOK_TIME)))
            .andExpect(jsonPath("$.[*].servings").value(hasItem(DEFAULT_SERVINGS)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
            .andExpect(jsonPath("$.[*].directions").value(hasItem(DEFAULT_DIRECTIONS.toString())))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].difficulty").value(hasItem(DEFAULT_DIFFICULTY.toString())));
    }
    
    public void getAllRecipesWithEagerRelationshipsIsEnabled() throws Exception {
        RecipeResource recipeResource = new RecipeResource(recipeRepositoryMock);
        when(recipeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restRecipeMockMvc = MockMvcBuilders.standaloneSetup(recipeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRecipeMockMvc.perform(get("/api/recipes?eagerload=true"))
        .andExpect(status().isOk());

        verify(recipeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    public void getAllRecipesWithEagerRelationshipsIsNotEnabled() throws Exception {
        RecipeResource recipeResource = new RecipeResource(recipeRepositoryMock);
            when(recipeRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restRecipeMockMvc = MockMvcBuilders.standaloneSetup(recipeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restRecipeMockMvc.perform(get("/api/recipes?eagerload=true"))
        .andExpect(status().isOk());

            verify(recipeRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getRecipe() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        // Get the recipe
        restRecipeMockMvc.perform(get("/api/recipes/{id}", recipe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(recipe.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.prepTime").value(DEFAULT_PREP_TIME))
            .andExpect(jsonPath("$.cookTime").value(DEFAULT_COOK_TIME))
            .andExpect(jsonPath("$.servings").value(DEFAULT_SERVINGS))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.directions").value(DEFAULT_DIRECTIONS.toString()))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.difficulty").value(DEFAULT_DIFFICULTY.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingRecipe() throws Exception {
        // Get the recipe
        restRecipeMockMvc.perform(get("/api/recipes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRecipe() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();

        // Update the recipe
        Recipe updatedRecipe = recipeRepository.findById(recipe.getId()).get();
        // Disconnect from session so that the updates on updatedRecipe are not directly saved in db
        em.detach(updatedRecipe);
        updatedRecipe
            .description(UPDATED_DESCRIPTION)
            .prepTime(UPDATED_PREP_TIME)
            .cookTime(UPDATED_COOK_TIME)
            .servings(UPDATED_SERVINGS)
            .source(UPDATED_SOURCE)
            .url(UPDATED_URL)
            .directions(UPDATED_DIRECTIONS)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .difficulty(UPDATED_DIFFICULTY);

        restRecipeMockMvc.perform(put("/api/recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRecipe)))
            .andExpect(status().isOk());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
        Recipe testRecipe = recipeList.get(recipeList.size() - 1);
        assertThat(testRecipe.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testRecipe.getPrepTime()).isEqualTo(UPDATED_PREP_TIME);
        assertThat(testRecipe.getCookTime()).isEqualTo(UPDATED_COOK_TIME);
        assertThat(testRecipe.getServings()).isEqualTo(UPDATED_SERVINGS);
        assertThat(testRecipe.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testRecipe.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testRecipe.getDirections()).isEqualTo(UPDATED_DIRECTIONS);
        assertThat(testRecipe.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testRecipe.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
        assertThat(testRecipe.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
    }

    @Test
    @Transactional
    public void updateNonExistingRecipe() throws Exception {
        int databaseSizeBeforeUpdate = recipeRepository.findAll().size();

        // Create the Recipe

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRecipeMockMvc.perform(put("/api/recipes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(recipe)))
            .andExpect(status().isBadRequest());

        // Validate the Recipe in the database
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRecipe() throws Exception {
        // Initialize the database
        recipeRepository.saveAndFlush(recipe);

        int databaseSizeBeforeDelete = recipeRepository.findAll().size();

        // Get the recipe
        restRecipeMockMvc.perform(delete("/api/recipes/{id}", recipe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Recipe> recipeList = recipeRepository.findAll();
        assertThat(recipeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Recipe.class);
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        Recipe recipe2 = new Recipe();
        recipe2.setId(recipe1.getId());
        assertThat(recipe1).isEqualTo(recipe2);
        recipe2.setId(2L);
        assertThat(recipe1).isNotEqualTo(recipe2);
        recipe1.setId(null);
        assertThat(recipe1).isNotEqualTo(recipe2);
    }
}
