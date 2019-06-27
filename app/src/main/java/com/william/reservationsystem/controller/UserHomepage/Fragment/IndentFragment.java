package com.william.reservationsystem.controller.UserHomepage.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.william.reservationsystem.R;
import com.william.reservationsystem.model.Bookings;
import com.william.reservationsystem.model.DBServerForBookings;
import com.william.reservationsystem.model.DBServerForMenu;
import com.william.reservationsystem.model.DBServerForU;
import com.william.reservationsystem.view.adapter.IndentListAdapter;

public class IndentFragment extends Fragment {

    ListView lvInfo;
    DBServerForBookings DBBookings;
    DBServerForMenu DBMenu;
    Bundle bundle;
    RelativeLayout have_not;

    Bookings booking = new Bookings();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_indent, container, false);

        lvInfo = view.findViewById(R.id.lv_dailyMenu);
        have_not = view.findViewById(R.id.have_not_history);

        DBBookings = new DBServerForBookings(getContext());
        DBMenu = new DBServerForMenu(getContext());

        bundle = this.getArguments();
        if (bundle != null) {
            String user = bundle.getString("username");
            DBServerForU db = new DBServerForU(getContext());
            db.open();
            Cursor cursor = db.selectByUsername(user);
            while (cursor.moveToNext()) {
                booking.setUser(cursor.getString(cursor.getColumnIndex("name")));
            }
            db.close();
        }

        seleteMenu();

        lvInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bundle.putString("username", booking.getUser());
                DetailedMenuFragment detailedMenu = new DetailedMenuFragment();
                detailedMenu.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.user_content_Layout, detailedMenu).addToBackStack(null).commit();
            }
        });

        lvInfo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        return view;
    }

    private void seleteMenu() {
        DBBookings.open();
        Cursor curBooking = DBBookings.selectByUser(booking.getUser());
        if (curBooking.getCount() > 0) {
            lvInfo.setVisibility(View.VISIBLE);
            have_not.setVisibility(View.GONE);
            IndentListAdapter Adapter = new IndentListAdapter(getContext(),
                    R.layout.listview_indent_item,
                    curBooking,
                    new String[]{"day", "dishes_one", "dishes_two", "dishes_three", "dishes_four", "soup"},
                    new int[]{R.id.history_date, R.id.history_dish_1, R.id.history_dish_2, R.id.history_dish_3, R.id.history_dish_4, R.id.history_soup},
                    CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
            lvInfo.setAdapter(Adapter);
            lvInfo.invalidateViews();
        } else {
            have_not.setVisibility(View.VISIBLE);
            lvInfo.setVisibility(View.GONE);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            seleteMenu();
        }
    }
}
