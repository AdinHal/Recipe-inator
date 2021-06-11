package com.example.recipeinator.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.recipeinator.models.Ingredient;
import com.example.recipeinator.models.Recipe;
import com.example.recipeinator.models.RecipeIngredientCrossRef;
import com.example.recipeinator.models.RecipeIngredients;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM Recipe")
    List<Recipe> getAll();

    @Query("SELECT * FROM Recipe WHERE recipe_id=:recipeId")
    Recipe getById(int recipeId);

    @Transaction
    @Query("SELECT * FROM Recipe")
    List<RecipeIngredients> getRecipeIngredients();

    @Transaction
    @Query("SELECT * FROM Recipe WHERE recipe_id=:recipeId")
    RecipeIngredients getRecipeIngredientsFor(int recipeId);

    @Insert
    void insertRecipeIngredients(RecipeIngredientCrossRef... ingredients);

    @Insert
    void insertAll(Recipe... recipes);

    @Delete
    void deleteAll(Recipe... recipes);
}
