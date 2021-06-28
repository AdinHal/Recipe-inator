package com.example.recipeinator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.recipeinator.Adapters.RecipeAdapter;
import com.example.recipeinator.Adapters.SearchRecipeAdapter;
import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.BottomNavbarListener;
import com.example.recipeinator.R;
import com.example.recipeinator.models.Category;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView searchResults;
    private SearchRecipeAdapter adapter;
    private TextView categoryText;
    private ImageView clearCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        AppDatabase database = AppDatabase.getInstance();

        BottomNavigationView bottomNavbar = findViewById(R.id.bottom_navbar);
        bottomNavbar.setSelectedItemId(R.id.page_search);
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavbarListener(this));

        searchResults = findViewById(R.id.search_results);
        searchResults.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(searchResults.getContext(), DividerItemDecoration.VERTICAL);
        searchResults.addItemDecoration(dividerItemDecoration);
        adapter = new SearchRecipeAdapter(database.recipeDao().getAll(), i -> {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra("RECIPE_ID", i);
            startActivity(intent);
        });
        searchResults.setAdapter(adapter);

        findViewById(R.id.show_all_recipes_search).setOnClickListener(v -> showAllRecipes());

        findViewById(R.id.asian_search_option).setOnClickListener(v -> filterByCategory(new Category("Asian")));
        findViewById(R.id.dessert_search_option).setOnClickListener(v -> filterByCategory(new Category("Dessert")));
        findViewById(R.id.fast_search_option).setOnClickListener(v -> filterByCategory(new Category("Fast")));
        findViewById(R.id.main_course_search_option).setOnClickListener(v -> filterByCategory(new Category("Main Course")));
        findViewById(R.id.pasta_search_option).setOnClickListener(v -> filterByCategory(new Category("Pasta")));
        findViewById(R.id.vegetarian_search_option).setOnClickListener(v -> filterByCategory(new Category("Vegetarian")));

        SearchView searchView = findViewById(R.id.main_searchbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                handleSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                handleSearch(newText);
                return true;
            }
        });

        categoryText = findViewById(R.id.search_category);
        clearCategory = findViewById(R.id.clear_category);
        clearCategory.setOnClickListener(v -> filterByCategory(null));
    }

    private void handleSearch(String query) {
        adapter.filterName(query);
        if (resetSearchIfEmpty()) {
            return;
        }
        searchResults.setVisibility(View.VISIBLE);
        if (adapter.getItemCount() == 0) {
            Snackbar.make(findViewById(android.R.id.content), R.string.no_results, Snackbar.LENGTH_LONG).show();
        }
    }

    private void showAllRecipes() {
        searchResults.setVisibility(View.VISIBLE);
        clearCategory.setVisibility(View.VISIBLE);
        adapter.filterName("");
        categoryText.setText(getString(R.string.category_parametrized, "All"));
    }

    private void filterByCategory(Category category) {
        adapter.filterCategory(category);
        if (resetSearchIfEmpty()) {
            return;
        }
        if (category != null) {
            clearCategory.setVisibility(View.VISIBLE);
            searchResults.setVisibility(View.VISIBLE);
            categoryText.setText(getString(R.string.category_parametrized, category.name));
        } else {
            clearCategory.setVisibility(View.GONE);
            categoryText.setText("");
        }
        if (adapter.getItemCount() == 0) {
            Snackbar.make(findViewById(android.R.id.content), R.string.no_results, Snackbar.LENGTH_LONG).show();
        }
    }

    private boolean resetSearchIfEmpty() {
        if (adapter.filterIsEmpty()) {
            resetSearch();
            return true;
        }
        return false;
    }

    private void resetSearch() {
        clearCategory.setVisibility(View.GONE);
        searchResults.setVisibility(View.GONE);
        adapter.clearFilter();
        categoryText.setText("");
    }
}
