package com.william.reservationsystem.SQLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/*
SQLiteOpenHelper is an abstract function.
Inherit and extend SQLiteOpenHelper to create the database and corresponding tables.
 */
public class DBHelper {

    Context context;

    public static final String TABLE_MASTER = "master";
    public static final String TABLE_USER = "user";

    /*
    Create a helper object to create, open, and/or manage a database.
     */
    public DBHelper(Context context) {
        this.context = context;
    }

    public static class SystemOpenHelper extends SQLiteOpenHelper {

        private Master master = new Master();

        /*
        Provides methods for creating a database
         */
        public SystemOpenHelper(Context context, String dbName, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, dbName, factory, version);
        }

        /*
        Called when the database is created for the first time.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            // A table of administrators
            String MASTER_SQL = "CREATE TABLE " + TABLE_MASTER + "(" +
                    "id INTEGER primary key AUTOINCREMENT, " +
                    "username varchar(20) not null, " +
                    "password varchar(20) not null" +
                    ");";

            // A table of user
            String USER_SQL = "CREATE TABLE " + TABLE_USER + "(" +
                    "id INTEGER primary key AUTOINCREMENT, " +
                    "username varchar(20) not null, " +
                    "password varchar(20) not null," +
                    "phone varchar(11) not null," +
                    "address TEXT" +
                    ");";
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

        /*
        Write the super administrator's data to the database as soon as the database is created
         */
        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
            if (db.query("master", new String[]{"username"},
                    "username='" + master.getSUPERUSERNAME() + "'",
                    null, null, null, null) == null) {
                db.execSQL("insert into master(username,password) Values(?,?)", new Object[]{master.getSUPERUSERNAME(), master.getSUPERPASSWORD()});
                Log.i("login","yes");
            }else {
                Log.i("login","no");
            }
            Log.i("login","open");
        }
    }
}
