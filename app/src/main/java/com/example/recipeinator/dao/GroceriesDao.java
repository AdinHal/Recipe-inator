package com.example.recipeinator.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.recipeinator.models.Groceries;

import java.util.List;

@Dao
public interface GroceriesDao {
    @Insert
    void addItems(Groceries groceries);

    @Query("SELECT * FROM Groceries")
    List<Groceries> getAllItems();

    @Delete
    void taskRemove(Groceries groceries);

    @Update
    void update(Groceries groceries);
}
