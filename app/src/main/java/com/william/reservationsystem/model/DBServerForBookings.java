package com.william.reservationsystem.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DBServerForBookings {
    private static final String DB_TABLE = "bookings";
    private static final String KEY_ID = "_id";
    private static final String KEY_DAY = "day";
    private static final String KEY_MENU = "menu";
    private static final String KEY_USER = "user";
    private static final String KEY_DISHES_ONE = "dishes_one";
    private static final String KEY_DISHES_TWO = "dishes_two";
    private static final String KEY_DISHES_THREE = "dishes_three";
    private static final String KEY_DISHES_FOUR = "dishes_four";
    private static final String KEY_SOUP = "soup";
    private static final String KEY_REMARK = "remark";

    SQLiteDatabase db;
    Context context;

    public DBServerForBookings(Context context) {
        this.context = context;
    }

    /**
     * Open database
     */
    public void open() {
        DBHelper.SystemOpenHelper dbHelper = new DBHelper.SystemOpenHelper(context);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    /**
     * Close database
     */
    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    public boolean insert(String day, String menu, String user, String dishes_one, String dishes_two, String dishes_three, String dishes_four, String soup, String remark) {
        boolean result = false;
        // Instantiate content values
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DAY, day);
        contentValues.put(KEY_MENU, menu);
        contentValues.put(KEY_USER, user);
        contentValues.put(KEY_DISHES_ONE, dishes_one);
        contentValues.put(KEY_DISHES_TWO, dishes_two);
        contentValues.put(KEY_DISHES_THREE, dishes_three);
        contentValues.put(KEY_DISHES_FOUR, dishes_four);
        contentValues.put(KEY_SOUP, soup);
        contentValues.put(KEY_REMARK, remark);
        if (db.insert(DB_TABLE, null, contentValues) > 0) {
            result = true;
            Toast.makeText(context, "Menu publishing faileded successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Menu publishing failed", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public Cursor selectByUser(String user) {
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{KEY_ID, KEY_DAY, KEY_MENU, KEY_DISHES_ONE, KEY_DISHES_TWO, KEY_DISHES_THREE, KEY_DISHES_FOUR, KEY_SOUP, KEY_REMARK},
                KEY_USER + "='" + user + "'",
                null, null, null, KEY_ID + " DESC");
        return cursor;
    }

    public Cursor selectByDay(String day, String user) {
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{},
                KEY_DAY + "='" + day + "' and " + KEY_USER + "='" + user + "'",
                null, null, null, null);
        return cursor;
    }

    public Cursor selectByMenu(String day, String menu){
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{}, KEY_DAY + "='" + day + "' and " + KEY_MENU + "='" + menu + "'",
                null,null,null,null);
        return cursor;
    }

    public Cursor selectDetail(String day, String menu){
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{KEY_ID, KEY_USER, KEY_SOUP}, KEY_DAY + "='" + day + "' and " + KEY_MENU + "='" + menu + "'",
                null,null,null,KEY_ID);
        return cursor;
    }

    public Cursor selectSoup(String day, String soup){
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{}, KEY_DAY + "='" + day + "' and " + KEY_SOUP + "='" + soup + "'",
                null,null,null,null);
        return cursor;
    }

    public boolean update(String day, String menu, String user, String dishes_one, String dishes_two, String dishes_three, String dishes_four, String soup, String remark) {
        boolean result = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DAY, day);
        contentValues.put(KEY_MENU, menu);
        contentValues.put(KEY_USER, user);
        contentValues.put(KEY_DISHES_ONE, dishes_one);
        contentValues.put(KEY_DISHES_TWO, dishes_two);
        contentValues.put(KEY_DISHES_THREE, dishes_three);
        contentValues.put(KEY_DISHES_FOUR, dishes_four);
        contentValues.put(KEY_SOUP, soup);
        contentValues.put(KEY_REMARK, remark);
        int n = db.update(DB_TABLE, contentValues, KEY_DAY + "='" + day + "' and " + KEY_USER + "='" + user + "'", null);
        if (n == 1) {
            result = true;
        }
        return result;
    }

    public boolean delete(String day, String user) {
        boolean result = false;
        int n = db.delete(DB_TABLE, KEY_DAY + "='" + day + "' and " + KEY_USER + "='" + user + "'", null);
        if (n == 1) {
            result = true;
            Toast.makeText(context, "Delete succeeded", Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
