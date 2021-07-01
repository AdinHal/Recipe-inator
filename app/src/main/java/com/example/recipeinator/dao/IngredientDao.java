package com.example.recipeinator.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.recipeinator.models.Ingredient;
import com.example.recipeinator.models.IngredientWithRecipes;

import java.util.List;

@Dao
public interface IngredientDao {
    @Query("SELECT * FROM Ingredient")
    List<Ingredient> getAll();

    @Query("SELECT * FROM Ingredient")
    List<IngredientWithRecipes> getAllWithRecipes();

    @Query("SELECT * FROM Ingredient WHERE name=:name")
    Ingredient getByName(String name);

    default void insertAll(Ingredient... ingredients){
        long[] ids = _insertAll(ingredients);
        for (int i = 0; i < ingredients.length; i++) {
            ingredients[i].id = (int) ids[i];
        }
    }

    @Insert
    long[] _insertAll(Ingredient... ingredients);

    @Delete
    void deleteAll(Ingredient... ingredients);
}
