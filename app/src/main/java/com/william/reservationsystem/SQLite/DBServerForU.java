package com.william.reservationsystem.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class DBServerForU {

    private static final String DB_TABLE = "user";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ADDRESS = "address";

    SQLiteDatabase db;
    Context context;

    public DBServerForU(Context context) {
        this.context = context;
    }

    /*
    Open database
     */
    public void open() {
        DBHelper.SystemOpenHelper dbHelper = new DBHelper.SystemOpenHelper(context, "ReservationSystem.db", null, 1);
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
    public boolean insert(String username, String password, String phone, String address) {
        boolean result = false;
        // Instantiate content values
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_PASSWORD, password);
        contentValues.put(KEY_PHONE, phone);
        contentValues.put(KEY_ADDRESS, address);
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
    public int login(String username, String password) {
        int result = 0;
        Cursor cursor = null;
        // Get a cursor object
        cursor = db.query("user", new String[]{"password"},
                "username='" + username + "'",
                null, null, null, null);
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
    Query all user data
     */
    public Cursor selectAll() {
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{KEY_ID, KEY_USERNAME, KEY_PHONE, KEY_ADDRESS},
                null, null, null, null, null);
        return cursor;
    }

    /*
    Query user data by username
     */
    public Cursor selectByUsername(String username) {
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{KEY_ID, KEY_USERNAME, KEY_PHONE, KEY_ADDRESS},
                KEY_USERNAME + "='" + username + "'",
                null, null, null, null);
        return cursor;
    }

    /*
    Query phone by username
     */
    public Cursor selectPhone(String username){
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{KEY_PHONE},
                KEY_USERNAME + "'" + username + "'",
                null, null, null ,null);
        return cursor;
    }

    /*
    Delete user data by id
     */
    public boolean deleteByID(int id) {
        boolean result = false;
        int i = db.delete(DB_TABLE, KEY_ID + "='" + id + "'", null);
        if (i > 0) {
            result = true;
            Toast.makeText(context, "Delete id:" + id + " succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Delete id:" + id + " failed", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    /*
    Delete user data by id
     */
    public boolean deleteByUsername(String username) {
        boolean result = false;
        int i = db.delete(DB_TABLE, KEY_USERNAME + "='" + username + "'", null);
        if (i > 0) {
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
    public boolean updata(int id, String username, String password, String phone, String address) {
        boolean result = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, id);
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_PASSWORD, password);
        contentValues.put(KEY_PHONE, phone);
        contentValues.put(KEY_ADDRESS, address);
        int n = db.update(DB_TABLE, contentValues, KEY_ID + "=" + id, null);
        if (n == 1) {
            result = true;
            Toast.makeText(context, "Update database succeeded", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Update database failed", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    /*
    ResetPassword
     */
    public boolean ResetPsw(String username, String password) {
        boolean result = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_PASSWORD, password);
        int n = db.update(DB_TABLE, contentValues, KEY_USERNAME + "=" + username, null);
        if (n == 1) {
            result = true;
        }
        return result;
    }

    /*
    Reset password authentication
     */
    public int resetVerify(String username, String phone) {
        int result = 0;
        Cursor cursor = null;
        // Get a cursor object
        cursor = db.query("user", new String[]{"phone"},
                "username='" + username + "'",
                null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String thephone = cursor.getString(cursor.getColumnIndex("phone"));
                if (thephone.equals(phone)) {
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
}
