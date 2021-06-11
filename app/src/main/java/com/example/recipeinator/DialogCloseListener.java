package com.example.recipeinator;

import android.content.DialogInterface;

public interface DialogCloseListener {
    default void handleDialogClose(DialogInterface dialogInterface){

    }
}
