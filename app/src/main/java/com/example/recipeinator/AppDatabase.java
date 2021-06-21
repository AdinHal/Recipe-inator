package com.example.recipeinator;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.recipeinator.dao.GroceriesDao;
import com.example.recipeinator.dao.IngredientDao;
import com.example.recipeinator.dao.RecipeDao;
import com.example.recipeinator.dao.UserDao;
import com.example.recipeinator.models.Groceries;
import com.example.recipeinator.models.Ingredient;
import com.example.recipeinator.models.Recipe;
import com.example.recipeinator.models.RecipeIngredientCrossRef;
import com.example.recipeinator.models.User;

@Database(
        version= 1,
        entities = {Recipe.class, Ingredient.class, RecipeIngredientCrossRef.class, Groceries.class, User.class},
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RecipeDao recipeDao();
    public abstract IngredientDao ingredientDao();
    public abstract GroceriesDao groceriesDao();
    public abstract UserDao userDao();
}
