package com.example.recipeinator.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices={@Index(value="name", unique=true)})
public class Ingredient {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ingredient_id") public int id;
    @ColumnInfo(name = "name") public String name;

    public Ingredient(){
    }

    @Ignore
    public Ingredient(String name) {
        this.name = name;
    }

    @Ignore
    public Ingredient(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
