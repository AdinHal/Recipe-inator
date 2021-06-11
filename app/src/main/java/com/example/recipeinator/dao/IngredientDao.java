package com.example.recipeinator.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.recipeinator.models.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao {
    @Query("SELECT * FROM Ingredient")
    List<Ingredient> getAll();

    @Query("SELECT * FROM Ingredient WHERE name=:name")
    Ingredient getByName(String name);

    @Insert
    void insertAll(Ingredient... ingredients);

    @Delete
    void deleteAll(Ingredient... ingredients);
}
