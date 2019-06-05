package com.william.reservationsystem.controller.MasterHomepage.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.william.reservationsystem.R;
import com.william.reservationsystem.model.DataDailyMenu;
import com.william.reservationsystem.view.adapter.HomeMaster.RecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class DailyMenuFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private List<DataDailyMenu> dataDailyMenuList;
    private DataDailyMenu dataDailyMenu;
    private RecyclerAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_menu, container, false);
        mRecyclerView = view.findViewById(R.id.recycle_view);
        initData();
        return view;
    }

    private void initData(){
        dataDailyMenuList = new ArrayList<>();
        for (int i = 1; i <= 50; i++){
            dataDailyMenu = new DataDailyMenu();
            dataDailyMenu.setID(i+"");
            dataDailyMenu.setType(0);
            dataDailyMenu.setChildMenu(dataDailyMenu);
            dataDailyMenuList.add(dataDailyMenu);
        }
        setData();
    }

    private void setData(){
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new RecyclerAdapter(getContext(),dataDailyMenuList);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnScrollListener(new RecyclerAdapter.OnScrollListener() {
            @Override
            public void scrollTo(int pos) {
                mRecyclerView.scrollToPosition(pos);
            }
        });
    }
}
