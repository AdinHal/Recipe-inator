package com.example.recipeinator.dao;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.recipeinator.models.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM Recipe")
    List<Recipe> getAll();

    @Insert
    void insertAll(Recipe... recipes);

    @Delete
    void deleteAll(Recipe... recipes);
}
