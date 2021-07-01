package com.example.recipeinator.Adapters;

import com.example.recipeinator.models.Category;
import com.example.recipeinator.models.Ingredient;
import com.example.recipeinator.models.IngredientWithRecipes;
import com.example.recipeinator.models.Recipe;
import com.example.recipeinator.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SearchRecipeAdapter extends RecipeAdapter {
    private final List<Recipe> filteredRecipes = new ArrayList<>();
    private final List<IngredientWithRecipes> ingredientWithRecipes;
    private boolean isIngredientMode = false;
    private Category filteredCategory;
    private String filteredName;

    public SearchRecipeAdapter(List<Recipe> recipes, OnItemClickListener itemClickListener, List<IngredientWithRecipes> ingredientWithRecipes) {
        super(recipes, itemClickListener);
        filteredRecipes.addAll(recipes);
        this.ingredientWithRecipes = ingredientWithRecipes;
    }

    public void filter() {
        filteredRecipes.clear();
        if (isIngredientMode()) {
            filterByIngredientAnd();
        } else {
            filterByRecipe();
        }
        notifyDataSetChanged();
    }

    private void filterByRecipe() {
        for (Recipe recipe : getRecipes()) {
            if (matchesName(recipe) && matchesCategory(recipe)) {
                filteredRecipes.add(recipe);
            }
        }
    }

    private void filterByIngredientOr() {
        Set<Recipe> filteredRecipesTemp = new HashSet<>();
        for (IngredientWithRecipes ingredient : ingredientWithRecipes) {
            if (matchesAnyIngredient(ingredient.ingredient)) {
                for (Recipe recipe : ingredient.recipes) {
                    if (matchesCategory(recipe)) {
                        filteredRecipesTemp.add(recipe);
                    }
                }
            }
        }
        filteredRecipes.addAll(filteredRecipesTemp);
    }

    private void filterByIngredientAnd() {
        Set<Recipe> filteredRecipesTemp = new HashSet<>();
        for (Recipe recipe : getRecipes()) {
            if (matchesCategory(recipe) && matchesAllIngredients(recipe)) {
                filteredRecipesTemp.add(recipe);
            }
        }
        filteredRecipes.addAll(filteredRecipesTemp);
    }

    public boolean isIngredientMode() {
        return isIngredientMode;
    }

    public void toggleIngredientMode() {
        isIngredientMode = !isIngredientMode;
    }

    public void setIngredientMode(boolean ingredientMode) {
        this.isIngredientMode = ingredientMode;
    }

    public void filterName(String query) {
        filteredName = query;
        filter();
    }

    public void filterCategory(Category category) {
        filteredCategory = category;
        filter();
    }

    public void clearFilter(){
        filteredName = null;
        filteredCategory = null;
    }

    public boolean filterIsEmpty() {
        return (filteredName == null || filteredName.isEmpty()) && (filteredCategory == null);
    }

    private boolean matchesCategory(Recipe recipe) {
        return filteredCategory == null || recipe.getCategory().name.equals(filteredCategory.name);
    }

    private boolean matchesName(Recipe recipe) {
        return filteredName == null || recipe.name.toLowerCase().contains(filteredName.toLowerCase());
    }

    private boolean matchesAnyIngredient(Ingredient ingredient) {
        if (filteredName == null) {
            return true;
        }
        for (String namePart : filteredName.split(",")) {
            if (ingredient.name.toLowerCase().contains(namePart.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private boolean matchesAllIngredients(Recipe recipe) {
        if (filteredName == null) {
            return true;
        }
        for (String namePart : filteredName.split(",")) {
            boolean contained = false;
            for (Ingredient ingredient : recipe.getIngredients()) {
                contained = contained || ingredient.name.toLowerCase().contains(namePart.toLowerCase());
            }
            if (!contained) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Recipe getRecipeAtPosition(int position) {
        return filteredRecipes.get(position);
    }

    @Override
    public int getItemCount() {
        return filteredRecipes.size();
    }
}
