package com.example.recipeinator.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

@Entity(primaryKeys = {"user_id", "recipe_id"}, indices = {@Index("recipe_id")})
public class UserRecipeCrossRef {
    @ColumnInfo(name = "user_id") public int userId;
    @ColumnInfo(name = "recipe_id") public int recipeId;

    public UserRecipeCrossRef(){
    }

    @Ignore
    public UserRecipeCrossRef(int userId, int recipeId){
        this.userId = userId;
        this.recipeId = recipeId;
    }
}
