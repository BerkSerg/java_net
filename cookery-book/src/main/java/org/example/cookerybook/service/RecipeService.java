package org.example.cookerybook.service;

import org.example.cookerybook.model.Ingredient;
import org.example.cookerybook.model.Recipe;

import java.util.List;

public interface RecipeService {
    void save(Recipe recipe);
    Recipe getRandomRecipe();
    List<Recipe> getAllRecipes();
    List<Recipe> getAllRecipesByIngredients(List<Ingredient> ingredients);
    List<Recipe> getRecipesByRating(Integer rating);
}
