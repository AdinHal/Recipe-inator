package com.example.recipeinator.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index({"recipe_id", "ingredient_id"}), @Index("ingredient_id")})
public class RecipeIngredientCrossRef {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") public int id;
    @ColumnInfo(name = "recipe_id") public int recipeId;
    @ColumnInfo(name = "ingredient_id") public int ingredientId;
    @ColumnInfo(name = "amount") public int amount;
    @ColumnInfo(name = "measurement") public String  measurement;

    public RecipeIngredientCrossRef(){
    }

    @Ignore
    public RecipeIngredientCrossRef(int recipeId, int ingredientId, int amount, String measurement) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.amount = amount;
        this.measurement = measurement;
    }
}
