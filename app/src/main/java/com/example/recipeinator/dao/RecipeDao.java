package com.example.recipeinator.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.recipeinator.models.Ingredient;
import com.example.recipeinator.models.Recipe;
import com.example.recipeinator.models.RecipeContainsIngredient;
import com.example.recipeinator.models.RecipeIngredients;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM Recipe")
    List<Recipe> getAll();

    @Transaction
    @Query("SELECT * FROM Recipe")
    List<RecipeIngredients> getRecipeIngredients();

    @Transaction
    @Query("SELECT * FROM Recipe WHERE recipe_id=:recipeId")
    RecipeIngredients getRecipeIngredientsFor(int recipeId);

    @Insert
    void addRecipeIngredients(RecipeContainsIngredient... ingredients);

    @Insert
    void insertAll(Recipe... recipes);

    @Delete
    void deleteAll(Recipe... recipes);
}
