package com.example.recipeinator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.recipeinator.MainActivity;
import com.example.recipeinator.R;

public class SearchActivity extends AppCompatActivity {

    private ImageView homebtn, searchbtn, randombtn, grocerylistbtn, navprofilebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchview);

        // Linking ImageViews with NavBar

        homebtn = findViewById(R.id.navbar_home);
        searchbtn = findViewById(R.id.navbar_search);
        randombtn = findViewById(R.id.navbar_random);
        grocerylistbtn = findViewById(R.id.navbar_grocerylist);
        navprofilebtn = findViewById(R.id.navbar_profile);

        // Linking Buttons with Activities

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        randombtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RandomActivity.class);
                startActivity(intent);
            }
        });
        grocerylistbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GroceryListActivity.class);
                startActivity(intent);
            }
        });
        navprofilebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
}
