package com.william.reservationsystem.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DBServerForM {

    private static final String DB_TABLE = "master";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

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
            Toast.makeText(context, "Insert username:" + username + " succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Insert username:" + username + " failed", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    /*
    Account login judgment
     */
    public int login(String username, String password) {
        int result = 0;
        Cursor cursor = null;
        // Get a cursor object
        cursor = db.query("master", new String[]{"password"},
                "username='" + username + "'",
                null, null, null, null);
        Log.i("login", "cursor" + cursor.getCount() );
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String thepassword = cursor.getString(cursor.getColumnIndex("password"));
                if (thepassword.equals(password)) {
                    result = 2;
                } else {
                    result = 1;
                }
            }
        } else {
            result = 0;
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
            Toast.makeText(context, "Delete id:" + id + " succeeded", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, "Delete username:" + username + " succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Delete username:" + username + " failed", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    /*
    Update database by ID
     */
    public boolean updata(int id, String username, String password) {
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
}
