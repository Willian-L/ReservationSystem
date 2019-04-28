package com.william.reservationsystem.SQLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
SQLiteOpenHelper is an abstract function.
Inherit and extend SQLiteOpenHelper to create the database and corresponding tables.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "ReservationSystem.db";

    public static final String TABLE_MASTER = "master";

    public static final String TABLE_USER = "user";

    // A table of administrators
    private static final String MASTER_SQL = "CREATE TABLE" + TABLE_MASTER + "(" +
            "id INTEGER primary key AUTOINCREMENT, " +
            "username varchar(20) not null, " +
            "password varchar(20) not null" +
            ");";

    // A table of user
    private static final String USER_SQL = "CREATE TABLE" + TABLE_USER + "(" +
            "id INTEGER auto_increment primary key AUTOINCREMENT, " +
            "username varchar(20) not null, " +
            "password varchar(20) not null," +
            "phone INTEGER not null," +
            "address TEXT" +
            ");";

    /*
    Create a helper object to create, open, and/or manage a database.
     */
    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    /*
    Called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Insert table
        db.execSQL(MASTER_SQL);
        Log.i("sql", "create table master:" + MASTER_SQL);
        db.execSQL(USER_SQL);
        Log.i("sql", "create table user:" + USER_SQL);
    }

    /*
    Called when the database needs to be upgraded.
    The implementation should use this method to drop tables, add tables, or do anything else it needs to upgrade to the new schema version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
