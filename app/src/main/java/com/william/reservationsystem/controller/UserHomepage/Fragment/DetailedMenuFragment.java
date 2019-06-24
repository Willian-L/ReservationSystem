package com.william.reservationsystem.controller.UserHomepage.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.TextUtils;
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

public class DetailedMenuFragment extends Fragment {

    TextView date, dish_1,dish_2,dish_3,dish_4,soup;
    Button edit, delete;
    LinearLayout soup_layout;

    Bookings booking = new Bookings();

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today_menu, container, false);

        init(view);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            booking.setUser(bundle.getString("username"));
        }

        getMenu();
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("date", booking.getDate());
                bundle.putString("username", booking.getUser());
                ModifyOrderFragment editOrder = new ModifyOrderFragment();
                editOrder.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.user_content_Layout, editOrder).addToBackStack(null).commit();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDB();
            }
        });

        return view;
    }

    private void deleteDB(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Do you want to cancel the reservation?");
        dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBServerForBookings db = new DBServerForBookings(getContext());
                db.open();
                if (db.delete(booking.getDate(),booking.getUser())){
                    Toast.makeText(getContext(),"Deleted successfully!",Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
                db.close();
            }
        });
        dialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    private void getMenu(){
        DBServerForBookings db = new DBServerForBookings(getContext());
        db.open();
        Cursor cursor = db.selectByUser(booking.getUser());
        if (cursor.moveToNext()){
            booking.setDate(cursor.getString(cursor.getColumnIndex("day")));
            booking.setDishes_one(cursor.getString(cursor.getColumnIndex("dishes_one")));
            booking.setDishes_two(cursor.getString(cursor.getColumnIndex("dishes_two")));
            booking.setDishes_three(cursor.getString(cursor.getColumnIndex("dishes_three")));
            booking.setDishes_four(cursor.getString(cursor.getColumnIndex("dishes_four")));
            booking.setSoup(cursor.getString(cursor.getColumnIndex("soup")));
            date.setText(booking.getDate());
            dish_1.setText(booking.getDishes_one());
            dish_2.setText(booking.getDishes_two());
            dish_3.setText(booking.getDishes_three());
            dish_4.setText(booking.getDishes_four());
            soup.setText(booking.getSoup());
        }
        if (!TextUtils.isEmpty(soup.getText().toString())){
            soup_layout.setVisibility(View.VISIBLE);
        }
        db.close();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getMenu();
        }
    }

    private void init(View view){
        date = view.findViewById(R.id.detail_date);
        dish_1 = view.findViewById(R.id.detail_dish_1);
        dish_2 = view.findViewById(R.id.detail_dish_2);
        dish_3 = view.findViewById(R.id.detail_dish_3);
        dish_4 = view.findViewById(R.id.detail_dish_4);
        soup = view.findViewById(R.id.detail_soup);
        edit = view.findViewById(R.id.edit_detail_menu);
        delete = view.findViewById(R.id.delete_detail_menu);
        soup_layout = view.findViewById(R.id.detail_soup_layout);
    }
}
