package com.william.reservationsystem.controller.ResetPassword;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.william.reservationsystem.R;
import com.william.reservationsystem.model.DBServerForU;
import com.william.reservationsystem.model.User;
import com.william.reservationsystem.controller.LoginAndRegister.UserLoginActivity;

public class ResetPasswordActivity extends AppCompatActivity {

    private String username;

    TextView txtUser;
    Button btnReset;
    EditText editPsw;

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        getUser();

        init();
    }

    /**
     * Gets the username by Login page.
     */
    private void getUser(){
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
    }

    /**
     * The reset button
     */
    public void btnReset(View view) {
        user.setUsername(username);
        String password = editPsw.getText().toString().trim();
        if (determine(password)){
            user.setPassword(editPsw.getText().toString().trim());
            DBServerForU dbServerForU = new DBServerForU(getApplicationContext());
            dbServerForU.open();
            if (dbServerForU.ResetPsw(user.getUsername(), user.getPassword())){
                Toast.makeText(getApplicationContext(),"Reset successful!",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(ResetPasswordActivity.this, UserLoginActivity.class);
                intent1.putExtra("username",username);
                startActivity(intent1);
            }
        }
    }

    /**
     * Determine if the username, password, and phone number match the rules
     */
    private boolean determine(String password) {
        boolean result = false;
        if (!password.matches("^[A-Za-z0-9]+$")) {
            Toast.makeText(getApplicationContext(),
                    "Password must be alphanumeric!", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }

    private void init() {
        btnReset = findViewById(R.id.btnNewPSW);
        editPsw = findViewById(R.id.editNewPSW);
        txtUser = findViewById(R.id.txtUsername);
    }
}
