package com.example.recipeinator.models;

import androidx.room.Embedded;
import androidx.room.Ignore;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeIngredients {
    @Embedded
    public Recipe recipe;
    @Relation(
            parentColumn = "recipe_id",
            entityColumn = "ingredient_id",
            associateBy = @Junction(RecipeIngredientCrossRef.class)
    )
    public List<Ingredient> ingredients;

    @Relation(
            parentColumn = "recipe_id",
            entity = RecipeIngredientCrossRef.class,
            entityColumn = "id",
            associateBy = @Junction(RecipeIngredientCrossRef.class)
    )
    public List<RecipeIngredientCrossRef> crossRefs;

    public String getIngredientMeasure(Ingredient ingredient){
        RecipeIngredientCrossRef c = crossRefs.get(ingredients.indexOf(ingredient));
        return String.format(Locale.ENGLISH, "%d %s", c.amount, c.measurement);
    }
}
