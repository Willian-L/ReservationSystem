package com.william.reservationsystem.controller.UserHomepage.Fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.william.reservationsystem.R;
import com.william.reservationsystem.model.DBServerForMenu;
import com.william.reservationsystem.model.Menus;

public class OrderFragment extends Fragment {

    private TextView date, dish_1_1, dish_1_2, dish_1_3, dish_1_4, dish_2_1, dish_2_2, dish_2_3, dish_2_4, soup_1, soup_2;
    private LinearLayout menu_one, menu_two, soup_layout_one, soup_layout_two;
    private Button confirm;
    private ScrollView have_menu;

    Menus menu = new Menus();

    private int clicks_one = 0;
    private int clicks_two = 0;
    private int clicks_soup = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, null);

        init(view);
        selectDB();


        menu_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks_one += 1;
                if (clicks_one % 2 == 1) {
                    menu_one.setBackgroundColor(Color.parseColor("#80000000"));
                    if (clicks_two != 0) {
                        clicks_two -= 1;
                        menu_two.setBackgroundColor(Color.parseColor("#80e6e6e6"));
                    }
                }
                else {
                    menu_one.setBackgroundColor(Color.parseColor("#80e6e6e6"));
                }
            }
        });

        menu_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks_two += 1;
                if (clicks_two % 2 == 1) {
                    menu_two.setBackgroundColor(Color.parseColor("#80000000"));
                    if (clicks_one != 0) {
                        clicks_one -= 1;
                        menu_one.setBackgroundColor(Color.parseColor("#80e6e6e6"));
                    }
                }
                else {
                    menu_two.setBackgroundColor(Color.parseColor("#80e6e6e6"));
                }
            }
        });

        soup_layout_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clicks_soup += 1;
                if (clicks_soup % 2 == 1){
                    soup_layout_one.setBackgroundColor(Color.parseColor("#80000000"));
                } else {
                    soup_layout_one.setBackgroundColor(Color.parseColor("#80e6e6e6"));
                }
            }
        });

        return view;
    }

    private void selectDB() {
        DBServerForMenu db = new DBServerForMenu(getContext());
        db.open();
        Cursor cursor = db.select();
        if (cursor.moveToLast()) {
            have_menu.setVisibility(View.VISIBLE);
            menu.setDate(cursor.getString(cursor.getColumnIndex("day")));
            menu.setOne_dishes_one(cursor.getString(cursor.getColumnIndex("dishes_one")));
            menu.setOne_dishes_two(cursor.getString(cursor.getColumnIndex("dishes_two")));
            menu.setOne_dishes_three(cursor.getString(cursor.getColumnIndex("dishes_three")));
            menu.setOne_dishes_four(cursor.getString(cursor.getColumnIndex("dishes_four")));
            menu.setOne_soup(cursor.getString(cursor.getColumnIndex("soup")));
            date.setText(menu.getDate());
            dish_1_1.setText(menu.getOne_dishes_one());
            dish_1_2.setText(menu.getOne_dishes_two());
            dish_1_3.setText(menu.getOne_dishes_three());
            dish_1_4.setText(menu.getOne_dishes_four());
            soup_1.setText(menu.getOne_soup());
        }
        cursor.moveToPrevious();
        if (cursor.getString(cursor.getColumnIndex("day")).equals(menu.getDate())) {
            menu_two.setVisibility(View.VISIBLE);
            menu.setTwo_dishes_one(cursor.getString(cursor.getColumnIndex("dishes_one")));
            menu.setTwo_dishes_two(cursor.getString(cursor.getColumnIndex("dishes_two")));
            menu.setTwo_dishes_three(cursor.getString(cursor.getColumnIndex("dishes_three")));
            menu.setTwo_dishes_four(cursor.getString(cursor.getColumnIndex("dishes_four")));
            dish_2_1.setText(menu.getOne_dishes_one());
            dish_2_2.setText(menu.getOne_dishes_two());
            dish_2_3.setText(menu.getOne_dishes_three());
            dish_2_4.setText(menu.getOne_dishes_four());
            Log.i("soup",cursor.getString(cursor.getColumnIndex("soup")) + ";" + menu.getOne_soup());
            if (!cursor.getString(cursor.getColumnIndex("soup")).equals(menu.getOne_soup()))
            {
                menu.setTwo_soup(cursor.getString(cursor.getColumnIndex("soup")));
                soup_layout_two.setVisibility(View.VISIBLE);
                soup_2.setText(menu.getTwo_soup());
            }
        }
    }

    private void init(View view) {
        date = view.findViewById(R.id.order_date);
        dish_1_1 = view.findViewById(R.id.order_dish_1_1);
        dish_1_2 = view.findViewById(R.id.order_dish_1_2);
        dish_1_3 = view.findViewById(R.id.order_dish_1_3);
        dish_1_4 = view.findViewById(R.id.order_dish_1_4);
        dish_2_1 = view.findViewById(R.id.order_dish_2_1);
        dish_2_2 = view.findViewById(R.id.order_dish_2_2);
        dish_2_3 = view.findViewById(R.id.order_dish_2_3);
        dish_2_4 = view.findViewById(R.id.order_dish_2_4);
        soup_1 = view.findViewById(R.id.order_soup_one);
        soup_2 = view.findViewById(R.id.order_soup_two);
        menu_one = view.findViewById(R.id.order_menu_one);
        menu_two = view.findViewById(R.id.order_menu_two);
        soup_layout_one = view.findViewById(R.id.order_layout_soup_one);
        soup_layout_two = view.findViewById(R.id.order_layout_soup_two);
        have_menu = view.findViewById(R.id.have_menu);
    }
}
