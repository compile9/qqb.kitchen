package org.example.model;

import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecipeDetailsMapper implements RowMapper<RecipeDetails> {
    private final RecipeMapper recipeMapper = new RecipeMapper();

    @Override
    public RecipeDetails mapRow(@NonNull ResultSet resultSet, int rowNum) throws SQLException {
        Recipe baseRecipe = recipeMapper.mapRow(resultSet, rowNum);
        RecipeDetails recipeDetails = new RecipeDetails();

        BeanUtils.copyProperties(baseRecipe, recipeDetails);

        Array tagsArray = resultSet.getArray("tags");
        Array instructionsArray = resultSet.getArray("instructions");
        Array ingredientsArray = resultSet.getArray("ingredients");

        recipeDetails.setTags((String[]) tagsArray.getArray());
        recipeDetails.setInstructions((String[]) instructionsArray.getArray());
        recipeDetails.setIngredients((String[]) ingredientsArray.getArray());

        return recipeDetails;
    }
}
