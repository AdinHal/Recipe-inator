package com.example.recipeinator.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeinator.Adapters.GroceriesAdapter;
import com.example.recipeinator.MainActivity;
import com.example.recipeinator.Model.GroceriesModel;
import com.example.recipeinator.R;

import java.util.ArrayList;
import java.util.List;

public class GroceryListActivity extends AppCompatActivity {

    private ImageView homebtn, searchbtn, randombtn, grocerylistbtn, navprofilebtn;
    private RecyclerView tasksRecyclerView;
    private GroceriesAdapter groceriesAdapter;

    private List<GroceriesModel> groceriesList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocerylistview);

        groceriesList = new ArrayList<>();

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

        tasksRecyclerView = findViewById(R.id.tasksRecycleView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceriesAdapter = new GroceriesAdapter(this);
        tasksRecyclerView.setAdapter(groceriesAdapter);

        // test
        GroceriesModel groceries = new GroceriesModel();
        groceries.setItem("Test item");
        groceries.setStatus(0);
        groceries.setId(1);

        groceriesList.add(groceries);
        groceriesList.add(groceries);
        groceriesList.add(groceries);
        groceriesList.add(groceries);
        groceriesList.add(groceries);

        groceriesAdapter.setTask(groceriesList);
    }




}
