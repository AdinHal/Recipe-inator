package com.example.recipeinator.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Groceries {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "grocery_id") public int id;
    @ColumnInfo(name = "status") public boolean status;
    @ColumnInfo(name = "item") public String item;

    public Groceries(){
    }

    @Ignore
    public Groceries(String item) {
        this.id = 0;
        this.status = false;
        this.item = item;
    }
}
