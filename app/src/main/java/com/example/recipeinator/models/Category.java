package com.example.recipeinator.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices={@Index(value="name", unique=true)})
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

    @Override
    public String toString() {
        return name;
    }
}
