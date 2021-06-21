package com.example.recipeinator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeinator.Activities.HomeActivity;
import com.example.recipeinator.Activities.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        new Handler().postDelayed(() -> startActivity(intent), 1700);
    }
}