package com.example.muhammadsalah.nesmaweatherapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Salah on 12/4/2017.
 */

public class WeatherDataSQLite extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "weather";

    // Weather table name
    private static final String TABLE_WEATHER = "weather";

    // weather Table Columns names
    private static final String MAX = "max";
    private static final String MIN = "min";
    private static final String STATUS = "status";
    private static final String ICON = "icon";
    private static final String DAY = "day";
    private static final String ID = "ID";


    public WeatherDataSQLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_WEATHER_TABLE = "CREATE TABLE " + TABLE_WEATHER + "("
                + ID + " INTEGER PRIMARY KEY," + DAY + " TEXT,"
                + STATUS + " TEXT," + ICON + " INTEGER,"
                + MAX + " TEXT," + MIN +" TEXT"+")";
        sqLiteDatabase.execSQL(CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_WEATHER);

        // Create tables again
        onCreate(sqLiteDatabase);
    }


    // Adding new Item
    public void addItem(Item item,int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

            values.put(MAX, item.getUpper()); // max temp
            values.put(MIN, item.getLower()); // min temp
            values.put(STATUS, item.getStatus()); // status temp
            values.put(DAY, item.getDay()); // status temp
            values.put(ICON, item.getImg()); // status temp
            values.put(ID, id);

            // Inserting Row
            db.insert(TABLE_WEATHER, null, values);
            db.close(); // Closing database connection

    }

    // Getting single Item
    public Item getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WEATHER, new String[] { ID,
                        DAY,STATUS,ICON, MAX, MIN }, ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item(cursor.getString(1),cursor.getString(2), cursor.getInt(3),cursor.getString(4),cursor.getString(5));
        return item;
    }

    // Getting All Items
    public ArrayList<Item> getAllItems() {
        ArrayList<Item> itemList = new ArrayList<Item>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WEATHER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do{
                Item item = new Item(cursor.getString(1),cursor.getString(2), cursor.getInt(3),cursor.getString(4),cursor.getString(5));
                itemList.add(item);
            }while (cursor.moveToNext());
        }

        // return contact list
        return itemList;
    }

    // Getting Items Count
    //public int getItemsCount() {}
    // Updating single Item
    public int updateItem(int id, Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DAY, item.getDay());
        values.put(STATUS, item.getStatus());
        values.put(ICON, item.getImg());
        values.put(MAX, item.getUpper());
        values.put(MIN, item.getLower());

        // updating row
        return db.update(TABLE_WEATHER, values, ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // Deleting single Item
    public void deleteItem(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEATHER, id + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }


}
