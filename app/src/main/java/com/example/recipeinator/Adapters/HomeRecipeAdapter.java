package com.example.recipeinator.Adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.recipeinator.Activities.LoginActivity;
import com.example.recipeinator.R;
import com.example.recipeinator.models.Recipe;
import com.example.recipeinator.util.OnItemClickListener;
import com.example.recipeinator.views.RatingView;

import java.util.List;

public class HomeRecipeAdapter extends RecipeAdapter {
    private final int[] backgroundResources = new int[]{
            R.drawable.background1,
            R.drawable.background2,
            R.drawable.background3,
            R.drawable.background4,
            R.drawable.background5
    };

    public HomeRecipeAdapter(List<Recipe> recipes, OnItemClickListener itemClickListener) {
        super(recipes, itemClickListener);
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_home, parent, false);
        return new RecipeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe recipe = getRecipes().get(position);

        ImageView background = holder.itemView.findViewById(R.id.home_recipe_background);
        background.setBackgroundResource(backgroundResources[position % 5]);

        TextView name = holder.itemView.findViewById(R.id.home_recipe_name);
        name.setText(recipe.name);

        TextView description = holder.itemView.findViewById(R.id.home_recipe_description);
        description.setText(recipe.description);

        ImageView picture = holder.itemView.findViewById(R.id.home_recipe_picture);
        picture.setImageURI(Uri.parse(recipe.pictureUri));

        TextView servings = holder.itemView.findViewById(R.id.home_recipe_servings);
        servings.setText(recipe.servings + "serving");

        TextView time = holder.itemView.findViewById(R.id.home_recipe_time);
        time.setText(recipe.preparationTime + " min");

        RatingView ratingView = holder.itemView.findViewById(R.id.recipe_difficulty_home);
        ratingView.setItemCount(recipe.difficulty);
        holder.itemView.setOnClickListener(v -> getItemClickListener().onItemClicked(recipe.id));
    }

    @Override
    public int getItemCount() {
        // Show at most 5 recipes
        return Math.min(getRecipes().size(), 5);
    }
}
