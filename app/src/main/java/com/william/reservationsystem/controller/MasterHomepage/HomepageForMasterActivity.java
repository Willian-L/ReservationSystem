package com.william.reservationsystem.controller.MasterHomepage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.william.reservationsystem.R;
import com.william.reservationsystem.controller.LoginAndRegister.UserLoginActivity;
import com.william.reservationsystem.controller.MasterHomepage.Fragment.DailyMenuFragment;
import com.william.reservationsystem.controller.MasterHomepage.Fragment.UserInfoFragment;
import com.william.reservationsystem.model.SharedPreferencesUtils;

import java.lang.reflect.Field;

public class HomepageForMasterActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private TextView title;
    private LinearLayout it_daily, it_expory, it_logout;
    private ImageView imgOpen;
    private ImageButton imgBtnAdd;
    private FragmentManager manager;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_for_master);

        init();

        embed();

        setDrawerLeftEdgeSize(this,mDrawerLayout,0.5f);

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
                transaction.commit();
                title.setText("Daily Menu");
                mDrawerLayout.closeDrawers();
            }
        });

        it_expory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transaction = manager.beginTransaction();
                transaction.replace(R.id.master_content, new UserInfoFragment());
                transaction.commit();
                title.setText("Export Data");
                mDrawerLayout.closeDrawers();
            }
        });

        it_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    /**
     * Logout method
     */
    private void logout() {
        SharedPreferencesUtils.getInstance().clear();
        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void init() {
        mDrawerLayout = findViewById(R.id.draLay_master);
        mToolbar = findViewById(R.id.toolbar_master);
        it_daily = findViewById(R.id.item_daily);
        it_expory = findViewById(R.id.item_export);
        imgOpen = findViewById(R.id.imgBtn_open);
        imgBtnAdd = findViewById(R.id.imgBtn_add);
        it_logout = findViewById(R.id.item_logout);
        title = findViewById(R.id.master_title);
    }

    private void setDrawerLeftEdgeSize (Activity activity, DrawerLayout drawerLayout, float displayWidthPercentage) {
        if (activity == null || drawerLayout == null) return;
        try {
            // 找到 ViewDragHelper 并设置 Accessible 为true
            Field leftDraggerField =
                    drawerLayout.getClass().getDeclaredField("mLeftDragger");
            leftDraggerField.setAccessible(true);
            ViewDragHelper leftDragger = (ViewDragHelper) leftDraggerField.get(drawerLayout);

            // 找到 edgeSizeField 并设置 Accessible 为true
            Field edgeSizeField = leftDragger.getClass().getDeclaredField("mEdgeSize");
            edgeSizeField.setAccessible(true);
            int edgeSize = edgeSizeField.getInt(leftDragger);

            // 设置新的边缘大小
            Point displaySize = new Point();
            activity.getWindowManager().getDefaultDisplay().getSize(displaySize);
            edgeSizeField.setInt(leftDragger, Math.max(edgeSize, (int) (displaySize.x * displayWidthPercentage)));
        } catch (NoSuchFieldException e) {
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }
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

    public void toRelease(View view) {
        Intent intent = new Intent(this,ReleaseActivity.class);
        startActivity(intent);
    }
}
