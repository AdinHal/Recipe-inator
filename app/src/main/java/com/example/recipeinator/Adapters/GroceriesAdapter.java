package com.example.recipeinator.Adapters;

import com.example.recipeinator.AddNewItem;
import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeinator.Activities.GroceryListActivity;
import com.example.recipeinator.models.Groceries;

import java.util.List;

public class GroceriesAdapter extends RecyclerView.Adapter<GroceriesAdapter.ViewHolder> {

    private List<Groceries> groceriesList;
    private GroceryListActivity activity;
    private AppDatabase database;

    public GroceriesAdapter(AppDatabase database, GroceryListActivity activity){
        this.activity=activity;
        this.database = database;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskview,parent,false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        Groceries item = groceriesList.get(position);
        holder.task.setText(item.item);
        holder.task.setChecked(item.status);
        holder.task.setOnCheckedChangeListener((buttonView, isChecked) -> {
            item.status = isChecked;
            database.groceriesDao().update(item);
        });
    }
    @Override
    public int getItemCount(){
        return groceriesList.size();
    }

    public void setTasks(List<Groceries>groceriesList){
        this.groceriesList=groceriesList;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return activity;
    }

    public void deleteItem(int position){
        Groceries item = groceriesList.get(position);
        database.groceriesDao().taskRemove(item);
        groceriesList.remove(position);
        notifyItemRemoved(position);

    }

    public void editItem(int position){
        Groceries item = groceriesList.get(position);
        new AddNewItem(database, item).show(activity.getSupportFragmentManager(),AddNewItem.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task=view.findViewById(R.id.todo_checkbox);
        }
    }
}
