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
import com.example.recipeinator.util.RecyclerItemDelete;
import com.example.recipeinator.util.RecyclerItemEdit;
import com.example.recipeinator.util.DialogCloseListener;
import com.example.recipeinator.R;
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

        database = AppDatabase.getInstance();

        groceriesList = new ArrayList<>();

        RecyclerView tasksRecyclerView = findViewById(R.id.tasksRecycleView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceriesAdapter = new GroceriesAdapter(this);
        tasksRecyclerView.setAdapter(groceriesAdapter);

        ImageView floatingActionButton = findViewById(R.id.grocerylist_addbutton);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerItemEdit(groceriesAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);
        itemTouchHelper = new ItemTouchHelper(new RecyclerItemDelete(groceriesAdapter));
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView);

        groceriesList = database.groceriesDao().getAllItems();
        Collections.reverse(groceriesList);
        groceriesAdapter.setTasks(groceriesList);

        floatingActionButton.setOnClickListener(v -> new AddNewItem().show(getSupportFragmentManager(),AddNewItem.TAG));
    }


    @Override
    public void handleDialogClose(DialogInterface dialogInterface){
        groceriesList = database.groceriesDao().getAllItems();
        Collections.reverse(groceriesList);
        groceriesAdapter.setTasks(groceriesList);
        groceriesAdapter.notifyDataSetChanged();
    }

}
