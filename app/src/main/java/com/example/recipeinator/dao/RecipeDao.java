package com.example.recipeinator.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.recipeinator.models.Ingredient;
import com.example.recipeinator.models.MeasuredIngredient;
import com.example.recipeinator.models.Recipe;
import com.example.recipeinator.models.RecipeIngredientCrossRef;
import com.example.recipeinator.models.RecipeIngredients;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Dao
public interface RecipeDao {
    default List<Recipe> getAll(){
        List<Recipe> recipes = new ArrayList<>();
        List<RecipeIngredients> recipesIngredients = _getRecipeIngredients();
        for (RecipeIngredients recipeIngredients : recipesIngredients) {
            Recipe recipe = recipeIngredients.recipe;
            recipe.setIngredients(recipeIngredients.getMeasuredIngredients());
            recipes.add(recipe);
        }
        return recipes;
    }

    @Query("SELECT * FROM Recipe ORDER BY recipe_id")
    List<Recipe> _getAll();

    @Transaction
    @Query("SELECT * FROM Recipe ORDER BY recipe_id")
    List<RecipeIngredients> _getRecipeIngredients();

    default Recipe getById(int recipeId){
        Recipe recipe = _getById(recipeId);
        recipe.setIngredients(_getRecipeIngredientsFor(recipeId).getMeasuredIngredients());
        return recipe;
    }

    @Query("SELECT * FROM Recipe WHERE recipe_id=:recipeId")
    Recipe _getById(int recipeId);

    @Transaction
    @Query("SELECT * FROM Recipe WHERE recipe_id=:recipeId")
    RecipeIngredients _getRecipeIngredientsFor(int recipeId);

    default void insertAll(Recipe... recipes){
        long[] ids = _insertAll(recipes);
        for (int i = 0; i < recipes.length; i++) {
            Recipe recipe = recipes[i];
            recipe.id = (int)ids[i];
            for (MeasuredIngredient ingredient : recipe.getIngredients()) {
                insertRecipeIngredients(new RecipeIngredientCrossRef(recipe.id, ingredient.id, ingredient.amount, ingredient.unit));
            }
        }
    }

    @Insert
    void insertRecipeIngredients(RecipeIngredientCrossRef... ingredients);

    @Insert
    long[] _insertAll(Recipe... recipes);

    @Delete
    void deleteAll(Recipe... recipes);
}
