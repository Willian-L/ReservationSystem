package com.william.reservationsystem.controller.MasterHomepage.Fragment;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.william.reservationsystem.R;
import com.william.reservationsystem.model.DBServerForMenu;
import com.william.reservationsystem.model.DataDailyMenu;
import com.william.reservationsystem.model.Menus;
import com.william.reservationsystem.view.adapter.HomeMaster.ChildViewHolder;
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
        while (cursor.moveToNext()) {
            dataDailyMenu = new DataDailyMenu();
            dataDailyMenu.setID(i + "");
            dataDailyMenu.setType(0);
            menu.setDate(cursor.getString(cursor.getColumnIndex("day")));
            menu.setOne_dishes_one(cursor.getString(cursor.getColumnIndex("dishes_one")));
            menu.setOne_dishes_two(cursor.getString(cursor.getColumnIndex("dishes_two")));
            menu.setOne_dishes_three(cursor.getString(cursor.getColumnIndex("dishes_three")));
            menu.setOne_dishes_four(cursor.getString(cursor.getColumnIndex("dishes_four")));
            menu.setOne_soup(cursor.getString(cursor.getColumnIndex("soup")));
            dataDailyMenu.setDate(menu.getDate());
            if (cursor.moveToNext()) {
                if (menu.getDate().equals(cursor.getString(cursor.getColumnIndex("day")))) {
                    menu.setTwo_dishes_one(cursor.getString(cursor.getColumnIndex("dishes_one")));
                    menu.setTwo_dishes_two(cursor.getString(cursor.getColumnIndex("dishes_two")));
                    menu.setTwo_dishes_three(cursor.getString(cursor.getColumnIndex("dishes_three")));
                    menu.setTwo_dishes_four(cursor.getString(cursor.getColumnIndex("dishes_four")));
                    menu.setTwo_soup(cursor.getString(cursor.getColumnIndex("soup")));
                } else {
                    cursor.moveToPrevious();
                }
            }
            dataDailyMenu.setChildOneDis_one(menu.getOne_dishes_one());
            dataDailyMenu.setChildOneDis_two(menu.getOne_dishes_two());
            dataDailyMenu.setChildOneDis_three(menu.getOne_dishes_three());
            dataDailyMenu.setChildOneDis_four(menu.getOne_dishes_four());
            dataDailyMenu.setChildOne_Soup(menu.getOne_soup());
            dataDailyMenu.setChildMenu(dataDailyMenu);
            dataDailyMenuList.add(dataDailyMenu);
            ++i;
        }
        setData();
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
}
