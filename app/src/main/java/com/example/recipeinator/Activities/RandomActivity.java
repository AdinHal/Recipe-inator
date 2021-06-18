package com.example.recipeinator.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeinator.BottomNavbarListener;
import com.example.recipeinator.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RandomActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random);

        BottomNavigationView bottomNavbar = findViewById(R.id.bottom_navbar);
        bottomNavbar.setSelectedItemId(R.id.page_random);
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavbarListener(this));
    }
}
