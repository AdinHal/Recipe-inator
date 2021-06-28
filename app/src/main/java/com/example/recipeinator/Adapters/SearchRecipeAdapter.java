package com.example.recipeinator.Adapters;

import com.example.recipeinator.models.Category;
import com.example.recipeinator.models.Recipe;
import com.example.recipeinator.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class SearchRecipeAdapter extends RecipeAdapter {
    private final List<Recipe> filteredRecipes = new ArrayList<>();
    private Category filteredCategory;
    private String filteredName;

    public SearchRecipeAdapter(List<Recipe> recipes, OnItemClickListener itemClickListener) {
        super(recipes, itemClickListener);
        filteredRecipes.addAll(recipes);
    }

    public void filter() {
        filteredRecipes.clear();
        for (Recipe recipe : getRecipes()) {
            if (matchesName(recipe) && matchesCategory(recipe)) {
                filteredRecipes.add(recipe);
            }
        }
        notifyDataSetChanged();
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

    private boolean matchesCategory(Recipe recipe) {
        return filteredCategory == null || recipe.getCategory().name.equals(filteredCategory.name);
    }

    private boolean matchesName(Recipe recipe) {
        return filteredName == null || recipe.name.toLowerCase().contains(filteredName.toLowerCase());
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
