package com.william.reservationsystem.controller;

import android.content.Context;
import android.content.Intent;
import android.widget.EditText;

import com.william.reservationsystem.application.AppContext;
import com.william.reservationsystem.controller.LoginAndRegister.RegisterActivity;
import com.william.reservationsystem.controller.LoginAndRegister.UserLoginActivity;
import com.william.reservationsystem.controller.ResetPassword.ResetVerActivity;
import com.william.reservationsystem.model.User;


public class LoginUtil extends AppContext {

    User user = new User();

//    public void UserLogin(EditText edtUsername,EditText edtPassword, CheckBox checkRemember){
//        int getResult = 0;
//        // Get the username and password
//        user.setUsername(edtUsername.getText().toString().trim());
//        user.setPassword(edtPassword.getText().toString().trim());
//        DBServerForU dbServerForU = new DBServerForU(getApplicationContext());
//        dbServerForU.open();
//        Log.i("eab", edtUsername.getText().toString() + edtPassword.getText().toString());
//                        /*
//                        Determine whether the username and password match the one in the database
//                         */
//        getResult = dbServerForU.login(user.getUsername(), user.getPassword());
//        switch (getResult) {
//            case 2:
//                if (checkRemember.isChecked()) {
//                    saveToPre(getBaseContext(), user.getUsername(), user.getPassword());
//                } else {
//                    deleteToPre(getBaseContext());
//                }
//                Intent intent = new Intent(getApplicationContext(), HomepageForUActivity.class);
//                intent.putExtra("username", user.getUsername());
//                startActivity(intent);
//                dbServerForU.close();
//                finish();
//                break;
//            case 1:
//                showResetDialog();
//                break;
//            case 0:
//                showRegisterDialog();
//                break;
//            default:
//                break;
//        }
//        dbServerForU.close();
//    }

    public void toRegister(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        startActivity(intent);
    }
}
