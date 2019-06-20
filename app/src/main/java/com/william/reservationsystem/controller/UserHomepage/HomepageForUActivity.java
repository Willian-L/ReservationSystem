package com.william.reservationsystem.controller.UserHomepage;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.william.reservationsystem.controller.UserHomepage.Fragment.MyFragment;
import com.william.reservationsystem.controller.UserHomepage.Fragment.OrderFragment;
import com.william.reservationsystem.controller.UserHomepage.Fragment.ShoppingFragment;
import com.william.reservationsystem.R;
import com.william.reservationsystem.model.User;

public class HomepageForUActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private RadioButton rb_order, rb_shopping, rb_my;

    User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_for_u);

        init();

        RadioButton[] rbs = new RadioButton[3];
        rbs[0] = rb_order;
        rbs[1] = rb_shopping;
        rbs[2] = rb_my;
        for (RadioButton rb : rbs){
            Drawable[] drawables = rb.getCompoundDrawables();

            Rect r = new Rect(0,0,drawables[1].getMinimumWidth()*3/8,drawables[1].getMinimumHeight()*3/8);
            drawables[1].setBounds(r);

            rb.setCompoundDrawables(null,drawables[1],null,null);
        }

        getUser();

        embed();
    }

    /**
     * Gets the username by Login page.
     */
    private void getUser(){
        Intent intent = getIntent();
        user.setUsername(intent.getStringExtra("username"));
    }

    /**
     * Embed Fragment
     */
    private void embed(){
        rb_order.setActivated(true);
        rb_order.setTextColor(Color.parseColor("#e9730a"));
        Bundle bundle = new Bundle();
        OrderFragment orderFragment = new OrderFragment();
        bundle.putString("username", user.getUsername());
        orderFragment.setArguments(bundle);
        TextPaint order = rb_order.getPaint();
        order.setFakeBoldText(true);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.user_content_Layout, orderFragment);
        transaction.commit();
    }

    /**
     * Find Controls
     */
    private void init() {
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
        init();
        TextPaint order = rb_order.getPaint();
        TextPaint shopping = rb_shopping.getPaint();
        TextPaint my = rb_my.getPaint();
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.rad_order:
                OrderFragment orderFragment = new OrderFragment();
                bundle.putString("username", user.getUsername());
                orderFragment.setArguments(bundle);
                transaction.replace(R.id.user_content_Layout, orderFragment);
                rb_order.setActivated(true);
                rb_shopping.setActivated(false);
                rb_my.setActivated(false);
                rb_order.setTextColor(Color.parseColor("#e9730a"));
                rb_shopping.setTextColor(Color.parseColor("#404040"));
                rb_my.setTextColor(Color.parseColor("#404040"));
                order.setFakeBoldText(true);
                shopping.setFakeBoldText(false);
                my.setFakeBoldText(false);
                break;
            case R.id.rad_shopping:
                ShoppingFragment shoppingFragment = new ShoppingFragment();
                bundle.putString("username", user.getUsername());
                shoppingFragment.setArguments(bundle);
                transaction.replace(R.id.user_content_Layout, shoppingFragment);
                rb_shopping.setActivated(true);
                rb_order.setActivated(false);
                rb_my.setActivated(false);
                rb_shopping.setTextColor(Color.parseColor("#e9730a"));
                rb_order.setTextColor(Color.parseColor("#404040"));
                rb_my.setTextColor(Color.parseColor("#404040"));
                shopping.setFakeBoldText(true);
                order.setFakeBoldText(false);
                my.setFakeBoldText(false);
                break;
            case R.id.rad_my:
                MyFragment myFragment = new MyFragment();
                bundle.putString("username", user.getUsername());
                myFragment.setArguments(bundle);
                transaction.replace(R.id.user_content_Layout, myFragment);
                rb_my.setActivated(true);
                rb_shopping.setActivated(false);
                rb_order.setActivated(false);
                rb_my.setTextColor(Color.parseColor("#e9730a"));
                rb_shopping.setTextColor(Color.parseColor("#404040"));
                rb_order.setTextColor(Color.parseColor("#404040"));
                my.setFakeBoldText(true);
                shopping.setFakeBoldText(false);
                order.setFakeBoldText(false);
                break;
        }
        transaction.commit();
    }

    private boolean isDestroyed = false;
    private void destroy()  {
        if (isDestroyed) {
            return;
        }
        // 回收资源
        isDestroyed = true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (isFinishing()) {
            destroy();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        destroy();//需要在onDestroy方法中进一步检测是否回收资源等。
    }

}

