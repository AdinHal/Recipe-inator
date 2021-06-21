package com.example.recipeinator.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.recipeinator.Adapters.GroceriesAdapter;
import com.example.recipeinator.AddNewItem;
import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.BottomNavbarListener;
import com.example.recipeinator.util.DialogCloseListener;
import com.example.recipeinator.R;
import com.example.recipeinator.RecyclerItemTouchHelper;
import com.example.recipeinator.models.Groceries;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroceryListActivity extends AppCompatActivity implements DialogCloseListener {
    private GroceriesAdapter groceriesAdapter;
    private List<Groceries> groceriesList;
    private AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groceries_list);

        BottomNavigationView bottomNavbar = findViewById(R.id.bottom_navbar);
        bottomNavbar.setSelectedItemId(R.id.page_list);
        bottomNavbar.setOnNavigationItemSelectedListener(new BottomNavbarListener(this));

        database = Room.databaseBuilder(
            getApplicationContext(),
            AppDatabase.class,
            "database"
        ).allowMainThreadQueries().build();

        groceriesList = new ArrayList<>();

        RecyclerView tasksRecyclerView = findViewById(R.id.tasksRecycleView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceriesAdapter = new GroceriesAdapter(database,this);
        tasksRecyclerView.setAdapter(groceriesAdapter);

        ImageView floatingActionButton = findViewById(R.id.grocerylist_addbutton);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemTouchHelper(groceriesAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        groceriesList = database.groceriesDao().getAllItems();
        Collections.reverse(groceriesList);
        groceriesAdapter.setTasks(groceriesList);

        floatingActionButton.setOnClickListener(v -> new AddNewItem(database).show(getSupportFragmentManager(),AddNewItem.TAG));
    }


    @Override
    public void handleDialogClose(DialogInterface dialogInterface){
        groceriesList = database.groceriesDao().getAllItems();
        Collections.reverse(groceriesList);
        groceriesAdapter.setTasks(groceriesList);
        groceriesAdapter.notifyDataSetChanged();
    }

}
