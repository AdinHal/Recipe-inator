package com.example.recipeinator.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices={@Index(value="name", unique=true)})
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipe_id") public int id;
    @ColumnInfo(name = "name") public String name;
    @ColumnInfo(name = "preparation_time") public int preparationTime;

    public Recipe(){
    }

    @Ignore
    public Recipe(String name) {
        this.name = name;
    }

    @Ignore
    public Recipe(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", preparationTime=" + preparationTime +
                '}';
    }
}
