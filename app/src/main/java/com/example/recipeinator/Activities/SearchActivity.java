package com.example.recipeinator.Activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.recipeinator.Adapters.RecipeAdapter;
import com.example.recipeinator.Adapters.RecipeIngredientsAdapter;
import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.BottomNavbarListener;
import com.example.recipeinator.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        AppDatabase database = Room.databaseBuilder(
                this,
                AppDatabase.class,
                "database"
        ).allowMainThreadQueries().build();

        BottomNavigationView bottomNavbar = findViewById(R.id.bottom_navbar);
        bottomNavbar.setSelectedItemId(R.id.page_search);
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavbarListener(this));

        RecyclerView searchResults = findViewById(R.id.search_results);
        searchResults.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(searchResults.getContext(), DividerItemDecoration.VERTICAL);
        searchResults.addItemDecoration(dividerItemDecoration);
        RecipeAdapter adapter = new RecipeAdapter(database.recipeDao().getAll());
        searchResults.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.main_searchbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                if (adapter.getItemCount() == 0 && !query.isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content), "No Results", Snackbar.LENGTH_LONG).show();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                if (adapter.getItemCount() == 0 && !newText.isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content), "No Results", Snackbar.LENGTH_LONG).show();
                }
                return true;
            }
        });
    }
}
