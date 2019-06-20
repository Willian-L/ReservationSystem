package com.william.reservationsystem.controller.UserHomepage.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.william.reservationsystem.R;
import com.william.reservationsystem.model.Bookings;
import com.william.reservationsystem.model.DBServerForBookings;
import com.william.reservationsystem.model.DBServerForMenu;
import com.william.reservationsystem.model.Menus;

public class ShoppingFragment extends Fragment {

    ListView lvInfo;
    DBServerForBookings DBBookings;
    DBServerForMenu DBMenu;
    Bundle bundle;

    Bookings booking = new Bookings();
    Menus menu = new Menus();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shopping, null);

        lvInfo = view.findViewById(R.id.lv_dailyMenu);

        DBBookings = new DBServerForBookings(getContext());
        DBMenu = new DBServerForMenu(getContext());

        bundle = this.getArguments();
        if (bundle != null) {
            booking.setUser(bundle.getString("username"));
        }

        seleteMenu();

        return view;
    }

    private void seleteMenu(){
        DBBookings.open();
        Cursor curBooking = DBBookings.selectByUser(booking.getUser());
        SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(getContext(),
                R.layout.listview_item,
                curBooking,
                new String[]{"day","dishes_one","dishes_two","dishes_three","dishes_four","soup"},
                new int[]{R.id.history_date,R.id.history_dish_1,R.id.history_dish_2,R.id.history_dish_3,R.id.history_dish_4,R.id.history_soup},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
       lvInfo.setAdapter(listAdapter);
       lvInfo.invalidateViews();
    }
}
