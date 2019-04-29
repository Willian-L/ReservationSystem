package com.william.reservationsystem;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.william.reservationsystem.SQLite.DBHelper;
import com.william.reservationsystem.SQLite.DBServerForM;
import com.william.reservationsystem.SQLite.DBServerForU;
import com.william.reservationsystem.SQLite.Master;
import com.william.reservationsystem.SQLite.User;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    RadioGroup ragType;
    Button btnConfirm;

    // Define an identity to get the selected value of RdaioGroup
    public String type = "User";

    User user = new User();
    Master master = new Master();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ui();

        // Listen for the selected value of RdaioGroup
        ragType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(ragType.getCheckedRadioButtonId());
                type = radioButton.getText().toString();
                Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // If the User is selected, the user data table is invoked
                if ("User".equals(type)) {
                    // Get the username and password
                    user.setUsername(edtUsername.getText().toString().trim());
                    user.setPassword(edtPassword.getText().toString().trim());
                    DBServerForU dbServerForU = new DBServerForU(getApplicationContext());
                    dbServerForU.open();
                    /*
                    Determine whether the username and password match the one in the database
                     */
                    if (dbServerForU.selectByUsername(user.getUsername()).getCount() == 0) {
                        Toast.makeText(getApplicationContext(), "This username does not exist!", Toast.LENGTH_SHORT).show();
                    } else if (dbServerForU.login(user.getUsername(), user.getPassword())) {
                        Intent intent = new Intent(LoginActivity.this, HomepageForUActivity.class);
                        startActivity(intent);
                        dbServerForU.close();
                    } else {
                        Toast.makeText(getApplicationContext(), "Password is wrong!", Toast.LENGTH_SHORT).show();
                    }
                    dbServerForU.close();
                    // If the Master is selected, the master data table is invoked
                } else if ("Master".equals(type)) {
                    master.setUsername(edtUsername.getText().toString().trim());
                    master.setPassword(edtPassword.getText().toString().trim());
                    DBServerForM dbServerForM = new DBServerForM(getApplicationContext());
                    dbServerForM.open();
                    if (dbServerForM.selectByUsername(master.getUsername()).getCount() == 0) {
                        Toast.makeText(getApplicationContext(), "This username does not exist!", Toast.LENGTH_SHORT).show();
                    } else if (dbServerForM.login(master.getUsername(), master.getPassword())) {
                        Intent intent = new Intent(LoginActivity.this, HomepageForMActivity.class);
                        startActivity(intent);
                        dbServerForM.close();
                    } else {
                        Toast.makeText(getApplicationContext(), "Password is wrong!", Toast.LENGTH_SHORT).show();
                    }
                    dbServerForM.close();
                }
            }
        });
    }

    public void register(View view) {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    public void forgot(View view) {
    }

    /*
    Access controls
     */
    private void ui(){
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        ragType = findViewById(R.id.ragType);
        btnConfirm = findViewById(R.id.btnConfirm);
    }
}
