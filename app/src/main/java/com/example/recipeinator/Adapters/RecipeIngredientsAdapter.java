package com.example.recipeinator.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.R;
import com.example.recipeinator.models.Ingredient;
import com.example.recipeinator.util.SwipeableAdapter;

import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.ViewHolder> implements SwipeableAdapter {
    private int count = 1;
    private Context context;

    public RecipeIngredientsAdapter(Context context){
        this.context = context;
        ViewHolder.ingredientNames = new ArrayList<>();
        List<Ingredient> ingredients = AppDatabase.getInstance().ingredientDao().getAll();
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
        notifyItemInserted(count - 1);
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecipeIngredientsAdapter.ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);

        ((EditText) holder.itemView.findViewById(R.id.ingredient)).setText("");
        ((EditText) holder.itemView.findViewById(R.id.count)).setText("");
        ((Spinner) holder.itemView.findViewById(R.id.unit)).setSelection(0);
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void editItem(int position) {
        notifyItemChanged(position);
    }

    @Override
    public void deleteItem(int position) {
        count--;
        notifyItemRemoved(position);
    }

    @Override
    public boolean requiresConfirmation() {
        return false;
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
