package com.example.recipeinator.models;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Recipe {
    @PrimaryKey(autoGenerate = true) public int id;
    @ColumnInfo(name = "name") public String name;

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
                '}';
    }
}
