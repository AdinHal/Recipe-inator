package com.example.recipeinator.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class CategoryWithRecipes {
    @Embedded
    public Category category;
    @Relation(
            parentColumn = "category_id",
            entityColumn = "category_id"
    )
    public List<Recipe> recipes;
}
