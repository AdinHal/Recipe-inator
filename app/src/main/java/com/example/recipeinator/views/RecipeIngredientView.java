package com.example.recipeinator.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.room.Room;

import com.example.recipeinator.AppDatabase;
import com.example.recipeinator.R;
import com.example.recipeinator.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeIngredientView extends ListView {
    public RecipeIngredientView(Context context) {
        super(context);
        initView(context);
    }

    public RecipeIngredientView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RecipeIngredientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
//
//    //LIST OF ARRAY STRINGS WHICH WILL SERVE AS LIST ITEMS
//    ArrayList<String> listItems=new ArrayList<String>();
//
//    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
//    ArrayAdapter<String> adapter;
//
//    //RECORDING HOW MANY TIMES THE BUTTON HAS BEEN CLICKED
//    int clickCounter=0;
//
//    @Override
//    public void onCreate(Bundle icicle) {
//        super.onCreate(icicle);
//        setContentView(R.layout.main);
//        adapter=new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1,
//                listItems);
//        setListAdapter(adapter);
//    }
//
//    //METHOD WHICH WILL HANDLE DYNAMIC INSERTION
//    public void addItems(View v) {
//        listItems.add("Clicked : "+clickCounter++);
//        adapter.notifyDataSetChanged();
//    }

    public void initView(Context context){
        // View view = inflate(context, R.layout.recipe_ingredient_view, this);
//        AppDatabase database = Room.databaseBuilder(
//                context.getApplicationContext(),
//                AppDatabase.class,
//                "database"
//        ).allowMainThreadQueries().build();
//
//        List<String> ingredientNames = new ArrayList<>();
//        List<Ingredient> ingredients = database.ingredientDao().getAll();
//        for (Ingredient ingredient: ingredients){
//            ingredientNames.add(ingredient.name);
//        }

//        ArrayAdapter<String> adapter = new ArrayAdapter<>(
//                context,
//                android.R.layout.simple_dropdown_item_1line,
//                ingredientNames
//        );
//        AutoCompleteTextView textView = view.findViewById(R.id.ingredient);
//        textView.setAdapter(adapter);
    }
}
