package com.example.recipeinator.Activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeinator.Adapters.GroceriesAdapter;
import com.example.recipeinator.AddNewItem;
import com.example.recipeinator.DialogCloseListener;
import com.example.recipeinator.MainActivity;
import com.example.recipeinator.Model.GroceriesModel;
import com.example.recipeinator.R;
import com.example.recipeinator.RecyclerItemTouchHelper;
import com.example.recipeinator.Utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroceryListActivity extends AppCompatActivity implements DialogCloseListener {

    private ImageView homebtn, searchbtn, randombtn, grocerylistbtn, navprofilebtn;
    private RecyclerView tasksRecyclerView;
    private GroceriesAdapter groceriesAdapter;
    private ImageView floatingActionButton;

    private List<GroceriesModel> groceriesList;
    private DatabaseHandler databaseHandler;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocerylistview);

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

        databaseHandler = new DatabaseHandler(this);
        databaseHandler.openDatabase();

        groceriesList = new ArrayList<>();

        tasksRecyclerView = findViewById(R.id.tasksRecycleView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceriesAdapter = new GroceriesAdapter(databaseHandler,this);
        tasksRecyclerView.setAdapter(groceriesAdapter);

        floatingActionButton = findViewById(R.id.grocerylist_addbutton);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(groceriesAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        groceriesList = databaseHandler.getAllItems();
        Collections.reverse(groceriesList);
        groceriesAdapter.setTasks(groceriesList);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewItem.newInstance().show(getSupportFragmentManager(),AddNewItem.TAG);
            }
        });
    }


    @Override
    public void handleDialogClose(DialogInterface dialogInterface){
        groceriesList = databaseHandler.getAllItems();
        Collections.reverse(groceriesList);
        groceriesAdapter.setTasks(groceriesList);
        groceriesAdapter.notifyDataSetChanged();
    }

}
