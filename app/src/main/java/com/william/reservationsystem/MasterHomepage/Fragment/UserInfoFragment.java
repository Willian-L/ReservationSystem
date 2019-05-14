package com.william.reservationsystem.MasterHomepage.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.william.reservationsystem.MasterHomepage.Fragment.Derive.FileUtils;
import com.william.reservationsystem.MasterHomepage.Fragment.Derive.UserInfo;
import com.william.reservationsystem.R;
import com.william.reservationsystem.SQLite.DBServerForU;

public class UserInfoFragment extends Fragment {

    Button imgBtn_out;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfo, null);

        inti(view);

        imgBtn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBServerForU dbServerForU = new DBServerForU(getContext());
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
                    UserInfo userInfo = gson.fromJson(jsonObject, UserInfo.class);
                    System.out.println(userInfo);
                    FileUtils.writeTxtToFile(userInfo.toString(), "/storage/emulated/0/","user.txt");
                }
            }
        });

        return view;
    }

    public void inti(View view){
        imgBtn_out  = view.findViewById(R.id.userIbtn_out);
    }
}
