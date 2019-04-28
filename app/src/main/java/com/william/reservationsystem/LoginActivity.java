package com.william.reservationsystem;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.william.reservationsystem.SQLite.DBHelper;
import com.william.reservationsystem.SQLite.DBServerForM;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    RadioGroup ragType;
    Button btnConfirm, btnRegister;

    public String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        ragType = findViewById(R.id.ragType);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnRegister = findViewById(R.id.btnRegister);

        ragType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(ragType.getCheckedRadioButtonId());
                type = radioButton.getText().toString();
                Toast.makeText(getApplicationContext(),type,Toast.LENGTH_SHORT).show();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the username and password
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
            }
        });
    }

    /*
    The reset function
     */
    public void reset(View view) {
        edtUsername.setText(null);
        edtPassword.setText(null);
    }
}
