package com.william.reservationsystem.MasterHomepage.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.william.reservationsystem.MasterHomepage.Fragment.Derive.FileUtils;
import com.william.reservationsystem.MasterHomepage.Fragment.Derive.Fileselect;
import com.william.reservationsystem.MasterHomepage.Fragment.Derive.UserInfo;
import com.william.reservationsystem.R;
import com.william.reservationsystem.SQLite.DBServerForU;

public class UserInfoFragment extends Fragment {

    ImageButton imgBtn_out;

    public static final int FILE_RESULT_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_userinfo, null);

        inti(view);

        imgBtn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Fileselect.class);
                startActivityForResult(intent, FILE_RESULT_CODE);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (FILE_RESULT_CODE == requestCode) {
            Bundle bundle = null;
            if (data != null && (bundle = data.getExtras()) != null){
                outStream(bundle.getString("file"));
            }
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
            FileUtils.writeTxtToFile(userInfo.toString(), filePath, "user.txt");
        }
    }
}
