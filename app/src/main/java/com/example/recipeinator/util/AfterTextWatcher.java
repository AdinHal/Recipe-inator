package com.example.recipeinator.util;

import android.text.Editable;
import android.text.TextWatcher;

public class AfterTextWatcher implements TextWatcher {
    private Consumer<Editable> consumer;

    public AfterTextWatcher(Consumer<Editable> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // ignore
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // ignore
    }

    @Override
    public void afterTextChanged(Editable s) {
        consumer.accept(s);
    }
}
