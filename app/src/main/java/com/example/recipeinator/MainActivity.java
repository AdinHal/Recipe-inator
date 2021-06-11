package com.example.recipeinator;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.recipeinator.Activities.HomeActivity;
import com.example.recipeinator.models.Ingredient;
import com.example.recipeinator.models.MeasuredIngredient;
import com.example.recipeinator.models.Recipe;
import com.example.recipeinator.models.RecipeIngredientCrossRef;
import com.example.recipeinator.models.RecipeIngredients;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        database = Room.databaseBuilder(
            getApplicationContext(),
            AppDatabase.class,
            "database"
        ).build();

        insertTestData();
        logTestData();

        final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 1700);
    }

    public void insertTestData(){
        new Thread(() -> {
            Ingredient ingredient1 = new Ingredient("Bread");
            Ingredient ingredient2 = new Ingredient("Cheese");
            Ingredient ingredient3 = new Ingredient("Ham");
            database.ingredientDao().insertAll(ingredient1, ingredient2, ingredient3);

            Recipe recipe1 = new Recipe("Empty Recipe");
            Recipe recipe2 = new Recipe("Toast");
            Recipe recipe3 = new Recipe("Cheese Toast");

            recipe2.addIngredient(ingredient1, 2, "pcs");
            recipe2.addIngredient(ingredient2, 50, "g");
            recipe2.addIngredient(ingredient3, 70, "g");
            recipe3.addIngredient(ingredient1, 2, "pcs");
            recipe3.addIngredient(ingredient2, 100, "g");

            database.recipeDao().insertAll(recipe1, recipe2, recipe3);
        }).start();
    }

    public void logTestData(){
        new Thread(() -> {
            for (Recipe r : database.recipeDao().getAll()){
                Log.e("Database", r.toString() + " --> " + Arrays.toString(r.getIngredients().toArray()));
            }
        }).start();
    }
}