package com.example.recipeinator.util;

import android.content.DialogInterface;

public interface DialogCloseListener {
    default void handleDialogClose(DialogInterface dialogInterface){

    }
}
