package com.william.reservationsystem;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/*
SQLiteOpenHelper is an abstract function.
Inherit and extend SQLiteOpenHelper to create the database and corresponding tables.
 */
public class DBHelper extends SQLiteOpenHelper {

    /*
    Create a helper object to create, open, and/or manage a database.
     */
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "user.db", null, 1);
    }

    /*
    Called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // A table of administrators
        String adminSQL = "CREATE TABLE master(" +
                "id INTEGER primary key, " +
                "username varchar(20) not null, " +
                "password varchar(20) not null)";

        // A table of user
        String userSQL = "CREATE TABLE user(" +
                "id int auto_increment primary key, " +
                "username varchar(20) not null, "+
                "password varchar(20) not null)";

        // Insert table
        db.execSQL(adminSQL);
        Log.i("sql", "create table master:" + adminSQL);
        db.execSQL(userSQL);
        Log.i("sql","create table user:" + userSQL);
    }

    /*
    Called when the database needs to be upgraded.
    The implementation should use this method to drop tables, add tables, or do anything else it needs to upgrade to the new schema version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
