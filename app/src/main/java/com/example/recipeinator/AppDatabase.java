package com.example.recipeinator;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
        version= 4,
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

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Recipe ADD COLUMN servings INTEGER NOT NULL DEFAULT 1;");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE Recipe ADD COLUMN difficulty INTEGER NOT NULL DEFAULT 1;");
        }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE User ADD COLUMN picture_uri VARCHAR(255);");
        }
    };
}
