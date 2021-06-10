package com.example.recipeinator;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeinator.Activities.GroceryListActivity;
import com.example.recipeinator.Activities.HomeActivity;
import com.example.recipeinator.Activities.ProfileActivity;
import com.example.recipeinator.Activities.RandomActivity;
import com.example.recipeinator.Activities.SearchActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        }, 1700);
    }

}