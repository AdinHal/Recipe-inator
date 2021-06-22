package com.example.recipeinator.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(indices={@Index(value="name", unique=true)})
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipe_id") public int id;
    @ColumnInfo(name = "name") public String name;
    @ColumnInfo(name = "description") public String description;
    @ColumnInfo(name = "preparation_time") public int preparationTime;
    @ColumnInfo(name = "picture_uri") public String pictureUri;
    @ColumnInfo(name = "instructions") public String instructions;
    @Ignore private List<MeasuredIngredient> ingredients = new ArrayList<>();

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

    public void addIngredient(Ingredient ingredient, int amount, String unit){
        ingredients.add(new MeasuredIngredient(ingredient, amount, unit));
    }

    public List<MeasuredIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<MeasuredIngredient> ingredients) {
        this.ingredients = ingredients;
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
