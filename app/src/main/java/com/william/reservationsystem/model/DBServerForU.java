package com.william.reservationsystem.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.william.reservationsystem.model.DBHelper;

public class DBServerForU {

    private static final String DB_TABLE = "user";
    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_NAME = "name";
    private static final String KEY_SEX = "sex";
    private static final String KEY_AGE = "age";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHOTO = "photo";

    SQLiteDatabase db;
    Context context;

    public DBServerForU(Context context) {
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
    public boolean insert(String username, String name , String password, String phone) {
        boolean result = false;
        // Instantiate content values
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_PASSWORD, password);
        contentValues.put(KEY_PHONE, phone);
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
                String thePassword = cursor.getString(cursor.getColumnIndex("password"));
                if (thePassword.equals(password)) {
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

    public boolean qrlogin(String username, String phone) {
        boolean result = false;
        Cursor cursor = null;
        cursor = db.query("user", new String[]{"phone"},
                "username='" + username + "'",
                null,null,null,null);
        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                String thePhone = cursor.getString(cursor.getColumnIndex("phone"));
                Log.i("qrDB",thePhone);
                if (thePhone.equals(phone)){
                    result = true;
                }
            }
        }
        return result;
    }

    /*
    Query all user data
     */
    public Cursor selectAll() {
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{KEY_ID, KEY_USERNAME, KEY_PASSWORD, KEY_NAME, KEY_SEX, KEY_AGE, KEY_PHONE, KEY_EMAIL, KEY_ADDRESS},
                null, null, null, null, null);
        return cursor;
    }

    /*
    Query user data by username
     */
    public Cursor selectByUsername(String username) {
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{KEY_ID, KEY_USERNAME, KEY_PHONE, KEY_NAME, KEY_SEX, KEY_AGE, KEY_EMAIL, KEY_ADDRESS, KEY_PHOTO},
                KEY_USERNAME + "='" + username + "'",
                null, null, null, null);
        return cursor;
    }

    /**
     * Query phone by username
     */
    public Cursor selectID(String username) {
        Cursor cursor = null;
        cursor = db.query(DB_TABLE, new String[]{KEY_ID},
                KEY_USERNAME + "='" + username + "'",
                null, null, null, null);
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
        int n = db.update(DB_TABLE, contentValues, KEY_USERNAME + "='" + username + "'", null);
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

    public boolean updataUsername(String username, String name, String sex, String age, String phone, String email, String address, String photo) {
        boolean result = false;
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USERNAME, username);
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_SEX, sex);
        contentValues.put(KEY_AGE, age);
        contentValues.put(KEY_PHONE, phone);
        contentValues.put(KEY_EMAIL, email);
        contentValues.put(KEY_ADDRESS, address);
        contentValues.put(KEY_PHOTO, photo);
        int n = db.update(DB_TABLE, contentValues, KEY_USERNAME + "='" + username + "'", null);
        if (n == 1) {
            result = true;
            Log.i("sql", "Update database succeeded");
        } else {
            Log.i("sql", "Update database failed");
        }
        return result;
    }
}
