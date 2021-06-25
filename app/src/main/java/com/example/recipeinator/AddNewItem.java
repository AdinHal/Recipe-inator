package com.example.recipeinator;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import com.example.recipeinator.models.Groceries;
import com.example.recipeinator.util.DialogCloseListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class AddNewItem extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";
    private EditText newItemText;
    private Button newItemSaveButton;
    private AppDatabase database;
    private Groceries groceries;

    public AddNewItem(){
        this.database = AppDatabase.getInstance();
    }

    public AddNewItem(Groceries groceries){
        this.database = AppDatabase.getInstance();
        this.groceries = groceries;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL,R.style.DialogStyle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.create_new_item_view, container,false);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newItemText = getView().findViewById(R.id.newTaskText);
        newItemSaveButton = getView().findViewById(R.id.newTaskButton);

        if(groceries!=null){
            newItemText.setText(groceries.item);
            if(groceries.item.length()>0){
                newItemSaveButton.setTextColor(ContextCompat.getColor(getContext(),R.color.black));
            }
        }
        // Listening to event changes
        newItemText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    newItemSaveButton.setEnabled(false);
                    newItemSaveButton.setTextColor(Color.GRAY);
                }else{
                    newItemSaveButton.setEnabled(true);
                    newItemSaveButton.setTextColor(Color.BLACK);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed, here only to not cause errors
            }
        });

        newItemSaveButton.setOnClickListener(v -> {
            String text = newItemText.getText().toString();
            if(groceries != null) {
                groceries.item = text;
                database.groceriesDao().update(groceries);
            }else{
                groceries = new Groceries(text);
                database.groceriesDao().addItems(groceries);
            }
            dismiss();
        });
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface){
        Activity activity = getActivity();

        if(activity instanceof DialogCloseListener){
            ((DialogCloseListener)activity).handleDialogClose(dialogInterface);
        }
    }
}
