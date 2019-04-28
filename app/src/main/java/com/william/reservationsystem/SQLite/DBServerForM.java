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
        DBHelper dbHelper = new DBHelper(context);
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
    public void close(){
        if (db != null){
            db.close();
            db = null;
        }
    }

    /*
    Register and add data to the database
     */
    public boolean insert(String username, String password) {
        boolean result = false;
        // Instantiate constant values
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_PASSWORD, password);
        if (db.insert(DB_TABLE, null, contentValues) > 0) {
            result = true;
            Toast.makeText(context, "Insert succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Insert failed", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    /*
    Account login judgment
     */
    public boolean login(String username, String password) {
        boolean result = false;

        // Get a cursor object
        Cursor cursor = db.query(DB_TABLE, new String[]{KEY_USERNAME, KEY_PASSWORD},
                KEY_USERNAME + "='" + username + "' and " +
                        KEY_PASSWORD + "='" + password + "'",
                null, null, null, null);
        // Return true if there is any content in the cursor.
        if (cursor.getCount() == 1) {
            result = true;
            Toast.makeText(context, "Login succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, "Delete succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(context, "Delete succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Delete failed", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    /*
    Update database
     */
    public boolean updata(int id, String username, String password) {
        boolean result = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, id);
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_PASSWORD, password);
        int n = db.update(DB_TABLE, contentValues, KEY_ID + "=" + String.valueOf(id), null);
        if (n == 1) {
            result = true;
            Log.i("sql", "Update database succeeded");
        } else {
            Log.i("sql", "Update database failed");
        }
        return result;
    }
}
