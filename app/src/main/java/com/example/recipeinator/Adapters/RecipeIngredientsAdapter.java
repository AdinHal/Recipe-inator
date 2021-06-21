package com.example.recipeinator.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.R;
import com.example.recipeinator.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.ViewHolder> {
    private int count = 1;

    public RecipeIngredientsAdapter(AppDatabase database){
        ViewHolder.ingredientNames = new ArrayList<>();
        List<Ingredient> ingredients = database.ingredientDao().getAll();
        for (Ingredient ingredient: ingredients){
            ViewHolder.ingredientNames.add(ingredient.name);
        }
    }

    @NonNull
    @Override
    public RecipeIngredientsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_ingredient_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientsAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return count;
    }

    public void addItem(){
        count++;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private static List<String> ingredientNames;

        ViewHolder(View view){
            super(view);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    view.getContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    ingredientNames
            );
            AutoCompleteTextView textView = view.findViewById(R.id.ingredient);
            textView.setAdapter(adapter);
        }
    }
}
