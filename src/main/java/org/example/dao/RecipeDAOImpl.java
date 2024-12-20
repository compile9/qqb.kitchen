package org.example.dao;

import org.example.db.TestConnect;
import org.example.model.Recipe;
import org.example.model.RecipeMapper;
import org.example.model.RecipeDetails;
import org.example.model.RecipeDetailsMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RecipeDAOImpl implements RecipeDAO {
    JdbcTemplate jdbc;

    public RecipeDAOImpl(DataSource dataSource) {
        jdbc = new JdbcTemplate(dataSource);
    }

    public List<Long> getRecipeIdByTagId(Long tag_id) {
        String getRecipeIdByTagIdSql = "SELECT recipe_id FROM recipes_tags WHERE tag_id = ?";
        return jdbc.queryForList(getRecipeIdByTagIdSql, Long.class, tag_id);
    }

    public List<Recipe> getAllRecipes() {
        String getAllRecipesSql = "SELECT * FROM recipes";
        return jdbc.query(getAllRecipesSql, new RecipeMapper());
    }

    public int deleteRecipe(Long id) {
        String deleteRecipeSql = "DELETE FROM recipes WHERE id = ?";
        return jdbc.update(deleteRecipeSql, id);
    }

    public int updateRecipe(Long id, Recipe recipe) {
        String updateRecipeSql = "UPDATE recipes SET title = ?, description = ?, instructions = ?::jsonb, rating = ?, image = ?, duration = ? WHERE id = ?";
        return jdbc.update(updateRecipeSql, recipe.getTitle(), recipe.getDescription(), recipe.getRating(), recipe.getImage(), recipe.getDuration(), id);
    }

    public int createRecipe(Recipe recipe) {
        String insertRecipeSql = "INSERT INTO recipes(title, description, instructions, rating, image, duration) VALUES (?,?,?::jsonb,?,?,?)";
        return jdbc.update(insertRecipeSql, recipe.getTitle(), recipe.getDescription(), recipe.getRating(), recipe.getImage(), recipe.getDuration());
    }

    public List<Recipe> getRecipesBySelectedTags(List<Integer> tagIds) throws SQLException {
        List<Recipe> recipeList = new ArrayList<>();
        String sql = "SELECT get_intersected_recipe_ids(?) AS result";
        try (Connection conn = TestConnect.dataSource().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            Array sqlArray = conn.createArrayOf("INTEGER", tagIds.toArray());
            pstmt.setArray(1, sqlArray);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Array sqlResultArray = rs.getArray("result");
                if (sqlResultArray != null) {
                    Long[] recipe_ids = (Long[]) sqlResultArray.getArray();
                    for (Long recipe_id: recipe_ids) {
                        recipeList.add(getRecipeById(recipe_id));
                    }
                }
            }
            return recipeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public RecipeDetails getRecipeById(Long id) throws SQLException {
        String sql = "SELECT * FROM get_recipe_with_details(?)";
        return jdbc.queryForObject(sql, new RecipeDetailsMapper(), id);
    }

}