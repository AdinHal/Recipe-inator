package com.example.recipeinator.models;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class RecipeIngredients {
    @Embedded
    public Recipe recipe;
    @Relation(
            parentColumn = "recipe_id",
            entityColumn = "ingredient_id",
            associateBy = @Junction(RecipeContainsIngredient.class)
    )
    public List<Ingredient> ingredients;

}
