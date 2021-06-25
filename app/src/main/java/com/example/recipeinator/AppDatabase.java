package com.example.recipeinator;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.recipeinator.dao.CategoryDao;
import com.example.recipeinator.dao.GroceriesDao;
import com.example.recipeinator.dao.IngredientDao;
import com.example.recipeinator.dao.RecipeDao;
import com.example.recipeinator.dao.UserDao;
import com.example.recipeinator.models.Category;
import com.example.recipeinator.models.Groceries;
import com.example.recipeinator.models.Ingredient;
import com.example.recipeinator.models.Recipe;
import com.example.recipeinator.models.RecipeIngredientCrossRef;
import com.example.recipeinator.models.User;
import com.example.recipeinator.models.UserRecipeCrossRef;

@Database(
        version= 1,
        entities = {Recipe.class, Ingredient.class, RecipeIngredientCrossRef.class, Groceries.class, User.class, Category.class, UserRecipeCrossRef.class},
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract RecipeDao recipeDao();
    public abstract IngredientDao ingredientDao();
    public abstract GroceriesDao groceriesDao();
    public abstract UserDao userDao();
    public abstract CategoryDao categoryDao();

    public static AppDatabase getInstance(){
        return instance;
    }

    public static void setInstance(AppDatabase instance){
        AppDatabase.instance = instance;
    }
}
