package org.example.controller;

import org.example.model.Recipe;
import org.example.model.RecipeDetails;
import org.example.service.RecipeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public RecipeDetails fetchRecipeById(@PathVariable Long id) throws SQLException {
        return recipeService.findRecipeById(id);
    }

    @GetMapping("/ids")
    public List<Long> fetchRecipeIdByTagId(@RequestParam("tag_id") Long tagId) {
        return recipeService.findRecipeIdByTagId(tagId);
    }

    @GetMapping
    public List<Recipe> fetchAllRecipes() {
        return recipeService.findAllRecipes();
    }

    @Value("${TAG_IDS}")
    private String tagIds;
    @GetMapping("/today")
    public List<Recipe> fetchTodayRecipes() throws SQLException {
        List<String> tagIdList = Arrays.asList(tagIds.split(","));
        return recipeService.findTodayRecipes(tagIdList);
    }

    @GetMapping("/tags")
    public List<Recipe> getQueryParameters(@RequestParam("selected") String selectedTags) throws SQLException {
        String[] tagIdsArrayStr = selectedTags.split("\\|");
        List<Integer> tagIdsArrayNum = new ArrayList<>();
        for (String tagIdStr: tagIdsArrayStr) {
            tagIdsArrayNum.add(Integer.parseInt(tagIdStr));
        }
        return recipeService.findRecipesBySelectedTags(tagIdsArrayNum);
    }

    @PostMapping
    public int postRecipe(@RequestBody Recipe recipe) {
        return recipeService.addRecipe(recipe);
    }

    @PutMapping("/{id}")
    public int alterRecipe(@PathVariable("id") Long id, @RequestBody Recipe recipe) {
        return recipeService.modifyRecipe(id, recipe);
    }

    @DeleteMapping("/{id}")
    public int dropRecipe(@PathVariable Long id) {
        return recipeService.removeRecipe(id);
    }
}