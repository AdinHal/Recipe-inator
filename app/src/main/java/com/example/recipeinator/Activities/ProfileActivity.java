package com.example.recipeinator.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeinator.Adapters.RecipeAdapter;
import com.example.recipeinator.Adapters.SearchRecipeAdapter;
import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.BottomNavbarListener;
import com.example.recipeinator.R;
import com.example.recipeinator.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        AppDatabase database = AppDatabase.getInstance();
        User user = LoginActivity.getLoggedInUser();
        RecyclerView recyclerView = findViewById(R.id.favorite_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        RecipeAdapter adapter = new RecipeAdapter(database.userDao().getUserWithRecipes(user.id).recipes, i -> {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra("RECIPE_ID", i);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        findViewById(R.id.profile_logout).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        });

        findViewById(R.id.profile_edit).setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
            startActivity(intent);
        });

        BottomNavigationView bottomNavbar = findViewById(R.id.bottom_navbar);
        bottomNavbar.setSelectedItemId(R.id.page_profile);
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavbarListener(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        User user = LoginActivity.getLoggedInUser();

        TextView username = findViewById(R.id.profileview_text);
        username.setText(user.nickname);

        ImageView imageView = findViewById(R.id.profile_picture);
        if (user.pictureUri != null && !user.pictureUri.isEmpty()) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageURI(Uri.parse(user.pictureUri));
        } else {
            imageView.setVisibility(View.GONE);
        }
    }
}
