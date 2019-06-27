package com.william.reservationsystem.controller.MasterHomepage.Detail;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;

import com.william.reservationsystem.R;
import com.william.reservationsystem.controller.UserHomepage.Fragment.ModifyOrderFragment;
import com.william.reservationsystem.controller.UserHomepage.Fragment.OrderFragment;
import com.william.reservationsystem.model.Bookings;

public class ModifyMenuActivity extends AppCompatActivity {


    private FragmentManager manager = getSupportFragmentManager();
    private FragmentTransaction transaction = manager.beginTransaction();

    Bookings booking = new Bookings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_menu);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            booking.setUser(bundle.getString("username"));
            booking.setDate(bundle.getString("date"));

            embed();
        }
    }

    private void embed(){
        Bundle bundle = new Bundle();
        ModifyOrderFragment modifyOrderFragment = new ModifyOrderFragment();
        bundle.putString("username", booking.getUser());
        bundle.putString("date", booking.getDate());
        modifyOrderFragment.setArguments(bundle);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        transaction.add(R.id.detail_layout, modifyOrderFragment);
        transaction.commit();
    }
}
