package com.example.recipeinator.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeinator.util.SwipeableAdapter;

public abstract class RecyclerItemOperation extends ItemTouchHelper.SimpleCallback {
    private final SwipeableAdapter adapter;

    public RecyclerItemOperation(int dragDirs, int swipeDirs, SwipeableAdapter adapter) {
        super(dragDirs, swipeDirs);
        this.adapter = adapter;
    }

    public SwipeableAdapter getAdapter() {
        return adapter;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }
}
