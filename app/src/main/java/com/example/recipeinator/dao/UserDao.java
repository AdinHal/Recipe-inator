package com.example.recipeinator.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.recipeinator.models.User;

@Dao
public interface UserDao {
    @Query("SELECT EXISTS(SELECT * FROM User WHERE email=:email and password=:password)")
    boolean getUserByEmailAndPassword(String email, String password);

    @Insert
    void insertAll(User... users);
}
