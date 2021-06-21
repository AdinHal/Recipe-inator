package com.example.recipeinator.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeinator.R;
import com.example.recipeinator.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private final List<Recipe> recipes;
    private final List<Recipe> filteredRecipes = new ArrayList<>();

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
        filteredRecipes.addAll(recipes);
    }

    public void filter(String query) {
        filteredRecipes.clear();
        for (Recipe recipe : recipes) {
            if (matches(recipe, query)) {
                filteredRecipes.add(recipe);
            }
        }
        notifyDataSetChanged();
    }

    private boolean matches(Recipe recipe, String query) {
        return recipe.name.toLowerCase().contains(query.toLowerCase());
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        Recipe recipe = filteredRecipes.get(position);
        ((TextView) holder.itemView.findViewById(R.id.recipe_list_name)).setText(recipe.name);
        ((ImageView) holder.itemView.findViewById(R.id.recipe_list_picture)).setImageURI(Uri.parse(recipe.pictureUri));
    }

    @Override
    public int getItemCount() {
        return filteredRecipes.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
