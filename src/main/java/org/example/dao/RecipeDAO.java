package org.example.dao;

import org.example.model.Recipe;

import java.sql.SQLException;
import java.util.List;
public interface RecipeDAO {
    Recipe getRecipeById(Long id) throws SQLException;
    List<Recipe> getAllRecipes();
    int deleteRecipe(Long id);
    int updateRecipe(Long id, Recipe recipe);
    int createRecipe(Recipe recipe);
}
