package com.example.recipeinator.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.recipeinator.Adapters.RecipeIngredientsAdapter;
import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.BottomNavbarListener;
import com.example.recipeinator.R;
import com.example.recipeinator.models.Ingredient;
import com.example.recipeinator.models.MeasuredIngredient;
import com.example.recipeinator.models.Recipe;
import com.example.recipeinator.models.RecipeIngredients;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RandomActivity extends AppCompatActivity {
    private RecipeIngredientsAdapter adapter;
    private RecyclerView recyclerView;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        database = Room.databaseBuilder(
                this,
                AppDatabase.class,
                "database"
        ).allowMainThreadQueries().build();

        BottomNavigationView bottomNavbar = findViewById(R.id.bottom_navbar);
        bottomNavbar.setSelectedItemId(R.id.page_recipes);
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavbarListener(this));

        Button createRecipe = findViewById(R.id.create_recipe);
        createRecipe.setOnClickListener(v -> saveRecipe());

        recyclerView = findViewById(R.id.ingredient_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecipeIngredientsAdapter(database);
        recyclerView.setAdapter(adapter);
        ImageView addIngredient = findViewById(R.id.add_ingredient);
        addIngredient.setOnClickListener(v -> adapter.addItem());

    }

    private void selectImage(){
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, 1);
    }

    private void saveRecipe(){

        Recipe recipe = new Recipe(((EditText)findViewById(R.id.recipe_name)).getText().toString());
        for (int i = 0; i < adapter.getItemCount(); i++) {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);
            if (viewHolder != null){
                String ingredient = ((EditText) viewHolder.itemView.findViewById(R.id.ingredient)).getText().toString();
                String count = ((EditText) viewHolder.itemView.findViewById(R.id.count)).getText().toString();
                String unit = ((EditText) viewHolder.itemView.findViewById(R.id.unit)).getText().toString();
                recipe.addIngredient(database.ingredientDao().getByName(ingredient), Integer.parseInt(count), unit);
            }
        }
        database.recipeDao().insertAll(recipe);
        Snackbar.make(findViewById(android.R.id.content), "Successfully Added", Snackbar.LENGTH_LONG).show();
    }
}
