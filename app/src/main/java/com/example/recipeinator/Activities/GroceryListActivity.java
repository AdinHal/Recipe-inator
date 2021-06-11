package com.example.recipeinator.Activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.recipeinator.Adapters.GroceriesAdapter;
import com.example.recipeinator.AddNewItem;
import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.DialogCloseListener;
import com.example.recipeinator.R;
import com.example.recipeinator.RecyclerItemTouchHelper;
import com.example.recipeinator.models.Groceries;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroceryListActivity extends AppCompatActivity implements DialogCloseListener {

    private ImageView homebtn, searchbtn, randombtn, grocerylistbtn, navprofilebtn;
    private RecyclerView tasksRecyclerView;
    private GroceriesAdapter groceriesAdapter;
    private ImageView floatingActionButton;

    private List<Groceries> groceriesList;
    private AppDatabase database;

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
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
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

        database = Room.databaseBuilder(
            getApplicationContext(),
            AppDatabase.class,
            "database"
        ).allowMainThreadQueries().build();

        groceriesList = new ArrayList<>();

        tasksRecyclerView = findViewById(R.id.tasksRecycleView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceriesAdapter = new GroceriesAdapter(database,this);
        tasksRecyclerView.setAdapter(groceriesAdapter);

        floatingActionButton = findViewById(R.id.grocerylist_addbutton);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(groceriesAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        groceriesList = database.groceriesDao().getAllItems();
        Collections.reverse(groceriesList);
        groceriesAdapter.setTasks(groceriesList);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddNewItem(database).show(getSupportFragmentManager(),AddNewItem.TAG);
            }
        });
    }


    @Override
    public void handleDialogClose(DialogInterface dialogInterface){
        groceriesList = database.groceriesDao().getAllItems();
        Collections.reverse(groceriesList);
        groceriesAdapter.setTasks(groceriesList);
        groceriesAdapter.notifyDataSetChanged();
    }

}
