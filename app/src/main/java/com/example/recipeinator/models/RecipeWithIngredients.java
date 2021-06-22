package com.example.recipeinator.models;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.ArrayList;
import java.util.List;

public class RecipeWithIngredients {
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

    public List<MeasuredIngredient> getMeasuredIngredients(){
        List<MeasuredIngredient> ingredients = new ArrayList<>();
        for (int i = 0, ingredientSize = this.ingredients.size(); i < ingredientSize; i++) {
            Ingredient ingredient = this.ingredients.get(i);
            RecipeIngredientCrossRef crossRef = crossRefs.get(i);

            ingredients.add(new MeasuredIngredient(ingredient, crossRef.amount, crossRef.measurement));
        }
        return ingredients;
    }
}
