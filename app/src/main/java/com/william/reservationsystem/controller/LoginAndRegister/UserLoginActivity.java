package com.william.reservationsystem.controller.LoginAndRegister;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.william.reservationsystem.controller.MasterHomepage.HomepageForMActivity;
import com.william.reservationsystem.R;
import com.william.reservationsystem.controller.ResetPassword.ResetVerActivity;
import com.william.reservationsystem.model.DBServerForM;
import com.william.reservationsystem.model.DBServerForU;
import com.william.reservationsystem.model.Master;
import com.william.reservationsystem.model.SharedPreferencesUtils;
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

    /**
     * Gets the username that the user just registered
     */
    private void getUser(EditText edtUsername){
        try {
            Intent intent = getIntent();
            String username = intent.getStringExtra("username");
            edtUsername.setText(username);
        } catch (Exception e) {
        }
    }

    /**
     * Automatic login function
     */
    private void checkAutoLogin(CheckBox checkRemember, EditText edtUsername, EditText edtPassword){
        String username = SharedPreferencesUtils.getInstance().getString(SharedPreferencesUtils.USER_NAME,"");
        String password = SharedPreferencesUtils.getInstance().getString(SharedPreferencesUtils.PASSWORD,"");
        edtUsername.setText(username);
        edtPassword.setText(password);

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

    /**
     * The login button
     */
    public void btnlogin(View view) {
        username = edtUsername.getText().toString().trim();
        password = edtPassword.getText().toString().trim();

        if (!username.equals("") && !password.equals("")) {
            if (MasterLogin()) {
                Intent intent = new Intent(UserLoginActivity.this, HomepageForMActivity.class);
                startActivity(intent);
                finish();
            } else {
                UserLogin();
                finish();
            }
        } else if (!username.equals("") && password.equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Please input your password!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please input your username and password!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * User login method
     */
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
                    SharedPreferencesUtils.getInstance().putString(SharedPreferencesUtils.USER_NAME,user.getUsername());
                    SharedPreferencesUtils.getInstance().putString(SharedPreferencesUtils.PASSWORD,user.getPassword());
                } else {
                    SharedPreferencesUtils.getInstance().clear();
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

    /**
     * Master login method
     */
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

    /**
     * Dialog
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

    /**
     * Intention of events
     */
    public void register(View view) {
        toRegister();
    }

    public void forgot(View view) {
        toForgot();
    }

    private void toRegister() {
        Intent intent = new Intent(UserLoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void toForgot() {
        Intent intent = new Intent(UserLoginActivity.this, ResetVerActivity.class);
        if (edtUsername != null) {
            intent.putExtra("username", edtUsername.getText().toString().trim());
        }
        startActivity(intent);
    }

    /**
     * Find Controls
     */
    private void init() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        checkRemember = findViewById(R.id.loginCb_Remember);
    }
}
