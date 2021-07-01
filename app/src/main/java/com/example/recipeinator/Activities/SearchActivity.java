package com.example.recipeinator.Activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeinator.Adapters.SearchRecipeAdapter;
import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.BottomNavbarListener;
import com.example.recipeinator.R;
import com.example.recipeinator.models.Category;
import com.example.recipeinator.models.Ingredient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView searchResults;
    private SearchRecipeAdapter adapter;
    private TextView categoryText;
    private ImageView clearCategory;
    private SearchView searchView;
    private CursorAdapter suggestionAdapter;

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
        }, database.ingredientDao().getAllWithRecipes());
        searchResults.setAdapter(adapter);

        findViewById(R.id.show_all_recipes_search).setOnClickListener(v -> showAllRecipes());

        findViewById(R.id.asian_search_option).setOnClickListener(v -> filterByCategory(new Category("Asian")));
        findViewById(R.id.dessert_search_option).setOnClickListener(v -> filterByCategory(new Category("Dessert")));
        findViewById(R.id.fast_search_option).setOnClickListener(v -> filterByCategory(new Category("Fast")));
        findViewById(R.id.main_course_search_option).setOnClickListener(v -> filterByCategory(new Category("Main Course")));
        findViewById(R.id.pasta_search_option).setOnClickListener(v -> filterByCategory(new Category("Pasta")));
        findViewById(R.id.vegetarian_search_option).setOnClickListener(v -> filterByCategory(new Category("Vegetarian")));

        searchView = findViewById(R.id.main_searchbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                handleSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (adapter.isIngredientMode()) {
                    populateSuggestionAdapter(newText);
                }
                handleSearch(newText);
                return true;
            }
        });
        addSuggestions();

        categoryText = findViewById(R.id.search_category);
        clearCategory = findViewById(R.id.clear_category);
        clearCategory.setOnClickListener(v -> filterByCategory(null));

        findViewById(R.id.switch_arrow).setOnClickListener(v -> toggleSearchMode());
    }

    private void toggleSearchMode() {
        adapter.toggleMode();
        boolean isIngredient = adapter.isIngredientMode();
        int gray = getResources().getColor(R.color.gray, null);
        int black = getResources().getColor(R.color.black, null);
        TextView byIngredients = findViewById(R.id.by_ingredients);
        TextView hint = findViewById(R.id.search_hint);
        TextView byRecipe = findViewById(R.id.by_recipe);
        byIngredients.setTextColor(isIngredient ? black : gray);
        byRecipe.setTextColor(isIngredient ? gray : black);
        hint.setVisibility(isIngredient ? View.VISIBLE : View.GONE);
        searchView.setQueryHint(getString(isIngredient ? R.string.ingredients_list : R.string.recipe));
        searchView.setQuery(searchView.getQuery(), true);
        hint.setText(getString(R.string.hint, getString(adapter.getIngredientModeStringId())));
        hint.setOnClickListener(v -> {
            adapter.toggleIngredientMode();
            hint.setText(getString(R.string.hint, getString(adapter.getIngredientModeStringId())));
        });
    }

    private void addSuggestions() {
        String[] from = new String[] {"ingredientName"};
        int[] to = new int[] {android.R.id.text1};
        suggestionAdapter = new SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        searchView.setSuggestionsAdapter(suggestionAdapter);
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = (Cursor) suggestionAdapter.getItem(position);
                String ingredientName = cursor.getString(cursor.getColumnIndex("ingredientName"));
                String[] pieces = searchView.getQuery().toString().split(",", -1);
                pieces[pieces.length - 1] = ingredientName;
                StringBuilder queryBuilder = new StringBuilder();
                for (String piece : pieces) {
                    queryBuilder.append(piece);
                    queryBuilder.append(",");
                }
                searchView.setQuery(queryBuilder.toString(), true);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                return true;
            }
        });
    }

    private void populateSuggestionAdapter(String query) {
        String[] pieces = query.split(",", -1);
        query = pieces[pieces.length - 1];
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{ BaseColumns._ID, "ingredientName" });
        if (query.isEmpty()) {
            suggestionAdapter.changeCursor(matrixCursor);
            return;
        }
        List<Ingredient> ingredients = AppDatabase.getInstance().ingredientDao().getAll();
        List<String> ingredientsNames = new ArrayList<>();
        for (Ingredient ingredient: ingredients){
            ingredientsNames.add(ingredient.name);
        }
        for (int i = 0; i < ingredientsNames.size(); i++) {
            String ingredient = ingredientsNames.get(i);
            if (ingredient.toLowerCase().startsWith(query.toLowerCase())) {
                matrixCursor.addRow(new Object[]{i, ingredient});
            }
        }
        suggestionAdapter.changeCursor(matrixCursor);
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
