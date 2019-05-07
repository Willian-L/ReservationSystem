package com.william.reservationsystem.Fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.icu.util.ValueIterator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.william.reservationsystem.R;
import com.william.reservationsystem.SQLite.DBServerForU;
import com.william.reservationsystem.SQLite.User;

public class MyFragment extends Fragment {

    ImageView imgPhoto;
    ImageButton ibtn_edit;
    EditText edtName, edtSex, edtAge, edtPhone, edtEmail, edtAddress;
    Button btnModify, btnCancel;
    User user = new User();

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_my,null);

        Bundle bundle = this.getArguments();

        if (bundle!=null){
            user.setUsername(bundle.getString("username"));
        }

        inti(view);

        DBServerForU dbServerForU = new DBServerForU(getContext());
        dbServerForU.open();
        Cursor cursor = dbServerForU.selectByUsername(user.getUsername());

        if (cursor.getCount()>0){
            while (cursor.moveToNext()){
                user.setName(cursor.getString(cursor.getColumnIndex("name")));
                edtName.setText(user.getName());
                user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
                edtSex.setText(user.getSex());
                user.setAge(cursor.getString(cursor.getColumnIndex("age")));
                edtAge.setText(user.getAge());
                user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                edtPhone.setText(user.getPhone());
                user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                edtEmail.setText(user.getEmail());
                user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                edtAddress.setText(user.getAddress());
            }
        }

        imgPhoto.setEnabled(false);

        ibtn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String backcolor = "#1fffffFF";
                edtName.setEnabled(true);
                edtSex.setEnabled(true);
                edtAge.setEnabled(true);
                edtPhone.setEnabled(true);
                edtEmail.setEnabled(true);
                edtAddress.setEnabled(true);
                imgPhoto.setEnabled(true);
                edtName.setBackgroundColor(Color.parseColor(backcolor));
                edtSex.setBackgroundColor(Color.parseColor(backcolor));
                edtAge.setBackgroundColor(Color.parseColor(backcolor));
                edtPhone.setBackgroundColor(Color.parseColor(backcolor));
                edtEmail.setBackgroundColor(Color.parseColor(backcolor));
                edtAddress.setBackgroundColor(Color.parseColor(backcolor));
                btnModify.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setName(edtName.getText().toString().trim());
                user.setSex(edtSex.getText().toString().trim());
                user.setAge(edtAge.getText().toString().trim());
                user.setPhone(edtPhone.getText().toString().trim());
                user.setEmail(edtEmail.getText().toString().trim());
                user.setAddress(edtAddress.getText().toString().trim());
                Log.i("edit", "user:"+user.getUsername()+
                        user.getName()+
                        user.getSex()+
                        user.getAge()+
                        user.getPhone()+
                        user.getEmail()+
                        user.getAddress());
                DBServerForU dbServerForU = new DBServerForU(getContext());
                dbServerForU.open();
                if (dbServerForU.updataUsername(
                        user.getUsername(),
                        user.getName(),
                        user.getSex(),
                        user.getAge(),
                        user.getPhone(),
                        user.getEmail(),
                        user.getAddress())){
                    Toast.makeText(getActivity(),"Modify successfully!",Toast.LENGTH_SHORT).show();
                }
                edtName.setEnabled(false);
                edtSex.setEnabled(false);
                edtAge.setEnabled(false);
                edtPhone.setEnabled(false);
                edtEmail.setEnabled(false);
                edtAddress.setEnabled(false);
                imgPhoto.setEnabled(false);
                edtName.setBackground(null);
                edtSex.setBackground(null);
                edtAge.setBackground(null);
                edtPhone.setBackground(null);
                edtEmail.setBackground(null);
                edtAddress.setBackground(null);
                btnModify.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtName.setText(user.getName());
                edtSex.setText(user.getSex());
                edtAge.setText(user.getAge());
                edtPhone.setText(user.getPhone());
                edtEmail.setText(user.getEmail());
                edtAddress.setText(user.getAddress());
                edtName.setEnabled(false);
                edtSex.setEnabled(false);
                edtAge.setEnabled(false);
                edtPhone.setEnabled(false);
                edtEmail.setEnabled(false);
                edtAddress.setEnabled(false);
                imgPhoto.setEnabled(false);
                edtName.setBackground(null);
                edtSex.setBackground(null);
                edtAge.setBackground(null);
                edtPhone.setBackground(null);
                edtEmail.setBackground(null);
                edtAddress.setBackground(null);
                btnModify.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
            }
        });

        imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"photo",Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void inti(View view){
        imgPhoto = view.findViewById(R.id.userImg_photo);
        ibtn_edit = view.findViewById(R.id.userIbtn_edit);
        edtName = view.findViewById(R.id.userMy_name);
        edtSex = view.findViewById(R.id.userMy_sex);
        edtAge = view.findViewById(R.id.userMy_age);
        edtPhone = view.findViewById(R.id.userMy_phone);
        edtEmail = view.findViewById(R.id.userMy_email);
        edtAddress = view.findViewById(R.id.userMy_address);
        btnModify = view.findViewById(R.id.my_btnModify);
        btnCancel = view.findViewById(R.id.my_btnCancel);
    }

}
