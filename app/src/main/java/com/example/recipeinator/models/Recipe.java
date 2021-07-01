package com.example.recipeinator.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Recipe {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "recipe_id") public int id;
    @ColumnInfo(name = "name") public String name;
    @ColumnInfo(name = "description") public String description;
    @ColumnInfo(name = "preparation_time") public int preparationTime;
    @ColumnInfo(name = "picture_uri") public String pictureUri;
    @ColumnInfo(name = "instructions") public String instructions;
    @ColumnInfo(name = "category_id") public int categoryId;
    @ColumnInfo(name = "servings", defaultValue = "1") public int servings;
    @ColumnInfo(name = "difficulty", defaultValue = "1") public int difficulty;

    @Ignore private List<MeasuredIngredient> ingredients = new ArrayList<>();
    @Ignore private Category category;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory(){
        return category;
    }
}
