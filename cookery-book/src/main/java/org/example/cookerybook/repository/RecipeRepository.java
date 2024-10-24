package org.example.cookerybook.repository;

import org.example.cookerybook.model.Ingredient;
import org.example.cookerybook.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long>, RecipeRepositoryCustom<Recipe> {
    List<Recipe> findAllByRecipeIngredientIn(List<Ingredient> ingredients);
    List<Recipe> findAllByRatingGreaterThan(int rating);
}
