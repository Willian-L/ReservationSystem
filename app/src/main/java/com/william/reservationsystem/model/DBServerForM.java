package com.william.reservationsystem.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.william.reservationsystem.model.DBHelper;

public class DBServerForM {

    private static final String DB_TABLE = "master";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAME = "name";
    private static final String KEY_SEX = "sex";
    private static final String KEY_AGE = "age";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHOTO = "photo";

    SQLiteDatabase db;
    Context context;

    public DBServerForM(Context context) {
        this.context = context;
    }

    /*
    Open database
     */
    public void open() {
        DBHelper.SystemOpenHelper dbHelper = new DBHelper.SystemOpenHelper(context);
        /*
        Try to open the database read-write.
        Open the database read-only if errors occurred such as bad permissions or a full disk.
        */
        try {
            db = dbHelper.getWritableDatabase();
        } catch (Exception e) {
            db = dbHelper.getReadableDatabase();
        }
    }

    /*
    Close database
     */
    public void close() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    /*
    Register and add data to the database
     */
    public boolean insert(String username, String password) {
        boolean result = false;
        // Instantiate content values
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_PASSWORD, password);
        if (db.insert(DB_TABLE, null, contentValues) > 0) {
            result = true;
        }
        return result;
    }

    /*
    Account login judgment
     */
    public boolean login(String username, String password) {
        boolean result = false;
        Cursor cursor = null;
        // Get a cursor object
        cursor = db.query("master", new String[]{},
                "username='" + username + "'and password='" + password + "'",
                null, null, null, null);
        if (cursor.getCount() > 0) {
            result = true;
        }
        return result;
    }

    /*
    Query all administrator data
     */
    public Cursor selectAll() {
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{KEY_ID, KEY_USERNAME},
                null, null, null, null, null);
        return cursor;
    }

    /*
    Query administrator data by username
     */
    public Cursor selectByUsername(String username) {
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{KEY_ID, KEY_USERNAME},
                KEY_USERNAME + "='" + username + "'",
                null, null, null, null);
        return cursor;
    }

    /*
    Delete administrator data by id
     */
    public boolean deleteByID(int id) {
        boolean result = false;
        int n = db.delete(DB_TABLE, KEY_ID + "='" + id + "'", null);
        if (n > 0) {
            result = true;
        } else {
            Toast.makeText(context, "Delete id:" + id + " failed", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    /*
    Delete administrator data by username
     */
    public boolean deleteByUsername(String username) {
        boolean result = false;
        int n = db.delete(DB_TABLE, KEY_USERNAME + "='" + username + "'", null);
        if (n > 0) {
            result = true;
        } else {
            Toast.makeText(context, "Delete username:" + username + " failed", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    /*
    Update database by ID
     */
    public boolean updataID(int id, String username, String password) {
        boolean result = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, id);
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_PASSWORD, password);
        int n = db.update(DB_TABLE, contentValues, KEY_ID + "=" + id, null);
        if (n == 1) {
            result = true;
            Log.i("sql", "Update database succeeded");
        } else {
            Log.i("sql", "Update database failed");
        }
        return result;
    }

    /*
    Update database by Username
     */
    public boolean updataUsername(String username, String name, String sex, int age, String email, String photo) {
        boolean result = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_SEX, sex);
        contentValues.put(KEY_AGE, age);
        contentValues.put(KEY_EMAIL, email);
        contentValues.put(KEY_PHOTO, photo);
        int n = db.update(DB_TABLE, contentValues, KEY_USERNAME + "=" + username, null);
        if (n == 1) {
            result = true;
            Log.i("sql", "Update database succeeded");
        } else {
            Log.i("sql", "Update database failed");
        }
        return result;
    }
}
