package com.example.recipeinator.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.R;
import com.example.recipeinator.models.MeasuredIngredient;
import com.example.recipeinator.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        AppDatabase database = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "database"
        ).allowMainThreadQueries().build();
        int recipeId = getIntent().getIntExtra("RECIPE_ID", 0);
        Recipe recipe = database.recipeDao().getById(recipeId);

        ImageView picture = findViewById(R.id.recipe_detail_picture);
        picture.setImageURI(Uri.parse(recipe.pictureUri));

        TextView name = findViewById(R.id.recipe_detail_name);
        name.setText(recipe.name);

        TextView time = findViewById(R.id.recipe_detail_preparation_time);
        time.setText(getString(R.string.preparation_time_parametrized, recipe.preparationTime));

        List<MeasuredIngredient> ingredients = recipe.getIngredients();
        List<String> ingredientStrings = new ArrayList<>();
        for (MeasuredIngredient ingredient : ingredients){
            ingredientStrings.add(String.format("%s %s %s", ingredient.amount, ingredient.unit, ingredient.name));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ingredientStrings);
        ListView listView = findViewById(R.id.recipe_detail_ingredients);
        listView.setAdapter(adapter);
    }
}
