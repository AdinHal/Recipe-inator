package com.example.recipeinator.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.recipeinator.Model.GroceriesModel;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "grocerieslistdb";
    private static final String GROCERIES_TABLE = "groceries";
    private static final String ID = "id";
    private static final String ITEM = "item";
    private static final String STATUS = "status";
    private static final String CREATE_GROCERIES_TABLE = "CREATE TABLE " + GROCERIES_TABLE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + ITEM + " TEXT, "
            + STATUS + " INTEGER)";

    private SQLiteDatabase database;

    public DatabaseHandler(Context context){
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        database.execSQL(CREATE_GROCERIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        // Removing an old list/table if exists
        database.execSQL("DROP TABLE IF EXISTS "+GROCERIES_TABLE);
        // Creating brand new table
        onCreate(database);
    }

    public void openDatabase() {
        database = this.getWritableDatabase();
    }

    public void addItems(GroceriesModel groceriesModel){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM,groceriesModel.getItem());
        contentValues.put(STATUS, 0);
        database.insert(GROCERIES_TABLE, null, contentValues);
    }

    public List<GroceriesModel> getAllItems(){
        List<GroceriesModel>itemList = new ArrayList<>();
        Cursor cursor = null;
        database.beginTransaction();

        try {
            cursor = database.query(GROCERIES_TABLE, null, null, null, null, null, null, null);
            if (cursor != null) {
                if(cursor.moveToFirst()){
                    do {
                        GroceriesModel groceriesModel = new GroceriesModel();
                        groceriesModel.setId(cursor.getInt(cursor.getColumnIndex(ID)));
                        groceriesModel.setItem(cursor.getString(cursor.getColumnIndex(ITEM)));
                        groceriesModel.setStatus(cursor.getInt(cursor.getColumnIndex(STATUS)));
                        itemList.add(groceriesModel);
                    } while (cursor.moveToNext());
                }
            }
        }
        finally {
            database.endTransaction();
            assert cursor != null;
            cursor.close();
        }
        return itemList;
    }

    public void statusUpdate(int id, int status){
        ContentValues contentValues = new ContentValues();
        contentValues.put(STATUS, status);
        database.update(GROCERIES_TABLE, contentValues, ID + "=?",new String[]{String.valueOf(id)});
    }

    public void taskUpdate(int id, String item){
        ContentValues contentValues = new ContentValues();
        contentValues.put(ITEM, item);
        database.update(GROCERIES_TABLE, contentValues, ID + "=?",new String[]{String.valueOf(id)});
    }

    public void taskRemove(int id){
        database.delete(GROCERIES_TABLE, ID + "=?",new String[]{String.valueOf(id)});
    }
}
