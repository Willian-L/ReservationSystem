package com.william.reservationsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.william.reservationsystem.ResetPassword.ResetVerActivity;
import com.william.reservationsystem.SQLite.DBServerForM;
import com.william.reservationsystem.SQLite.DBServerForU;
import com.william.reservationsystem.SQLite.Master;
import com.william.reservationsystem.SQLite.User;

public class UserLoginActivity extends AppCompatActivity {

//    private String username, password;

    EditText edtUsername, edtPassword;
    RadioGroup ragType;
    Button btnLogin;

    // Define an identity to get the selected value of RdaioGroup
    public String type = "User";

    User user = new User();
    Master master = new Master();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        init();

        edtPassword.setText(null);
        edtUsername.setText(null);



        Log.i("edt","edtPassword:" + edtPassword.getText() + "edtUsername:" + edtUsername.getText());

//        try {
//            Intent intent = getIntent();
//            String username = intent.getStringExtra("username");
//            edtUsername.setText(username);
//        } catch (Exception e) {
//        }

        // Listen for the selected value of RdaioGroup
        ragType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(ragType.getCheckedRadioButtonId());
                type = radioButton.getText().toString();
                Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = 0;

                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (username != null && password != null) {
                    switch (type) {
                        case "User":
                            // Get the username and password
                            user.setUsername(edtUsername.getText().toString().trim());
                            user.setPassword(edtPassword.getText().toString().trim());
                            DBServerForU dbServerForU = new DBServerForU(getApplicationContext());
                            dbServerForU.open();
                            Log.i("eab",edtUsername.getText().toString() + edtPassword.getText().toString());
                        /*
                        Determine whether the username and password match the one in the database
                         */
                            result = dbServerForU.login(user.getUsername(), user.getPassword());
                            switch (result) {
                                case 2:
                                    Intent intent = new Intent(UserLoginActivity.this, HomepageForUActivity.class);
                                    startActivity(intent);
                                    dbServerForU.close();
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
                            break;
                        case "Master":
                            master.setUsername(edtUsername.getText().toString().trim());
                            master.setPassword(edtPassword.getText().toString().trim());
                            DBServerForM dbServerForM = new DBServerForM(getApplicationContext());
                            dbServerForM.open();
                            result = dbServerForM.login(master.getUsername(), master.getPassword());
                            switch (result) {
                                case 2:
                                    Intent intent = new Intent(UserLoginActivity.this, HomepageForMActivity.class);
                                    startActivity(intent);
                                    dbServerForM.close();
                                    break;
                                case 1:
                                    Toast.makeText(getApplicationContext(),
                                            "Password is wrong!\nPlease contact the super administrator." + result, Toast.LENGTH_SHORT).show();
                                    break;
                                case 0:
                                    Toast.makeText(getApplicationContext(),
                                            "This username does not exist!\nPlease contact the super administrator.", Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    break;
                            }
                            dbServerForM.close();
                            break;
                        default:
                            break;
                    }
                }
                else if (username != null && password == null) {
                    Toast.makeText(getApplicationContext(),
                            "Please input your password!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please input your username and password!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

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

    /*
    Access controls
     */
    private void init() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        ragType = findViewById(R.id.ragType);
        btnLogin = findViewById(R.id.btnLogin);
    }
}
