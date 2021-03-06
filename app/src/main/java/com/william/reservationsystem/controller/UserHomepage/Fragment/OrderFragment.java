package com.william.reservationsystem.controller.UserHomepage.Fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.william.reservationsystem.R;
import com.william.reservationsystem.model.Bookings;
import com.william.reservationsystem.model.DBServerForBookings;
import com.william.reservationsystem.model.DBServerForMenu;
import com.william.reservationsystem.model.DBServerForU;
import com.william.reservationsystem.model.Menus;

public class OrderFragment extends Fragment {

    private TextView date, dish_1_1, dish_1_2, dish_1_3, dish_1_4, dish_2_1, dish_2_2, dish_2_3, dish_2_4, soup_1, soup_2;
    private LinearLayout menu_one, menu_two, soup_layout_one, soup_layout_two;
    private Button confirm;
    private ScrollView have_menu;
    private RelativeLayout have_not_menu;
    private RadioButton rb_order, rb_shopping;

    Menus menu = new Menus();
    Bookings booking = new Bookings();

    private boolean clicks_one = false;
    private boolean clicks_two = false;
    private boolean clicks_soup_one = false;
    private boolean clicks_soup_two = false;

    private String usermane;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        init(view);
        initForActivity((AppCompatActivity) getActivity());

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            usermane = bundle.getString("username");
            DBServerForU db = new DBServerForU(getContext());
            db.open();
            Cursor cursor = db.selectByUsername(usermane);
            cursor.moveToNext();
            booking.setUser((cursor.getString(cursor.getColumnIndex("name"))));
            db.close();
        }

        selectDB();

        menu_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks_one == false) {
                    clicks_one = true;
                    menu_one.setBackgroundColor(Color.parseColor("#ffa800"));
                    if (clicks_two == true) {
                        clicks_two = false;
                        menu_two.setBackgroundColor(Color.parseColor("#80e6e6e6"));
                    } else {
                        if (clicks_soup_one == false) {
                            clicks_soup_one = true;
                            soup_layout_one.setBackgroundColor(Color.parseColor("#ffa800"));
                        }
                    }
                } else {
                    clicks_one = false;
                    menu_one.setBackgroundColor(Color.parseColor("#80e6e6e6"));
                }
                haveClick();
            }
        });

        menu_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks_two == false) {
                    clicks_two = true;
                    menu_two.setBackgroundColor(Color.parseColor("#ffa800"));
                    if (clicks_one == true) {
                        clicks_one = false;
                        menu_one.setBackgroundColor(Color.parseColor("#80e6e6e6"));
                    } else {
                        if (soup_layout_two.getVisibility() == View.VISIBLE) {
                            if (clicks_soup_two == false) {
                                clicks_soup_two = true;
                                soup_layout_two.setBackgroundColor(Color.parseColor("#ffa800"));
                            }
                        } else {
                            if (clicks_soup_one == false) {
                                clicks_soup_one = true;
                                soup_layout_one.setBackgroundColor(Color.parseColor("#ffa800"));
                            }
                        }
                    }
                } else {
                    clicks_two = false;
                    menu_two.setBackgroundColor(Color.parseColor("#80e6e6e6"));
                }
                haveClick();
            }
        });

        soup_layout_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks_soup_one == false) {
                    clicks_soup_one = true;
                    soup_layout_one.setBackgroundColor(Color.parseColor("#ffa800"));
                    if (clicks_soup_two == true) {
                        soup_layout_two.setBackgroundColor(Color.parseColor("#80e6e6e6"));
                        clicks_soup_two = false;
                    }
                } else {
                    soup_layout_one.setBackgroundColor(Color.parseColor("#80e6e6e6"));
                    clicks_soup_one = false;
                }
                haveClick();
            }
        });

        soup_layout_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks_soup_two == false) {
                    clicks_soup_two = true;
                    soup_layout_two.setBackgroundColor(Color.parseColor("#ffa800"));
                    if (clicks_soup_one == true) {
                        soup_layout_one.setBackgroundColor(Color.parseColor("#80e6e6e6"));
                        clicks_soup_one = false;
                    }
                } else {
                    soup_layout_two.setBackgroundColor(Color.parseColor("#80e6e6e6"));
                    clicks_soup_two = false;
                }
                haveClick();
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm();
            }
        });

        return view;
    }

    private void selectDB() {
        DBServerForMenu db = new DBServerForMenu(getContext());
        db.open();
        Cursor cursor = db.select();
        if (cursor.getCount() > 0) {
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
            if (cursor.moveToPrevious()) {
                if (cursor.getString(cursor.getColumnIndex("day")).equals(menu.getDate())) {
                    menu_two.setVisibility(View.VISIBLE);
                    menu.setTwo_dishes_one(cursor.getString(cursor.getColumnIndex("dishes_one")));
                    menu.setTwo_dishes_two(cursor.getString(cursor.getColumnIndex("dishes_two")));
                    menu.setTwo_dishes_three(cursor.getString(cursor.getColumnIndex("dishes_three")));
                    menu.setTwo_dishes_four(cursor.getString(cursor.getColumnIndex("dishes_four")));
                    dish_2_1.setText(menu.getTwo_dishes_one());
                    dish_2_2.setText(menu.getTwo_dishes_two());
                    dish_2_3.setText(menu.getTwo_dishes_three());
                    dish_2_4.setText(menu.getTwo_dishes_four());
                    String soupTwo = cursor.getString(cursor.getColumnIndex("soup"));
                    if (!soupTwo.equals(menu.getOne_soup()) && !soupTwo.equals("")) {
                        menu.setTwo_soup(cursor.getString(cursor.getColumnIndex("soup")));
                        soup_layout_two.setVisibility(View.VISIBLE);
                        soup_2.setText(menu.getTwo_soup());
                    }
                }
            }
            db.close();
            DBServerForBookings dbBooking = new DBServerForBookings(getContext());
            dbBooking.open();
            if (dbBooking.selectByDay(menu.getDate(), booking.getUser()).getCount() == 0) {
                have_not_menu.setVisibility(View.GONE);
                have_menu.setVisibility(View.VISIBLE);
            } else {
                have_menu.setVisibility(View.GONE);
                have_not_menu.setVisibility(View.VISIBLE);
            }
            dbBooking.close();
        }
    }


    private void haveClick() {
        if (clicks_one || clicks_two || clicks_soup_one || clicks_soup_two) {
            confirm.setEnabled(true);
            confirm.setBackgroundResource(R.drawable.bg_button_clickable);
        } else {
            confirm.setEnabled(false);
            confirm.setBackgroundResource(R.drawable.bg_button_unclickable);
        }
    }

    private void initForActivity(AppCompatActivity activity) {
        rb_order = activity.findViewById(R.id.rad_order);
        rb_shopping = activity.findViewById(R.id.rad_shopping);
    }

    private void confirm() {
        booking.setDate(menu.getDate());
        if (clicks_one) {
            booking.setMenu(menu.getMENU_ONE());
            booking.setDishes_one(menu.getOne_dishes_one());
            booking.setDishes_two(menu.getOne_dishes_two());
            booking.setDishes_three(menu.getOne_dishes_three());
            booking.setDishes_four(menu.getOne_dishes_four());
        } else if (clicks_two) {
            booking.setMenu(menu.getMENU_TWO());
            booking.setDishes_one(menu.getTwo_dishes_one());
            booking.setDishes_two(menu.getTwo_dishes_two());
            booking.setDishes_three(menu.getTwo_dishes_three());
            booking.setDishes_four(menu.getTwo_dishes_four());
        } else {
            booking.setMenu("null");
        }
        if (clicks_soup_one) {
            booking.setSoup(menu.getOne_soup());
        } else if (clicks_soup_two) {
            booking.setSoup(menu.getTwo_soup());
        } else {
            booking.setSoup("");
        }
        DBServerForBookings dbBooking = new DBServerForBookings(getContext());
        dbBooking.open();
        if (dbBooking.insert(booking.getDate(), booking.getMenu(), booking.getUser(), booking.getDishes_one(), booking.getDishes_two(), booking.getDishes_three(), booking.getDishes_four(), booking.getSoup(), null)) {
            Bundle bundle = new Bundle();
            bundle.putString("username", usermane);
            Fragment IndentFragment = new IndentFragment();
            IndentFragment.setArguments(bundle);
            getFragmentManager().beginTransaction().replace(R.id.user_content_Layout, IndentFragment).commit();
            rb_shopping.setActivated(true);
            rb_order.setActivated(false);
            rb_shopping.setTextColor(Color.parseColor("#e9730a"));
            rb_order.setTextColor(Color.parseColor("#404040"));
            TextPaint order = rb_order.getPaint();
            TextPaint shopping = rb_shopping.getPaint();
            shopping.setFakeBoldText(true);
            order.setFakeBoldText(false);
        }
        dbBooking.close();
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
        have_not_menu = view.findViewById(R.id.have_not_menu);
        confirm = view.findViewById(R.id.order_btn);
    }
}
