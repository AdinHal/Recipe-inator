package com.example.recipeinator.util;

import android.app.AlertDialog;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recipeinator.R;

public class RecyclerItemDelete extends RecyclerItemOperation {
    public RecyclerItemDelete(SwipeableAdapter adapter){
        super(0, ItemTouchHelper.LEFT, adapter);
    }

    @Override
    public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction){
        final int position = viewHolder.getBindingAdapterPosition();
        if (getAdapter().requiresConfirmation()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getAdapter().getContext());
            builder.setTitle(R.string.delete_item);
            builder.setMessage(R.string.delete_warning);
            builder.setPositiveButton(R.string.confirm, (dialog, which) -> getAdapter().deleteItem(position));
            builder.setNegativeButton(android.R.string.cancel, (dialog, which) -> getAdapter().notifyItemChanged(viewHolder.getBindingAdapterPosition()));
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            getAdapter().deleteItem(position);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        Drawable icon = ContextCompat.getDrawable(getAdapter().getContext(), R.drawable.deleteitem);
        ColorDrawable background = new ColorDrawable(Color.RED);

        assert icon != null;
        int backgroundCornerOffset = 20;
        int iconMargin = (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconTop = itemView.getTop() + (itemView.getHeight() - icon.getIntrinsicHeight()) / 2;
        int iconBottom = iconTop + icon.getIntrinsicHeight();

       if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - iconMargin - icon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMargin;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        icon.draw(c);
    }
}
