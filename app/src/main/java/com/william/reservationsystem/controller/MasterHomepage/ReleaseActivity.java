package com.william.reservationsystem.controller.MasterHomepage;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.william.reservationsystem.R;
import com.william.reservationsystem.model.DBServerForBookings;
import com.william.reservationsystem.model.DBServerForMenu;
import com.william.reservationsystem.model.Menus;

import java.util.Calendar;

public class ReleaseActivity extends AppCompatActivity {

    private DatePicker mData;
    private LinearLayout menu_two;
    private EditText edt_dish_1_1, edt_dish_1_2, edt_dish_1_3, edt_dish_1_4, edt_soup_1,
            edt_dish_2_1, edt_dish_2_2, edt_dish_2_3, edt_dish_2_4, edt_soup_2;
    private ImageButton confirm, add_menu;

    Menus menu = new Menus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);

        init();
        defaultDate();
        getDate();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                push();
            }
        });
    }

    private void push(){
        DBServerForMenu db = new DBServerForMenu(getApplicationContext());
        db.open();
        getDishesForOne();
        menu.setMenu("menu_one");
        db.insert(menu.getDate(),menu.getMenu(),menu.getDishes_one(),menu.getDishes_two(),menu.getDishes_three(),menu.getDishes_four(),menu.getSoup());
        if (menu_two.getVisibility() == View.VISIBLE){
            menu.setMenu("menu_two");
            getDishesForTwo();
            db.insert(menu.getDate(),menu.getMenu(),menu.getDishes_one(),menu.getDishes_two(),menu.getDishes_three(),menu.getDishes_four(),menu.getSoup());
        }
        db.close();
        finish();
    }

    private void getDishesForOne(){
        menu.setDishes_one(edt_dish_1_1.getText().toString().trim());
        menu.setDishes_two(edt_dish_1_2.getText().toString().trim());
        menu.setDishes_three(edt_dish_1_3.getText().toString().trim());
        menu.setDishes_four(edt_dish_1_4.getText().toString().trim());
        menu.setSoup(edt_soup_1.getText().toString().trim());
    }

    private void getDishesForTwo(){
        menu.setDishes_one(edt_dish_2_1.getText().toString().trim());
        menu.setDishes_two(edt_dish_2_2.getText().toString().trim());
        menu.setDishes_three(edt_dish_2_3.getText().toString().trim());
        menu.setDishes_four(edt_dish_2_4.getText().toString().trim());
        menu.setSoup(edt_soup_2.getText().toString().trim());
    }

    private void getDate(){
        if (Build.VERSION.SDK_INT >= 26) {
            mData.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    year = mData.getYear();
                    monthOfYear = mData.getMonth();
                    dayOfMonth = mData.getDayOfMonth();
                    menu.setYear(year);
                    menu.setMonth(monthOfYear);
                    menu.setDay(dayOfMonth);
                    menu.setDate(menu.getYear()+"/"+menu.getMonth()+"/"+menu.getDay());
                }
            });
        }else {
            mData.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int year = mData.getYear();
                    int monthOfYear = mData.getMonth();
                    int dayOfMonth = mData.getDayOfMonth();
                    menu.setYear(year);
                    menu.setMonth(monthOfYear);
                    menu.setDay(dayOfMonth);
                    menu.setDate(menu.getYear() + "/" + menu.getMonth() + "/" + menu.getDay());
                    return false;
                }
            });
        }
    }

    private void defaultDate() {
        Calendar calendar = Calendar.getInstance();
        int default_year = calendar.get(Calendar.YEAR);
        int default_month = calendar.get(Calendar.MONTH) + 1;
        int default_day = calendar.get(Calendar.DAY_OF_MONTH);

        getMonthLastDay();
        if (default_day != getMonthLastDay()) {
            default_day += 1;
        } else {
            default_month += 1;
            default_day = 1;
        }
        if (default_month > 12) {
            default_year += 1;
            default_month = 1;
            default_day = 1;
        }

        mData.updateDate(default_year, default_month, default_day);
    }

    private int getMonthLastDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int maxData = calendar.get(Calendar.DATE);
        return maxData;
    }

    public void back_Edit(View view) {
        Intent intent = new Intent(this, HomepageForMasterActivity.class);
        startActivity(intent);
        finish();
    }

    public void newMenu(View view) {
        add_menu.setVisibility(View.GONE);
        menu_two.setVisibility(View.VISIBLE);
    }

    private void init() {
        confirm = findViewById(R.id.push);
        mData = findViewById(R.id.push_data);
        add_menu = findViewById(R.id.add_menu);
        menu_two = findViewById(R.id.Re_menu_two);
        edt_dish_1_1 = findViewById(R.id.edt_dish_1_1);
        edt_dish_1_2 = findViewById(R.id.edt_dish_1_2);
        edt_dish_1_3 = findViewById(R.id.edt_dish_1_3);
        edt_dish_1_4 = findViewById(R.id.edt_dish_1_4);
        edt_dish_2_1 = findViewById(R.id.edt_dish_2_1);
        edt_dish_2_2 = findViewById(R.id.edt_dish_2_2);
        edt_dish_2_3 = findViewById(R.id.edt_dish_2_3);
        edt_dish_2_4 = findViewById(R.id.edt_dish_2_4);
        edt_soup_1 = findViewById(R.id.edt_soup_1);
        edt_soup_2 = findViewById(R.id.edt_soup_2);
    }
}
