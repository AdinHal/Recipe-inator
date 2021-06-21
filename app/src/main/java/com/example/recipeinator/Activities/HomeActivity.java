package com.example.recipeinator.Activities;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.recipeinator.BottomNavbarListener;
import com.example.recipeinator.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavbar = findViewById(R.id.bottom_navbar);
        bottomNavbar.setSelectedItemId(R.id.page_home);
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavbarListener(this));

        username = findViewById(R.id.home_usernameTxt);
    }
}
