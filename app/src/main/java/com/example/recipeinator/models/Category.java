package com.example.recipeinator.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id") public int id;
    @ColumnInfo(name = "name") public String name;

    public Category() {
    }

    @Ignore
    public Category(String name) {
        this.name = name;
    }
}
