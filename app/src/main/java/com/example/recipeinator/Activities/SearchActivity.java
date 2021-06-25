package com.example.recipeinator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView searchResults;
    private SearchRecipeAdapter adapter;

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
    }

    private void handleSearch(String query) {
        searchResults.setVisibility(query.isEmpty() ? View.GONE : View.VISIBLE);
        adapter.filter(query);
        if (adapter.getItemCount() == 0 && !query.isEmpty()) {
            Snackbar.make(findViewById(android.R.id.content), R.string.no_results, Snackbar.LENGTH_LONG).show();
        }
    }
}
