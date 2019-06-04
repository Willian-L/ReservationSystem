package com.william.reservationsystem.controller.MasterHomepage;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.william.reservationsystem.R;
import com.william.reservationsystem.controller.MasterHomepage.Fragment.DailyMenuFragment;
import com.william.reservationsystem.controller.MasterHomepage.Fragment.UserInfoFragment;

public class HomepageForMasterActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private LinearLayout it_daily, it_my;
    private ImageView imgOpen;
    private ImageButton imgBtnAdd;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    private GestureDetector detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_for_master);

        init();

        embed();

        detector = new GestureDetector(getApplicationContext(), this);

        imgOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });

        it_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = manager.beginTransaction();
                transaction.replace(R.id.master_content, new DailyMenuFragment());
                Toast.makeText(getApplicationContext(), "onclick", Toast.LENGTH_SHORT).show();
                transaction.commit();
                mDrawerLayout.closeDrawers();
            }
        });

        it_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = manager.beginTransaction();
                transaction.replace(R.id.master_content, new UserInfoFragment());
                Toast.makeText(getApplicationContext(), "onclick", Toast.LENGTH_SHORT).show();
                transaction.commit();
                mDrawerLayout.closeDrawers();
            }
        });
    }

    private void init() {
        mDrawerLayout = findViewById(R.id.draLay_master);
        mToolbar = findViewById(R.id.toolbar_master);
        it_daily = findViewById(R.id.item_daily);
        it_my = findViewById(R.id.item_my);
        imgOpen = findViewById(R.id.imgBtn_open);
        imgBtnAdd = findViewById(R.id.imgBtn_add);
    }

    /**
     * Embed Fragment
     */
    private void embed() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.master_content, new DailyMenuFragment());
        transaction.commit();
    }

        @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Toast.makeText(this,"onDown",Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX()-e2.getX()>120){
            Toast.makeText(getApplicationContext(),"fling",Toast.LENGTH_SHORT).show();
        }else if (e1.getX()-e2.getX()<120){
            Toast.makeText(getApplicationContext(),"fling",Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
