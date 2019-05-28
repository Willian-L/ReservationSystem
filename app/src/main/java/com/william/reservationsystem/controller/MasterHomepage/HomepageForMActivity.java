package com.william.reservationsystem.controller.MasterHomepage;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.william.reservationsystem.controller.MasterHomepage.Fragment.UserInfoFragment;
import com.william.reservationsystem.R;
import com.william.reservationsystem.controller.UserHomepage.Fragment.OrderFragment;

public class HomepageForMActivity extends AppCompatActivity implements View.OnClickListener  {

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private RadioButton rb_orderInfo, rb_userInfo, rb_my;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_for_m);

        inti();

        embed();
    }


    /**
     * Embed Fragment
     */
    private void embed(){
        rb_orderInfo.setActivated(true);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.master_content_Layout, new OrderFragment());
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        transaction = manager.beginTransaction();
        switch (v.getId()) {
            case R.id.masterRad_orderInfo:
                rb_orderInfo.setActivated(true);
                rb_userInfo.setActivated(false);
                rb_my.setActivated(false);
                break;
            case R.id.masterRad_userInfo:
                transaction.replace(R.id.master_content_Layout, new UserInfoFragment());
                rb_orderInfo.setActivated(false);
                rb_userInfo.setActivated(true);
                rb_my.setActivated(false);
                break;
            case R.id.masterRad_my:
                rb_my.setActivated(true);
                rb_orderInfo.setActivated(false);
                rb_userInfo.setActivated(false);
                break;
        }
        transaction.commit();
    }

    private void inti() {
        rb_orderInfo = findViewById(R.id.masterRad_orderInfo);
        rb_userInfo = findViewById(R.id.masterRad_userInfo);
        rb_my = findViewById(R.id.masterRad_my);

        rb_orderInfo.setOnClickListener(this);
        rb_userInfo.setOnClickListener(this);
        rb_my.setOnClickListener(this);
    }

}
