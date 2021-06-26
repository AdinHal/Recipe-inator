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
import com.example.recipeinator.util.OnItemClickListener;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    private final List<Recipe> recipes;
    private final OnItemClickListener itemClickListener;

    public RecipeAdapter(List<Recipe> recipes, OnItemClickListener itemClickListener) {
        this.recipes = recipes;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_item_view, parent, false);
        return new ViewHolder(view);
    }

    public Recipe getRecipeAtPosition(int position) {
        return recipes.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        Recipe recipe = getRecipeAtPosition(position);
        ((TextView) holder.itemView.findViewById(R.id.recipe_list_name)).setText(recipe.name);
        ((TextView) holder.itemView.findViewById(R.id.recipe_list_description)).setText(recipe.description);
        ((ImageView) holder.itemView.findViewById(R.id.recipe_list_picture)).setImageURI(Uri.parse(recipe.pictureUri));
        holder.itemView.setOnClickListener(v -> itemClickListener.onItemClicked(recipe.id));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public OnItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
