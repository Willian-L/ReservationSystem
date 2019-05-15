package com.william.reservationsystem.UserHomepage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.william.reservationsystem.UserHomepage.Fragment.MyFragment;
import com.william.reservationsystem.UserHomepage.Fragment.OrderFragment;
import com.william.reservationsystem.UserHomepage.Fragment.ShoppingFragment;
import com.william.reservationsystem.R;
import com.william.reservationsystem.Information.User;

public class HomepageForUActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private RadioButton rb_order, rb_shopping, rb_my;

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_for_u);

        inti();

        rb_order.setActivated(true);

        Intent intent = getIntent();
        user.setUsername(intent.getStringExtra("username"));

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.user_content_Layout, new OrderFragment());
        transaction.commit();
    }

    public void inti() {
        rb_order = findViewById(R.id.rad_order);
        rb_shopping = findViewById(R.id.rad_shopping);
        rb_my = findViewById(R.id.rad_my);

        rb_order.setOnClickListener(this);
        rb_shopping.setOnClickListener(this);
        rb_my.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        transaction = manager.beginTransaction();
        inti();
        switch (v.getId()) {
            case R.id.rad_order:
                transaction.replace(R.id.user_content_Layout, new OrderFragment());
                rb_order.setActivated(true);
                rb_shopping.setActivated(false);
                rb_my.setActivated(false);
                break;
            case R.id.rad_shopping:
                transaction.replace(R.id.user_content_Layout, new ShoppingFragment());
                rb_shopping.setActivated(true);
                rb_order.setActivated(false);
                rb_my.setActivated(false);
                break;
            case R.id.rad_my:
                MyFragment myFragment = new MyFragment();
                Bundle bundle = new Bundle();
                bundle.putString("username", user.getUsername());
                myFragment.setArguments(bundle);
                transaction.replace(R.id.user_content_Layout, myFragment);
                rb_my.setActivated(true);
                rb_shopping.setActivated(false);
                rb_order.setActivated(false);
                break;
        }
        transaction.commit();
    }


}

