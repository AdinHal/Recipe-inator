package com.example.recipeinator.Adapters;

import android.content.Context;

import com.example.recipeinator.dao.UserDAO;
import com.example.recipeinator.models.User;

public class DatabaseAdapter {
    private static DatabaseAdapter instance;
    private UserDAO userDAO;


    public boolean signIn(String email, String password){
        User current = userDAO.getUserByEmailAndPassword(email,password);
        return current!=null;
    }
    public void addNewUser(User user){
        userDAO.createUser(user);
    }
    /*
    private static DatabaseAdapter instance;
    private static final String DATABASE_NAME = "recipesDatabase";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Context mContext;

    private UserDAO userDAO;


    public static DatabaseAdapter getInstance(Context context) {
        if (instance == null) {
            synchronized (DatabaseAdapter.class) {
                if (instance == null)
                    instance = new DatabaseAdapter(context).open();
            }
        }

        return instance;
    }

    private DatabaseAdapter(Context context) {
        mContext = context;
        dbHelper = new SQLiteDatabaseHelper(context, DATABASE_NAME, DATABASE_VERSION);
    }

    private DatabaseAdapter open() {
        db = dbHelper.getWritableDatabase();
        userDAO = new UserDAO(db);
        return this;
    }*/
}
