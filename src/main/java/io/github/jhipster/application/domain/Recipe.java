package io.github.jhipster.application.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.jhipster.application.domain.enumeration.Difficulty;

/**
 * A Recipe.
 */
@Entity
@Table(name = "recipe")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "prep_time")
    private Integer prepTime;

    @Column(name = "cook_time")
    private Integer cookTime;

    @Column(name = "servings")
    private Integer servings;

    @Column(name = "source")
    private String source;

    @Column(name = "url")
    private String url;

    @Column(name = "directions")
    private String directions;

    @Lob
    @Column(name = "image")
    private byte[] image;

    @Column(name = "image_content_type")
    private String imageContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private Difficulty difficulty;

    @OneToOne
    @JoinColumn(unique = true)
    private Notes notes;

    @OneToMany(mappedBy = "recipe")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "recipe_category",
               joinColumns = @JoinColumn(name = "recipes_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "categories_id", referencedColumnName = "id"))
    private Set<Category> categories = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public Recipe description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public Recipe prepTime(Integer prepTime) {
        this.prepTime = prepTime;
        return this;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public Integer getCookTime() {
        return cookTime;
    }

    public Recipe cookTime(Integer cookTime) {
        this.cookTime = cookTime;
        return this;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public Integer getServings() {
        return servings;
    }

    public Recipe servings(Integer servings) {
        this.servings = servings;
        return this;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getSource() {
        return source;
    }

    public Recipe source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public Recipe url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDirections() {
        return directions;
    }

    public Recipe directions(String directions) {
        this.directions = directions;
        return this;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public byte[] getImage() {
        return image;
    }

    public Recipe image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Recipe imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public Recipe difficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        return this;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Notes getNotes() {
        return notes;
    }

    public Recipe notes(Notes notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public Recipe ingredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public Recipe addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
        ingredient.setRecipe(this);
        return this;
    }

    public Recipe removeIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
        ingredient.setRecipe(null);
        return this;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Recipe categories(Set<Category> categories) {
        this.categories = categories;
        return this;
    }

    public Recipe addCategory(Category category) {
        this.categories.add(category);
        category.getRecipes().add(this);
        return this;
    }

    public Recipe removeCategory(Category category) {
        this.categories.remove(category);
        category.getRecipes().remove(this);
        return this;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Recipe recipe = (Recipe) o;
        if (recipe.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recipe.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Recipe{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", prepTime=" + getPrepTime() +
            ", cookTime=" + getCookTime() +
            ", servings=" + getServings() +
            ", source='" + getSource() + "'" +
            ", url='" + getUrl() + "'" +
            ", directions='" + getDirections() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", difficulty='" + getDifficulty() + "'" +
            "}";
    }
}
