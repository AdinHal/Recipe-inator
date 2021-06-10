package com.example.recipeinator.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"recipe_id", "ingredient_id"})
public class RecipeContainsIngredient {
    @ColumnInfo(name = "recipe_id") public int recipeId;
    @ColumnInfo(name = "ingredient_id") public int ingredientId;

    public RecipeContainsIngredient(){
    }

    public RecipeContainsIngredient(int recipeId, int ingredientId) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
    }
}
