package com.william.reservationsystem.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.william.reservationsystem.JsonActivity;
import com.william.reservationsystem.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toLogin(View view) {
        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);
    }

    public void toGson(View view) {
        Intent intent = new Intent(this, JsonActivity.class);
        startActivity(intent);
    }
}
