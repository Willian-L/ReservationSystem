package com.william.reservationsystem.controller.MasterHomepage;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.william.reservationsystem.R;
import com.william.reservationsystem.model.DBServerForMenu;
import com.william.reservationsystem.model.Menus;

import java.util.Calendar;

public class ReleaseActivity extends AppCompatActivity {

    private DatePicker mData;
    private LinearLayout menu_two;
    private EditText edt_dish_1_1, edt_dish_1_2, edt_dish_1_3, edt_dish_1_4, edt_soup_1,
            edt_dish_2_1, edt_dish_2_2, edt_dish_2_3, edt_dish_2_4, edt_soup_2;
    private ImageButton confirm, add_menu;

    private int tag = 0;

    Menus menu = new Menus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_release);

        init();
        defaultDate();
        getDate();
    }

    public void push(View view) {
        final DBServerForMenu db = new DBServerForMenu(getApplicationContext());
        db.open();
        if (db.select(menu.getDate()) == 0) {
            if (!edt_dish_1_1.getText().toString().trim().equals("")) {
                tag = 1;
                if (menu_two.getVisibility() == View.VISIBLE) {
                    if (!edt_dish_2_1.getText().toString().trim().equals("")) {
                        tag = 2;
                    }
                }
            } else {
                Toast.makeText(getApplicationContext(), "the contents of the menu is empty", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "The date already has menus", Toast.LENGTH_LONG).show();
        }

        if (tag != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            getDishesForOne();
            builder.setTitle("Please confirm the contents of the menus");
            builder.setMessage(dialogString());
            builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (tag == 2) {
                        db.insert(menu.getDate(), menu.getMENU_TWO(), menu.getTwo_dishes_one(), menu.getTwo_dishes_two(), menu.getTwo_dishes_three(), menu.getTwo_dishes_four(), menu.getTwo_soup());
                    }
                    db.insert(menu.getDate(), menu.getMENU_ONE(), menu.getOne_dishes_one(), menu.getOne_dishes_two(), menu.getOne_dishes_three(), menu.getOne_dishes_four(), menu.getOne_soup());
                    db.close();
                    finish();
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
        }
    }

    private void getDishesForOne() {
        menu.setOne_dishes_one(edt_dish_1_1.getText().toString().trim());
        menu.setOne_dishes_two(edt_dish_1_2.getText().toString().trim());
        menu.setOne_dishes_three(edt_dish_1_3.getText().toString().trim());
        menu.setOne_dishes_four(edt_dish_1_4.getText().toString().trim());
        menu.setOne_soup(edt_soup_1.getText().toString().trim());
    }

    private void getDishesForTwo() {
        menu.setTwo_dishes_one(edt_dish_2_1.getText().toString().trim());
        menu.setTwo_dishes_two(edt_dish_2_2.getText().toString().trim());
        menu.setTwo_dishes_three(edt_dish_2_3.getText().toString().trim());
        menu.setTwo_dishes_four(edt_dish_2_4.getText().toString().trim());
        menu.setTwo_soup(edt_soup_2.getText().toString().trim());
    }

    private String dialogString() {
        String message;
        String one_dishes_two = "";
        String one_dishes_three = "";
        String one_dishes_four = "";
        String one_soup = "";
        getDishesForOne();
        if (!menu.getOne_dishes_two().equals("")) {
            one_dishes_two = "\n" + "\t\t②" + menu.getOne_dishes_two();
        }
        if (!menu.getOne_dishes_three().equals("")) {
            one_dishes_three = "\n" + "\t\t③" + menu.getOne_dishes_three();
        }
        if (!menu.getOne_dishes_four().equals("")) {
            one_dishes_four = "\n" + "\t\t④" + menu.getOne_dishes_four();
        }
        if (!menu.getOne_soup().equals("")) {
            one_soup = "\n" + "SOUP\n\t\t" + menu.getOne_soup();
        }
        message = "DATE\t\t-\t" + menu.getDate() + "\n\n" +
                "MENU\t-\t" + menu.getMENU_ONE() + "\n" +
                "DISHES\n\t\t①" + menu.getOne_dishes_one() + one_dishes_two + one_dishes_three + one_dishes_four + one_soup;

        if (tag == 2) {
            getDishesForTwo();
            String two_dishes_two = "";
            String two_dishes_three = "";
            String two_dishes_four = "";
            String two_soup = "";
            if (!menu.getTwo_dishes_two().equals("")) {
                two_dishes_two = "\n" + "\t\t②" + menu.getTwo_dishes_two();
            }
            if (!menu.getTwo_dishes_three().equals("")) {
                two_dishes_three = "\n" + "\t\t③" + menu.getTwo_dishes_three();
            }
            if (!menu.getTwo_dishes_four().equals("")) {
                two_dishes_four = "\n" + "\t\t④" + menu.getTwo_dishes_four();
            }
            if (!menu.getTwo_soup().equals("")) {
                two_soup = "\n" + "SOUP\n\t\t" + menu.getTwo_soup();
            }
            message = message + "\n\n" + "MENU\t-\t" + menu.getMENU_TWO() + "\n" + "DISHES\n\t\t①" + menu.getTwo_dishes_one() + two_dishes_two + two_dishes_three + two_dishes_four + two_soup;
        }

        return message;
    }

    private void getDate() {
        if (Build.VERSION.SDK_INT >= 26) {
            mData.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    year = mData.getYear();
                    monthOfYear = mData.getMonth() + 1;
                    dayOfMonth = mData.getDayOfMonth();
                    menu.setDate(year + "/" + monthOfYear + "/" + dayOfMonth);
                }
            });
        } else {
            mData.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    int year = mData.getYear();
                    int monthOfYear = mData.getMonth() + 1;
                    int dayOfMonth = mData.getDayOfMonth();
                    menu.setDate(year + "/" + monthOfYear + "/" + dayOfMonth);
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

        mData.updateDate(default_year, default_month - 1, default_day);

        menu.setDate(default_year + "/" + default_month + "/" + default_day);
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
