package com.example.recipeinator.neu;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.recipeinator.models.User;

public class DatabaseAdapter {
    private static DatabaseAdapter instance;
    private UserDAO userDAO;
    private Context context;
    private SQLiteDatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    private DatabaseAdapter(Context context){
        this.context=context;
        databaseHelper = new SQLiteDatabaseHelper(context,"usersDatabase",1);
    }

    private DatabaseAdapter open(){
        database = databaseHelper.getReadableDatabase();
        userDAO = new UserDAO(database);
        return this;
    }

    public static DatabaseAdapter getInstance(Context context){
        if(instance==null){
            synchronized (DatabaseAdapter.class){
                if(instance==null){
                    instance = new DatabaseAdapter(context).open();
                }
            }
        }
        return instance;
    }


    public boolean signIn(String email, String password){
        User current = userDAO.getUserByEmailAndPassword(email,password);
        return current!=null;
    }
    public void addNewUser(User user){
        userDAO.createUser(user);
    }

}
