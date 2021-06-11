package com.example.recipeinator.Adapters;

import com.example.recipeinator.AddNewItem;
import com.example.recipeinator.R;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeinator.Activities.GroceryListActivity;
import com.example.recipeinator.Model.GroceriesModel;
import com.example.recipeinator.Utils.DatabaseHandler;

import java.util.List;

public class GroceriesAdapter extends RecyclerView.Adapter<GroceriesAdapter.ViewHolder> {

    private List<GroceriesModel> groceriesList;
    private GroceryListActivity activity;
    private DatabaseHandler databaseHandler;

    public GroceriesAdapter(DatabaseHandler databaseHandler,GroceryListActivity activity){
        this.activity=activity;
        this.databaseHandler = databaseHandler;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.taskview,parent,false);
        return new ViewHolder(itemView);
    }

    public void onBindViewHolder(ViewHolder holder, int position){
        databaseHandler.openDatabase();
        GroceriesModel item = groceriesList.get(position);
        holder.task.setText(item.getItem());
        holder.task.setChecked(toBoolean(item.getStatus()));
        holder.task.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    databaseHandler.statusUpdate(item.getId(), 1);
                }else{
                    databaseHandler.statusUpdate(item.getId(),0);
                }
            }
        });
    }
    @Override
    public int getItemCount(){
        return groceriesList.size();
    }

    private boolean toBoolean(int number){
        return number!=0;
    }

    public void setTasks(List<GroceriesModel>groceriesList){
        this.groceriesList=groceriesList;
        notifyDataSetChanged();
    }

    public Context getContext(){
        return activity;
    }

    public void deleteItem(int position){
        GroceriesModel item = groceriesList.get(position);
        databaseHandler.taskRemove(item.getId());
        groceriesList.remove(position);
        notifyItemRemoved(position);

    }

    public void editItem(int position){
        GroceriesModel item = groceriesList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("id",item.getId());
        bundle.putString("item",item.getItem());
        AddNewItem fragment = new AddNewItem();
        fragment.setArguments(bundle);
        fragment.show(activity.getSupportFragmentManager(),AddNewItem.TAG);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;

        ViewHolder(View view){
            super(view);
            task=view.findViewById(R.id.todo_checkbox);
        }
    }
}
