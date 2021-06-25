package com.example.recipeinator.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.recipeinator.Adapters.RecyclerArrayAdapter;
import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.R;
import com.example.recipeinator.fragments.TimerFragment;
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

        TextView description = findViewById(R.id.recipe_detail_description);
        description.setText(recipe.description);

        TextView instructions = findViewById(R.id.recipe_detail_instructions);
        instructions.setText(recipe.instructions);

        TextView category = findViewById(R.id.recipe_detail_category);
        category.setText(getString(R.string.category_parametrized, recipe.getCategory().name));

        TextView time = findViewById(R.id.recipe_detail_preparation_time);
        time.setText(getString(R.string.preparation_time_parametrized, recipe.preparationTime));

        RecyclerArrayAdapter<MeasuredIngredient> adapter = new RecyclerArrayAdapter<>(android.R.layout.simple_list_item_1, recipe.getIngredients());
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
    }
}
