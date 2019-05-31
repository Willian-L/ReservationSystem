package com.william.reservationsystem.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DBSeverForBookings {
    private static final String DB_TABLE = "bookings";
    private static final String KEY_DAY = "day";
    private static final String KEY_MENU = "menu";
    private static final String KEY_USER = "user";
    private static final String KEY_HAVESOUP = "have_soup";
    private static final String KEY_REMARK = "remark";

    SQLiteDatabase db;
    Context context;

    public DBSeverForBookings(Context context) {
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

    public boolean insert(String day, String menu, String user, int have_soup, String remark) {
        boolean result = false;
        // Instantiate content values
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DAY, day);
        contentValues.put(KEY_MENU, menu);
        contentValues.put(KEY_USER, user);
        contentValues.put(KEY_HAVESOUP, have_soup);
        contentValues.put(KEY_REMARK, remark);
        if (db.insert(DB_TABLE, null, contentValues) > 0) {
            result = true;
            Toast.makeText(context, "Menu publishing faileded successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Menu publishing failed", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public Cursor select(String day, String user){
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{KEY_MENU, KEY_HAVESOUP, KEY_REMARK},
                KEY_DAY + "='" + day + "' and " + KEY_USER + "='" + user + "'" ,
                null, null, null, null);
        return cursor;
    }


    public boolean update(String day, String menu, String user, int have_soup, String remark){
        boolean result = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_DAY, day);
        contentValues.put(KEY_MENU, menu);
        contentValues.put(KEY_USER, user);
        contentValues.put(KEY_HAVESOUP, have_soup);
        contentValues.put(KEY_REMARK, remark);
        int n = db.update(DB_TABLE, contentValues, KEY_DAY + "='" + day + "' and " + KEY_USER + "='" + user + "'" , null);
        if (n == 1) {
            result = true;
            Toast.makeText(context, "Update database succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Update database failed", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    public boolean delete(String day, String user){
        boolean result = false;
        int n = db.delete(DB_TABLE, KEY_DAY + "='" + day + "' and " + KEY_USER + "='" + user + "'", null);
        if (n == 1) {
            result = true;
            Toast.makeText(context, "Delete succeeded", Toast.LENGTH_SHORT).show();
        }
        return result;
    }
}