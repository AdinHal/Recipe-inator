package com.example.recipeinator.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.recipeinator.R;

public class RatingView extends View {
    private final Drawable drawable;
    private int maxItemCount;
    private int itemCount;

    public RatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DifficultyRating, 0, 0);

        try {
            drawable = a.getDrawable(R.styleable.DifficultyRating_itemDrawable);
            itemCount = a.getInteger(R.styleable.DifficultyRating_itemCount, 5);
            maxItemCount = a.getInteger(R.styleable.DifficultyRating_maxItemCount, 0);
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int itemWidth = width / maxItemCount;
        int height = getHeight();
        Bitmap bitmap = scaleDrawable(drawable, itemWidth, height);
        for (int i = 0; i < itemCount; i++) {
            canvas.drawBitmap(bitmap, itemWidth * i, 0, null);
        }
    }

    private Bitmap scaleDrawable(Drawable drawable, int width, int height) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        return bitmap;
    }

    public int getMaxItemCount() {
        return maxItemCount;
    }

    public void setMaxItemCount(int maxItemCount) {
        this.maxItemCount = maxItemCount;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }
}
