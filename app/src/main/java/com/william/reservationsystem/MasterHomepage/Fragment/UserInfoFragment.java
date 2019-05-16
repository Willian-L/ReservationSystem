package com.william.reservationsystem.MasterHomepage.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.william.reservationsystem.MasterHomepage.Fragment.Derive.ChooseFileActivity;
import com.william.reservationsystem.MasterHomepage.Fragment.Derive.FileUtils;
import com.william.reservationsystem.MasterHomepage.Fragment.Derive.UserInfo;
import com.william.reservationsystem.R;
import com.william.reservationsystem.SQLite.DBServerForU;

import java.io.IOException;
import java.util.ArrayList;

public class UserInfoFragment extends Fragment {

    public static final int PATHREQUESTCODE = 44;

    private static final int MY_ADD_CASE_WRITE_READ = 6;

    ImageButton imgBtn_out;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfo, null);

        inti(view);

        imgBtn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChooseFileActivity.enterActivityForResult(getActivity(), PATHREQUESTCODE);
//                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//
//                    Log.d("www","have ");
//                } else {
//                    String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                    ActivityCompat.requestPermissions(getActivity(), perms, MY_ADD_CASE_WRITE_READ);
//                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("zww","onActivityResult ");
        if (requestCode == PATHREQUESTCODE && resultCode == ChooseFileActivity.RESULTCODE) {
            ArrayList<String> resPath = data.getStringArrayListExtra(ChooseFileActivity.SELECTPATH);
            Log.d("ZWW", resPath.toString());
        }
    }

    public void inti(View view) {
        imgBtn_out = view.findViewById(R.id.userIbtn_out);
    }

    public void outStream(String filePath) {
        DBServerForU dbServerForU = new DBServerForU(getContext());
        dbServerForU.open();
        Cursor cursor = dbServerForU.selectAll();
        while (cursor.moveToNext()) {
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
            Log.i("file", ""+filePath);
            FileUtils.writeTxtToFile(userInfo.toString(), filePath, "user.txt");
        }
    }
}
