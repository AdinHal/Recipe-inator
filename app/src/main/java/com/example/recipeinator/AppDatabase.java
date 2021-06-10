package com.example.recipeinator;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.recipeinator.dao.RecipeDao;
import com.example.recipeinator.models.Recipe;

@Database(version= 1, entities = {Recipe.class}, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();
}
