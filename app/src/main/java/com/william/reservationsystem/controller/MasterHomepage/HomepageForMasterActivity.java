package com.william.reservationsystem.controller.MasterHomepage;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.william.reservationsystem.R;

public class HomepageForMasterActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private LinearLayout it_daily, it_my;
    private ImageView imgOpen;
    private ImageButton imgBtnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_for_master);

        init();
        imgOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void init(){
        mDrawerLayout = findViewById(R.id.draLay_master);
        mToolbar = findViewById(R.id.toolbar_master);
        it_daily = findViewById(R.id.item_daily);
        it_my = findViewById(R.id.item_my);
        imgOpen = findViewById(R.id.imgBtn_open);
        imgBtnAdd = findViewById(R.id.imgBtn_add);
    }
}
