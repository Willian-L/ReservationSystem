package com.william.reservationsystem.controller.MasterHomepage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.william.reservationsystem.R;

public class ReleaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);
    }

    public void back_Edit(View view) {
        Intent intent = new Intent(this,HomepageForMasterActivity.class);
        startActivity(intent);
        finish();
    }
}
