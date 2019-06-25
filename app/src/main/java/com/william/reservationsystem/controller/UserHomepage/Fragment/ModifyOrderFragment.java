package com.william.reservationsystem.controller.UserHomepage.Fragment;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.william.reservationsystem.R;
import com.william.reservationsystem.model.Bookings;
import com.william.reservationsystem.model.DBServerForBookings;
import com.william.reservationsystem.model.DBServerForMenu;
import com.william.reservationsystem.model.Menus;

public class ModifyOrderFragment extends Fragment {

    private TextView date, dish_1_1, dish_1_2, dish_1_3, dish_1_4, dish_2_1, dish_2_2, dish_2_3, dish_2_4, soup_1, soup_2;
    private LinearLayout menu_one, menu_two, soup_layout_one, soup_layout_two;
    private Button confirm, back;

    Bookings booking = new Bookings();
    Menus menu = new Menus();

    private boolean clicks_one = false;
    private boolean clicks_two = false;
    private boolean clicks_soup_one = false;
    private boolean clicks_soup_two = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_modify_order, container, false);

        init(view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            booking.setUser(bundle.getString("username"));
            booking.setDate(bundle.getString("date"));
        }

        getMenu(booking.getDate());
        getChoice(booking.getDate(), booking.getUser());

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

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void getMenu(String thisDate) {
        DBServerForMenu db = new DBServerForMenu(getContext());
        db.open();
        Cursor cursor = db.selectByDate(thisDate);
        if (cursor.getCount() > 0) {
            if (cursor.moveToNext()) {
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
            if (cursor.getCount() == 2) {
                cursor.moveToNext();
                menu_two.setVisibility(View.VISIBLE);
                menu.setTwo_dishes_one(cursor.getString(cursor.getColumnIndex("dishes_one")));
                menu.setTwo_dishes_two(cursor.getString(cursor.getColumnIndex("dishes_two")));
                menu.setTwo_dishes_three(cursor.getString(cursor.getColumnIndex("dishes_three")));
                menu.setTwo_dishes_four(cursor.getString(cursor.getColumnIndex("dishes_four")));
                dish_2_1.setText(menu.getTwo_dishes_one());
                dish_2_2.setText(menu.getTwo_dishes_two());
                dish_2_3.setText(menu.getTwo_dishes_three());
                dish_2_4.setText(menu.getTwo_dishes_four());
                if (!cursor.getString(cursor.getColumnIndex("soup")).equals(menu.getOne_soup())) {
                    menu.setTwo_soup(cursor.getString(cursor.getColumnIndex("soup")));
                    soup_layout_two.setVisibility(View.VISIBLE);
                    soup_2.setText(menu.getTwo_soup());
                }
            }
            db.close();
        }
    }

    private void getChoice(String thisDate, String user) {
        DBServerForBookings db = new DBServerForBookings(getContext());
        db.open();
        Cursor cursor = db.selectByDay(thisDate, user);
        if (cursor.moveToNext()) {
            booking.setMenu(cursor.getString(cursor.getColumnIndex("menu")));
            if (booking.getMenu().equals(menu.getMenu_one())) {
                clicks_one = true;
                menu_one.setBackgroundColor(Color.parseColor("#ffa800"));
            } else {
                clicks_two = true;
                menu_two.setBackgroundColor(Color.parseColor("#ffa800"));
            }
            booking.setSoup(cursor.getString(cursor.getColumnIndex("soup")));
            if (menu.getOne_soup() != null) {
                if (booking.getSoup().equals(menu.getOne_soup())) {
                    clicks_soup_one = true;
                    soup_layout_one.setBackgroundColor(Color.parseColor("#ffa800"));
                } else if (menu.getTwo_soup() != null) {
                    if (booking.getSoup().equals(menu.getTwo_soup())) {
                        clicks_soup_two = true;
                        soup_layout_two.setBackgroundColor(Color.parseColor("#ffa800"));
                    }
                }
            }
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

    private void confirm() {
        booking.setDate(menu.getDate());
        if (clicks_one) {
            booking.setMenu(menu.getMenu_one());
            booking.setDishes_one(menu.getOne_dishes_one());
            booking.setDishes_two(menu.getOne_dishes_two());
            booking.setDishes_three(menu.getOne_dishes_three());
            booking.setDishes_four(menu.getOne_dishes_four());
        } else if (clicks_two) {
            booking.setMenu(menu.getMenu_two());
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
        if (dbBooking.update(booking.getDate(), booking.getMenu(), booking.getUser(), booking.getDishes_one(), booking.getDishes_two(), booking.getDishes_three(), booking.getDishes_four(), booking.getSoup(), null)) {
            Toast.makeText(getContext(), "Modify successfully !", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        }
        dbBooking.close();
    }

    private void init(View view) {
        date = view.findViewById(R.id.edit_order_date);
        dish_1_1 = view.findViewById(R.id.edit_order_dish_1_1);
        dish_1_2 = view.findViewById(R.id.edit_order_dish_1_2);
        dish_1_3 = view.findViewById(R.id.edit_order_dish_1_3);
        dish_1_4 = view.findViewById(R.id.edit_order_dish_1_4);
        dish_2_1 = view.findViewById(R.id.edit_order_dish_2_1);
        dish_2_2 = view.findViewById(R.id.edit_order_dish_2_2);
        dish_2_3 = view.findViewById(R.id.edit_order_dish_2_3);
        dish_2_4 = view.findViewById(R.id.edit_order_dish_2_4);
        soup_1 = view.findViewById(R.id.edit_order_soup_one);
        soup_2 = view.findViewById(R.id.edit_order_soup_two);
        menu_one = view.findViewById(R.id.edit_order_menu_one);
        menu_two = view.findViewById(R.id.edit_order_menu_two);
        soup_layout_one = view.findViewById(R.id.edit_order_layout_soup_one);
        soup_layout_two = view.findViewById(R.id.edit_order_layout_soup_two);
        confirm = view.findViewById(R.id.edit_order_confirm);
        back = view.findViewById(R.id.edit_order_return);
    }

}
