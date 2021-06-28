package com.example.recipeinator.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.recipeinator.Adapters.RecyclerArrayAdapter;
import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.BottomNavbarListener;
import com.example.recipeinator.R;
import com.example.recipeinator.fragments.TimerFragment;
import com.example.recipeinator.models.MeasuredIngredient;
import com.example.recipeinator.models.Recipe;
import com.example.recipeinator.models.UserRecipeCrossRef;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RecipeDetailActivity extends AppCompatActivity {

    private AppDatabase database;
    private int recipeId;
    private ImageView favorite;
    private int userId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_view);

        BottomNavigationView bottomNavbar = findViewById(R.id.bottom_navbar);
        bottomNavbar.setSelectedItemId(R.id.page_recipes);
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavbarListener(this));

        database = AppDatabase.getInstance();
        recipeId = getIntent().getIntExtra("RECIPE_ID", 0);
        userId = LoginActivity.getLoggedInUser().id;
        Recipe recipe = database.recipeDao().getById(recipeId);

        ImageView picture = findViewById(R.id.recipe_detail_picture);
        picture.setImageURI(Uri.parse(recipe.pictureUri));

        TextView name = findViewById(R.id.recipe_detail_name);
        name.setText(recipe.name);

        TextView description = findViewById(R.id.recipe_detail_description);
        description.setText(recipe.description);

        TextView instructions = findViewById(R.id.recipe_detail_instructions);
        instructions.setText(recipe.instructions);

        TextView category = findViewById(R.id.recipe_detail_category);
        category.setText(getString(R.string.category_parametrized, recipe.getCategory().name));

        TextView time = findViewById(R.id.recipe_detail_preparation_time);
        time.setText(getString(R.string.preparation_time_parametrized, recipe.preparationTime));

        TextView servings = findViewById(R.id.recipe_detail_servings);
        servings.setText(getString(R.string.serving_parametrized2, recipe.servings));

        RatingBar difficulty = findViewById(R.id.recipe_detail_difficulty);
        difficulty.setRating(recipe.difficulty);

        RecyclerArrayAdapter<MeasuredIngredient> adapter = new RecyclerArrayAdapter<>(R.layout.generic_list_item_small, recipe.getIngredients());
        RecyclerView recyclerView = findViewById(R.id.recipe_detail_ingredients);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });

        TimerFragment fragment = new TimerFragment(recipe.preparationTime);
        getSupportFragmentManager().beginTransaction().add(R.id.timer_frame, fragment).hide(fragment).commit();
        findViewById(R.id.timer_button).setOnClickListener(v -> {
            if (fragment.isVisible()) {
                getSupportFragmentManager().beginTransaction().hide(fragment).commit();
            } else {
                getSupportFragmentManager().beginTransaction().show(fragment).commit();
            }
        });

        favorite = findViewById(R.id.favorite_image);
        favorite.setOnClickListener(v -> handleFavoriteClicked());
        setFavoriteIcon(database.userDao().isUserFavoriteRecipe(userId, recipeId));
    }

    private void handleFavoriteClicked(){
        if (database.userDao().isUserFavoriteRecipe(userId, recipeId)) {
            database.userDao().deleteUserRecipes(new UserRecipeCrossRef(userId, recipeId));
            Toast.makeText(this, "Removed Recipe from Favorites", Toast.LENGTH_LONG).show();
            setFavoriteIcon(false);
        } else {
            database.userDao().insertUserRecipes(new UserRecipeCrossRef(userId, recipeId));
            Toast.makeText(this, "Added Recipe to Favorites", Toast.LENGTH_LONG).show();
            setFavoriteIcon(true);
        }
    }

    private void setFavoriteIcon(boolean isFavorite){
        if (isFavorite) {
            favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
        } else {
            favorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
        }
    }
}
