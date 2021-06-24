package com.example.recipeinator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.recipeinator.BottomNavbarListener;
import com.example.recipeinator.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavbar = findViewById(R.id.bottom_navbar);
        bottomNavbar.setSelectedItemId(R.id.page_home);
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavbarListener(this));

        ImageView tester = findViewById(R.id.timertest);

        tester.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),TimerActivity.class);
                startActivity(intent);
            }
        });
    }
}
