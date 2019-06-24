package com.william.reservationsystem.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


public class DBServerForMenu {
    private static final String DB_TABLE = "menus";
    private static final String KEY_DAY = "day";
    private static final String KEY_MENU = "menu";
    private static final String KEY_DISHES_ONE = "dishes_one";
    private static final String KEY_DISHES_TWO = "dishes_two";
    private static final String KEY_DISHES_THREE = "dishes_three";
    private static final String KEY_DISHES_FOUR = "dishes_four";
    private static final String KEY_SOUP = "soup";

    SQLiteDatabase db;
    Context context;

    public DBServerForMenu(Context context) {
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

    /**
     * Publish menu
     *
     * @param day
     * @param menu
     * @param dishes_one
     * @param dishes_two
     * @param dishes_three
     * @param dishes_four
     * @param soup
     * @return
     */
    public boolean insert(String day, String menu, String dishes_one, String dishes_two, String dishes_three, String dishes_four, String soup) {
        boolean result = false;
        // Instantiate content values
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DAY, day);
        contentValues.put(KEY_MENU, menu);
        contentValues.put(KEY_DISHES_ONE, dishes_one);
        contentValues.put(KEY_DISHES_TWO, dishes_two);
        contentValues.put(KEY_DISHES_THREE, dishes_three);
        contentValues.put(KEY_DISHES_FOUR, dishes_four);
        contentValues.put(KEY_SOUP, soup);
        if (db.insert(DB_TABLE, null, contentValues) > 0) {
            result = true;
            Toast.makeText(context, "Menu publishing faileded successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Menu publishing failed", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public Cursor select() {
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{KEY_DAY, KEY_MENU, KEY_DISHES_ONE, KEY_DISHES_TWO, KEY_DISHES_THREE, KEY_DISHES_FOUR, KEY_SOUP},
                null, null, null, null, KEY_DAY);
        return cursor;
    }

    public int select(String date) {
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{KEY_MENU}, KEY_DAY + "='" + date + "'", null, null, null, null);
        if (cursor.getCount() == 1) {
            return 1;
        } else if (cursor.getCount() == 2) {
            return 2;
        } else {
            return 0;
        }
    }

    public Cursor selectByDate(String date) {
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{KEY_DAY, KEY_MENU, KEY_DISHES_ONE, KEY_DISHES_TWO, KEY_DISHES_THREE, KEY_DISHES_FOUR, KEY_SOUP},
                KEY_DAY + "='" + date + "'", null, null, null, KEY_MENU);
        return cursor;
    }

    public int selectDayCount() {
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{KEY_DAY}, null, null, KEY_DAY, null, null);
        return cursor.getCount();
    }

    public boolean update(String day, String menu, String dishes_one, String dishes_two, String dishes_three, String dishes_four, String soup) {
        boolean result = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DAY, day);
        contentValues.put(KEY_MENU, menu);
        contentValues.put(KEY_DISHES_ONE, dishes_one);
        contentValues.put(KEY_DISHES_TWO, dishes_two);
        contentValues.put(KEY_DISHES_THREE, dishes_three);
        contentValues.put(KEY_DISHES_FOUR, dishes_four);
        contentValues.put(KEY_SOUP, soup);
        int n = db.update(DB_TABLE, contentValues, KEY_DAY + "='" + day + "'", null);
        if (n == 1) {
            result = true;
            Toast.makeText(context, "Update database succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Update database failed", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public boolean delete(String day) {
        boolean result = false;
        int n = db.delete(DB_TABLE, KEY_DAY + "='" + day + "'", null);
        if (n != 0) {
            result = true;
            Toast.makeText(context, "Delete succeeded", Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}
