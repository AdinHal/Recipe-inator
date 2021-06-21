package com.example.recipeinator;

import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeinator.Activities.GroceryListActivity;
import com.example.recipeinator.Activities.HomeActivity;
import com.example.recipeinator.Activities.ProfileActivity;
import com.example.recipeinator.Activities.CreateRecipeActivity;
import com.example.recipeinator.Activities.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavbarListener implements BottomNavigationView.OnNavigationItemSelectedListener {
    AppCompatActivity activity;

    public BottomNavbarListener(AppCompatActivity activity){
        this.activity = activity;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        Intent intent = null;
        if (itemId == R.id.page_home) {
            intent = new Intent(activity, HomeActivity.class);
        } else if (itemId == R.id.page_search) {
            intent = new Intent(activity, SearchActivity.class);
        } else if (itemId == R.id.page_recipes) {
            intent = new Intent(activity, CreateRecipeActivity.class);
        } else if (itemId == R.id.page_list) {
            intent = new Intent(activity, GroceryListActivity.class);
        } else if (itemId == R.id.page_profile) {
            intent = new Intent(activity, ProfileActivity.class);
        }
        if (intent != null){
            activity.startActivity(intent);
            activity.finish();
            activity = null;
            return true;
        }
        return false;
    }
}
