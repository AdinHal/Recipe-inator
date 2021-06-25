package com.example.recipeinator.models;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class UserWithRecipes {
    @Embedded public User user;
    @Relation(
            parentColumn = "user_id",
            entityColumn = "recipe_id",
            associateBy = @Junction(UserRecipeCrossRef.class)
    )
    public List<Recipe> recipes;
}
