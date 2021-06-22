package com.example.recipeinator.util;

import android.content.Context;

public interface SwipeableAdapter {
    Context getContext();
    void editItem(int position);
    void deleteItem(int position);
    boolean requiresConfirmation();
    void notifyItemChanged(int position);
}
