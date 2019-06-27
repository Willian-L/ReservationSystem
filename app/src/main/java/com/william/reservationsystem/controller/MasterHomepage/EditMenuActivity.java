package com.william.reservationsystem.controller.MasterHomepage;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class EditMenuActivity extends AppCompatActivity {

    private DatePicker mData;
    private LinearLayout menu_two;
    private EditText edt_dish_1_1, edt_dish_1_2, edt_dish_1_3, edt_dish_1_4, edt_soup_1,
            edt_dish_2_1, edt_dish_2_2, edt_dish_2_3, edt_dish_2_4, edt_soup_2;
    private ImageButton confirm, add_menu;

    String date;

    Menus menu = new Menus();

    int tag = 0;

    DBServerForMenu db = new DBServerForMenu(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_menu);

        init();
        selectDB();
        editDate();
    }

    private void selectDB() {
        getDate();
        Cursor cursor;
        db.open();
        cursor = db.selectByDate(menu.getDate());
        if (cursor.moveToNext()) {
            tag = 1;
            date = cursor.getString(cursor.getColumnIndex("day"));
            menu.setDate(date);
            String[] date = menu.getDate().split("/");
            int year = Integer.parseInt(date[0]);
            int month = Integer.parseInt(date[1]);
            int day = Integer.parseInt(date[2]);
            Log.i("date", month + "");
            mData.updateDate(year, month - 1, day);
            edt_dish_1_1.setText(cursor.getString(cursor.getColumnIndex("dishes_one")));
            edt_dish_1_2.setText(cursor.getString(cursor.getColumnIndex("dishes_two")));
            edt_dish_1_3.setText(cursor.getString(cursor.getColumnIndex("dishes_three")));
            edt_dish_1_4.setText(cursor.getString(cursor.getColumnIndex("dishes_four")));
            edt_soup_1.setText(cursor.getString(cursor.getColumnIndex("soup")));
        }
        if (cursor.getCount() == 2) {
            tag = 2;
            menu_two.setVisibility(View.VISIBLE);
            add_menu.setVisibility(View.GONE);
            cursor.moveToNext();
            edt_dish_2_1.setText(cursor.getString(cursor.getColumnIndex("dishes_one")));
            edt_dish_2_2.setText(cursor.getString(cursor.getColumnIndex("dishes_two")));
            edt_dish_2_3.setText(cursor.getString(cursor.getColumnIndex("dishes_three")));
            edt_dish_2_4.setText(cursor.getString(cursor.getColumnIndex("dishes_four")));
            edt_soup_2.setText(cursor.getString(cursor.getColumnIndex("soup")));
        }
    }

    private void editDate() {
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

    private void getDate() {
        Intent intent = getIntent();
        menu.setDate(intent.getStringExtra("date"));
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

    public void back_Edit(View view) {
        Intent intent = new Intent(this, HomepageForMasterActivity.class);
        startActivity(intent);
        finish();
    }

    private void init() {
        confirm = findViewById(R.id.edit_push);
        mData = findViewById(R.id.edit_push_data);
        add_menu = findViewById(R.id.edit_add_menu);
        menu_two = findViewById(R.id.edit_Re_menu_two);
        edt_dish_1_1 = findViewById(R.id.edit_edt_dish_1_1);
        edt_dish_1_2 = findViewById(R.id.edit_edt_dish_1_2);
        edt_dish_1_3 = findViewById(R.id.edit_edt_dish_1_3);
        edt_dish_1_4 = findViewById(R.id.edit_edt_dish_1_4);
        edt_dish_2_1 = findViewById(R.id.edit_edt_dish_2_1);
        edt_dish_2_2 = findViewById(R.id.edit_edt_dish_2_2);
        edt_dish_2_3 = findViewById(R.id.edit_edt_dish_2_3);
        edt_dish_2_4 = findViewById(R.id.edit_edt_dish_2_4);
        edt_soup_1 = findViewById(R.id.edit_edt_soup_1);
        edt_soup_2 = findViewById(R.id.edit_edt_soup_2);
    }

    public void edit(View view) {
        db.open();
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


        if (tag != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            getDishesForOne();
            builder.setTitle("Please confirm the contents of the menus");
            builder.setMessage(dialogString());
            builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.delete(date);
                    Log.i("tag", tag + "");
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

    public void add_menu(View view) {
        menu_two.setVisibility(View.VISIBLE);
        add_menu.setVisibility(View.GONE);
    }

    public void deleteOne(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete menu");
        if (tag == 2) {
            dialog.setMessage("Do you want to delete menu-1?");
            dialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    edt_dish_1_1.setText(edt_dish_2_1.getText().toString().trim());
                    edt_dish_1_2.setText(edt_dish_2_2.getText().toString().trim());
                    edt_dish_1_3.setText(edt_dish_2_3.getText().toString().trim());
                    edt_dish_1_4.setText(edt_dish_2_4.getText().toString().trim());
                    edt_soup_1.setText(edt_soup_2.getText().toString().trim());
                    edt_dish_2_1.setText(null);
                    edt_dish_2_2.setText(null);
                    edt_dish_2_3.setText(null);
                    edt_dish_2_4.setText(null);
                    edt_soup_2.setText(null);
                    menu_two.setVisibility(View.GONE);
                    add_menu.setVisibility(View.VISIBLE);
                }
            });
            dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        } else {
            dialog.setMessage("Do you want to delete menu-1?");
            dialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    edt_dish_1_1.setText(null);
                    edt_dish_1_2.setText(null);
                    edt_dish_1_3.setText(null);
                    edt_dish_1_4.setText(null);
                    edt_soup_1.setText(null);
                }
            });
            dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
        }
        dialog.show();
    }


    public void deleteTwo(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Delete menu");
        dialog.setMessage("Do you want to delete menu-2?");
        dialog.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edt_dish_2_1.setText(null);
                edt_dish_2_2.setText(null);
                edt_dish_2_3.setText(null);
                edt_dish_2_4.setText(null);
                edt_soup_2.setText(null);
                menu_two.setVisibility(View.GONE);
                add_menu.setVisibility(View.VISIBLE);
            }
        });
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.show();
    }
}
