package com.william.reservationsystem;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.william.reservationsystem.SQLite.DBServerForM;
import com.william.reservationsystem.SQLite.DBServerForU;
import com.william.reservationsystem.SQLite.User;

public class JsonActivity extends AppCompatActivity {

    Button btn_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);

        btn_json=findViewById(R.id.btn_json);

        btn_json.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBServerForU dbServerForU = new DBServerForU(getApplicationContext());
                dbServerForU.open();
                Cursor cursor = dbServerForU.selectAll();
                while (cursor.moveToNext()){
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("id", cursor.getInt(cursor.getColumnIndex("id")));
                    jsonObject.addProperty("username", cursor.getString(cursor.getColumnIndex("username")));
                    jsonObject.addProperty("password", cursor.getString(cursor.getColumnIndex("password")));
                    jsonObject.addProperty("phone", cursor.getString(cursor.getColumnIndex("phone")));
                    jsonObject.addProperty("name", cursor.getString(cursor.getColumnIndex("name")));
                    jsonObject.addProperty("sex", cursor.getString(cursor.getColumnIndex("sex")));
                    jsonObject.addProperty("age", cursor.getString(cursor.getColumnIndex("age")));
                    jsonObject.addProperty("email", cursor.getString(cursor.getColumnIndex("email")));
                    System.out.println(jsonObject);
                    Gson gson = new Gson();
                    Format format = gson.fromJson(jsonObject, Format.class);
                    System.out.println(format);
                    FileUtils.writeTxtToFile(format.toString(), "/storage/emulated/0/","user.txt");
                }
            }
        });


    }
}
