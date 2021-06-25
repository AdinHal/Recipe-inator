package com.example.recipeinator.Adapters;

import com.example.recipeinator.models.Recipe;
import com.example.recipeinator.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class SearchRecipeAdapter extends RecipeAdapter {
    private final List<Recipe> filteredRecipes = new ArrayList<>();

    public SearchRecipeAdapter(List<Recipe> recipes, OnItemClickListener itemClickListener) {
        super(recipes, itemClickListener);
        filteredRecipes.addAll(recipes);
    }

    public void filter(String query) {
        filteredRecipes.clear();
        for (Recipe recipe : getRecipes()) {
            if (matches(recipe, query)) {
                filteredRecipes.add(recipe);
            }
        }
        notifyDataSetChanged();
    }

    private boolean matches(Recipe recipe, String query) {
        return recipe.name.toLowerCase().contains(query.toLowerCase());
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
