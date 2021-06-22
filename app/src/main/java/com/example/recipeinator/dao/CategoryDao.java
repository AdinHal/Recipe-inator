package com.example.recipeinator.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.recipeinator.models.Category;
import com.example.recipeinator.models.CategoryWithRecipes;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insertAll(Category... categories);

    @Query("SELECT * FROM Category")
    List<Category> getAll();

    @Transaction
    @Query("SELECT * FROM Category WHERE category_id=:categoryId")
    CategoryWithRecipes getCategoryWithRecipes(int categoryId);
}
