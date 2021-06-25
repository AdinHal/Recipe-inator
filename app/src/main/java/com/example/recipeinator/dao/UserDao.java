package com.example.recipeinator.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.recipeinator.models.User;
import com.example.recipeinator.models.UserRecipeCrossRef;
import com.example.recipeinator.models.UserWithRecipes;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User WHERE email=:email and password=:password")
    User getUserByEmailAndPassword(String email, String password);

    @Insert
    void insertAll(User... users);

    @Transaction
    @Query("SELECT * FROM User WHERE user_id=:userId")
    UserWithRecipes getUserWithRecipes(int userId);

    @Query("SELECT EXISTS(SELECT * FROM UserRecipeCrossRef WHERE user_id=:userId AND recipe_id=:recipeId)")
    boolean isUserFavoriteRecipe(int userId, int recipeId);

    @Insert
    void insertUserRecipes(UserRecipeCrossRef... crossRefs);

    @Delete
    void deleteUserRecipes(UserRecipeCrossRef... crossRefs);
}
