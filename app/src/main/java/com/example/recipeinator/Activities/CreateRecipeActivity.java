package com.example.recipeinator.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.recipeinator.Adapters.RecipeIngredientsAdapter;
import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.BottomNavbarListener;
import com.example.recipeinator.R;
import com.example.recipeinator.models.Ingredient;
import com.example.recipeinator.models.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class CreateRecipeActivity extends AppCompatActivity {
    private RecipeIngredientsAdapter adapter;
    private RecyclerView recyclerView;
    private Uri pictureUri;
    private AppDatabase database;
    private ImageView imageView;
    private EditText pictureName;
    private final ActivityResultLauncher<Intent> selectImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    pictureUri = result.getData().getData();
                    getContentResolver().takePersistableUriPermission(pictureUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    pictureName.setText(pictureUri.getLastPathSegment());
                    runOnUiThread(() -> imageView.setImageURI(result.getData().getData()));
                }
            }
        );

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);

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

        imageView = findViewById(R.id.picture_preview);
        pictureName = findViewById(R.id.recipe_picture);
        Button selectPicture = findViewById(R.id.select_recipe_picture);
        selectPicture.setOnClickListener(v -> selectImage());
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        selectImageLauncher.launch(intent);
    }

    private void saveRecipe(){
        Recipe recipe = new Recipe(((EditText)findViewById(R.id.recipe_name)).getText().toString());
        recipe.preparationTime = Integer.parseInt(((EditText) findViewById(R.id.preparation_time)).getText().toString());
        recipe.pictureUri = pictureUri.toString();
        for (int i = 0; i < adapter.getItemCount(); i++) {
            RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(i);
            if (viewHolder != null){
                String ingredientName = ((EditText) viewHolder.itemView.findViewById(R.id.ingredient)).getText().toString();
                String count = ((EditText) viewHolder.itemView.findViewById(R.id.count)).getText().toString();
                String unit = ((Spinner) viewHolder.itemView.findViewById(R.id.unit)).getSelectedItem().toString();
                Ingredient ingredient = database.ingredientDao().getByName(ingredientName);
                if (ingredient == null){
                    ingredient = new Ingredient(ingredientName);
                    database.ingredientDao().insertAll(ingredient);
                }
                recipe.addIngredient(ingredient, Integer.parseInt(count), unit);
            }
        }

        try {
            database.recipeDao().insertAll(recipe);
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra("RECIPE_ID", recipe.id);
            startActivity(intent);
        } catch (Exception e) {
            Snackbar.make(findViewById(android.R.id.content), "Failed to add recipe!", Snackbar.LENGTH_LONG).show();
        }
    }
}
