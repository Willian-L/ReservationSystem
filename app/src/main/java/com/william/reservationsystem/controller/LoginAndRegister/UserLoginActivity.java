package com.william.reservationsystem.controller.LoginAndRegister;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.william.reservationsystem.controller.LoginUtil;
import com.william.reservationsystem.controller.MasterHomepage.HomepageForMActivity;
import com.william.reservationsystem.R;
import com.william.reservationsystem.controller.ResetPassword.ResetVerActivity;
import com.william.reservationsystem.model.DBServerForM;
import com.william.reservationsystem.model.DBServerForU;
import com.william.reservationsystem.model.Master;
import com.william.reservationsystem.model.User;
import com.william.reservationsystem.controller.UserHomepage.HomepageForUActivity;

public class UserLoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin;
    CheckBox checkRemember;

    private String username;
    private String password;

    User user = new User();
    Master master = new Master();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        init();

        checkAutoLogin(checkRemember, edtUsername, edtPassword);

        getUser(edtUsername);
    }

    /*
    Gets the username that the user just registered
     */
    private void getUser(EditText edtUsername){
        try {
            Intent intent = getIntent();
            String username = intent.getStringExtra("username");
            edtUsername.setText(username);
        } catch (Exception e) {
        }
    }

    /*
    Automatic login function
     */
    private void checkAutoLogin(CheckBox checkRemember, EditText edtUsername, EditText edtPassword){
        readFromPre(this, edtUsername, edtPassword);

        if (TextUtils.isEmpty(edtUsername.getText().toString())) {
            checkRemember.setChecked(false);
            edtPassword.setText(null);
        } else {
            checkRemember.setChecked(true);
            Intent intent = new Intent(UserLoginActivity.this, HomepageForUActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public static void readFromPre(Context context, EditText tusername, EditText tpassword) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");
        tusername.setText(username);
        tpassword.setText(password);
    }

    public static void saveToPre(Context context, String username, String password) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

    public static void deleteToPre(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userInfo", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    /*
    The login operation
     */
    public void btnlogin(View view) {
        username = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        boolean hasUsernameValue = false;
        if (!username.equals("")) {
            hasUsernameValue = true;
        }

        boolean hasPasswordValue = false;
        if (!password.equals("")) {
            hasPasswordValue = true;
        }

        if (hasUsernameValue && hasPasswordValue) {
            if (MasterLogin()) {
                Intent intent = new Intent(UserLoginActivity.this, HomepageForMActivity.class);
                startActivity(intent);
                finish();
            } else {
                UserLogin();
                finish();
            }
        } else if (hasUsernameValue && !hasPasswordValue) {
            Toast.makeText(getApplicationContext(),
                    "Please input your password!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please input your username and password!", Toast.LENGTH_SHORT).show();
        }
    }

    private void UserLogin() {
        int getResult = 0;
        // Get the username and password
        user.setUsername(edtUsername.getText().toString().trim());
        user.setPassword(edtPassword.getText().toString().trim());
        DBServerForU dbServerForU = new DBServerForU(getApplicationContext());
        dbServerForU.open();
        Log.i("eab", edtUsername.getText().toString() + edtPassword.getText().toString());
                        /*
                        Determine whether the username and password match the one in the database
                         */
        getResult = dbServerForU.login(user.getUsername(), user.getPassword());
        switch (getResult) {
            case 2:
                if (checkRemember.isChecked()) {
                    saveToPre(getBaseContext(), user.getUsername(), user.getPassword());
                } else {
                    deleteToPre(getBaseContext());
                }
                Intent intent = new Intent(UserLoginActivity.this, HomepageForUActivity.class);
                intent.putExtra("username", user.getUsername());
                startActivity(intent);
                dbServerForU.close();
                finish();
                break;
            case 1:
                showResetDialog();
                break;
            case 0:
                showRegisterDialog();
                break;
            default:
                break;
        }
        dbServerForU.close();
    }

    private boolean MasterLogin() {
        boolean result = false;
        master.setUsername(username);
        master.setPassword(password);
        DBServerForM dbServerForM = new DBServerForM(getApplicationContext());
        dbServerForM.open();
        if (dbServerForM.login(master.getUsername(), master.getPassword())) {
            dbServerForM.close();
            result = true;
        }
        dbServerForM.close();
        return result;
    }

    /*
    Dialog
     */
    private void showRegisterDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserLoginActivity.this);
        alertDialog.setTitle("This username does not exist!");
        alertDialog.setMessage("Do you need to register a new account?");
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toRegister();
                    }
                });
        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog.show();
    }

    private void showResetDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserLoginActivity.this);
        alertDialog.setTitle("Password is wrong!");
        alertDialog.setMessage("Do you need to reset your password?");
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toForgot();
                    }
                });
        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alertDialog.show();
    }

    /*
    Intention of events
     */
    public void register(View view) {
        toRegister();
    }

    public void forgot(View view) {
        toForgot();
    }

    private void toRegister() {
        LoginUtil loginUtil = new LoginUtil();
        loginUtil.toRegister();
//        Intent intent = new Intent(UserLoginActivity.this, RegisterActivity.class);
//        startActivity(intent);
    }

    private void toForgot() {
        Intent intent = new Intent(UserLoginActivity.this, ResetVerActivity.class);
        if (edtUsername != null) {
            intent.putExtra("username", edtUsername.getText().toString().trim());
        }
        startActivity(intent);
    }

    /*
    Find Controls
     */
    private void init() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        checkRemember = findViewById(R.id.loginCb_Remember);
    }
}
