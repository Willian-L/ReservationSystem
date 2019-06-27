package com.william.reservationsystem.controller.MasterHomepage.Fragment;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.william.reservationsystem.R;
import com.william.reservationsystem.controller.MasterHomepage.ReleaseActivity;
import com.william.reservationsystem.model.DBServerForBookings;
import com.william.reservationsystem.model.DBServerForMenu;
import com.william.reservationsystem.model.DataDailyMenu;
import com.william.reservationsystem.model.Menus;
import com.william.reservationsystem.view.adapter.HomeMaster.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DailyMenuFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<DataDailyMenu> dataDailyMenuList;
    private DataDailyMenu dataDailyMenu;
    private RecyclerAdapter mAdapter;

    Menus menu = new Menus();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_menu, container, false);
        mRecyclerView = view.findViewById(R.id.recycle_view);
        initData();
        return view;
    }

    private void initData() {
        dataDailyMenuList = new ArrayList<>();
        DBServerForMenu db = new DBServerForMenu(getContext());
        int i = 1;
        db.open();
        Cursor cursor = db.select();
        menu.setDate("");
        DBServerForBookings dbBookings = new DBServerForBookings(getContext());
        dbBookings.open();
        if (cursor.moveToLast()) {
            do {
                if (!menu.getDate().equals(cursor.getString(cursor.getColumnIndex("day")))) {
                    dataDailyMenu = new DataDailyMenu();
                    dataDailyMenu.setID(i + "");
                    dataDailyMenu.setType(0);
                    menu.setDate(cursor.getString(cursor.getColumnIndex("day")));
                    menu.setOne_dishes_one(cursor.getString(cursor.getColumnIndex("dishes_one")));
                    menu.setOne_dishes_two(cursor.getString(cursor.getColumnIndex("dishes_two")));
                    menu.setOne_dishes_three(cursor.getString(cursor.getColumnIndex("dishes_three")));
                    menu.setOne_dishes_four(cursor.getString(cursor.getColumnIndex("dishes_four")));
                    menu.setOne_soup(cursor.getString(cursor.getColumnIndex("soup")));
                    Cursor oneCount = dbBookings.selectByMenu(menu.getDate(), menu.getMENU_ONE());
                    Cursor oneSoupCount = dbBookings.selectSoup(menu.getDate(), menu.getOne_soup());
                    dataDailyMenu.setDate(menu.getDate());
                    dataDailyMenu.setChildOneDis_one(menu.getOne_dishes_one());
                    dataDailyMenu.setChildOneDis_two(menu.getOne_dishes_two());
                    dataDailyMenu.setChildOneDis_three(menu.getOne_dishes_three());
                    dataDailyMenu.setChildOneDis_four(menu.getOne_dishes_four());
                    dataDailyMenu.setChildOne_Soup(menu.getOne_soup());
                    dataDailyMenu.setChildOneCount(String.valueOf(oneCount.getCount()));
                    dataDailyMenu.setChildOneSoupCount(String.valueOf(oneSoupCount.getCount()));
                    dataDailyMenu.setCount(dataDailyMenu.getChildOneCount());
                    dataDailyMenu.setSoupCount(dataDailyMenu.getChildOneSoupCount());
                    dataDailyMenu.setChildMenu(dataDailyMenu);
                    dataDailyMenu.setTwoView(false);
                    dataDailyMenuList.add(dataDailyMenu);
                    i++;
                } else {
                    menu.setTwo_dishes_one(cursor.getString(cursor.getColumnIndex("dishes_one")));
                    menu.setTwo_dishes_two(cursor.getString(cursor.getColumnIndex("dishes_two")));
                    menu.setTwo_dishes_three(cursor.getString(cursor.getColumnIndex("dishes_three")));
                    menu.setTwo_dishes_four(cursor.getString(cursor.getColumnIndex("dishes_four")));
                    menu.setTwo_soup(cursor.getString(cursor.getColumnIndex("soup")));
                    Cursor twoCount = dbBookings.selectByMenu(menu.getDate(), menu.getMENU_TWO());
                    if (!menu.getOne_soup().equals(menu.getTwo_soup()) && !menu.getTwo_soup().equals("")) {
                        Cursor twoSoupCount = dbBookings.selectSoup(menu.getDate(), menu.getTwo_soup());
                        dataDailyMenu.setChildTwoSoupCount(String.valueOf(twoSoupCount.getCount()));
                        dataDailyMenu.setChildTwo_Soup(menu.getTwo_soup());
                        dataDailyMenu.setSoupCount(String.valueOf(Integer.parseInt(dataDailyMenu.getSoupCount())+twoSoupCount.getCount()));
                    }else {
                        dataDailyMenu.setChildTwo_Soup(menu.getOne_soup());
                    }
                    dataDailyMenu.setChildTwoDis_one(menu.getTwo_dishes_one());
                    dataDailyMenu.setChildTwoDis_two(menu.getTwo_dishes_two());
                    dataDailyMenu.setChildTwoDis_three(menu.getTwo_dishes_three());
                    dataDailyMenu.setChildTwoDis_four(menu.getTwo_dishes_four());
                    dataDailyMenu.setChildTwoCount(String.valueOf(twoCount.getCount()));
                    dataDailyMenu.setCount(String.valueOf(Integer.parseInt(dataDailyMenu.getCount())+twoCount.getCount()));
                    dataDailyMenu.setTwoView(true);
                    dataDailyMenu.setChildMenu(dataDailyMenu);
                }
            } while (cursor.moveToPrevious());
            setData();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void setData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerAdapter(getContext(), dataDailyMenuList);
        mRecyclerView.setAdapter(mAdapter);


        mAdapter.setOnScrollListener(new RecyclerAdapter.OnScrollListener() {
            @Override
            public void scrollTo(int pos) {
                mRecyclerView.scrollToPosition(pos);
            }
        });
    }

    public void toRelease(View view) {
        Intent intent = new Intent(getContext(), ReleaseActivity.class);
        startActivity(intent);
    }
}
