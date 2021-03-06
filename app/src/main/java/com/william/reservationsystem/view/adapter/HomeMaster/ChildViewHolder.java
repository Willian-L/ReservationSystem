package com.william.reservationsystem.view.adapter.HomeMaster;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.william.reservationsystem.R;
import com.william.reservationsystem.controller.MasterHomepage.Detail.DetailActivity;
import com.william.reservationsystem.model.DataDailyMenu;

public class ChildViewHolder extends BaseViewHolder {
    public Context context;
    public View view;
    public RelativeLayout menu_one, menu_two, menu_view;
    public TextView one_disOne, one_disTwo, one_disThree, one_disFour, one_soup,
            two_disOne, two_disTwo, two_disThree, two_disFour, two_soup,
            one_count, two_count, one_soup_count, two_soup_count;

    public ChildViewHolder(Context context, @NonNull View itemView) {
        super(itemView);
        this.context = context;
        this.view = itemView;
    }

    public void bindView(final DataDailyMenu dataDailyMenu, final int pos) {
        menu_one = view.findViewById(R.id.menu_one);

        menu_view = view.findViewById(R.id.menu_view);
        one_disOne = view.findViewById(R.id.one_Dishes_one);
        one_disTwo = view.findViewById(R.id.one_Dishes_two);
        one_disThree = view.findViewById(R.id.one_Dishes_three);
        one_disFour = view.findViewById(R.id.one_Dishes_four);
        one_soup = view.findViewById(R.id.one_Soup);
        one_count = view.findViewById(R.id.menu_one_count);
        one_soup_count = view.findViewById(R.id.menu_one_soup_count);
        menu_two = view.findViewById(R.id.menu_two);
        two_disOne = view.findViewById(R.id.two_Dishes_one);
        two_disTwo = view.findViewById(R.id.two_Dishes_two);
        two_disThree = view.findViewById(R.id.two_Dishes_three);
        two_disFour = view.findViewById(R.id.two_Dishes_four);
        two_soup = view.findViewById(R.id.two_Soup);
        two_count = view.findViewById(R.id.menu_two_count);
        two_soup_count = view.findViewById(R.id.menu_two_soup_count);
        two_count = view.findViewById(R.id.menu_two_count);

        one_disOne.setText(dataDailyMenu.getChildOneDis_one());
        one_disTwo.setText(dataDailyMenu.getChildOneDis_two());
        one_disThree.setText(dataDailyMenu.getChildOneDis_three());
        one_disFour.setText(dataDailyMenu.getChildOneDis_four());
        one_soup.setText(dataDailyMenu.getChildOne_Soup());
        one_count.setText(dataDailyMenu.getChildOneCount());
        one_soup_count.setText(dataDailyMenu.getChildOneSoupCount());
        two_disOne.setText(dataDailyMenu.getChildTwoDis_one());
        two_disTwo.setText(dataDailyMenu.getChildTwoDis_two());
        two_disThree.setText(dataDailyMenu.getChildTwoDis_three());
        two_disFour.setText(dataDailyMenu.getChildTwoDis_four());
        two_soup.setText(dataDailyMenu.getChildTwo_Soup());
        two_count.setText(dataDailyMenu.getChildTwoCount());
        two_soup_count.setText(dataDailyMenu.getChildTwoSoupCount());

        if (dataDailyMenu.isTwoView()) {
            menu_two.setVisibility(View.VISIBLE);
        } else {
            menu_two.setVisibility(View.GONE);
        }

        one_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!one_count.getText().equals("0")) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("date", dataDailyMenu.getDate());
                    bundle.putString("menu", dataDailyMenu.getCHILD_MENU_ONE());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });

        two_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!two_count.getText().equals("0")) {
                    Intent intent = new Intent(context, DetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("date", dataDailyMenu.getDate());
                    bundle.putString("menu", dataDailyMenu.getCHILD_MENU_TWO());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });

//        if (dataDailyMenu.getChildTwoDis_one() != null){
//            menu_item = view.findViewById(R.id.view_item);
//            LayoutInflater inflater = LayoutInflater.from(context);
//            RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.menu_child_view,null);
//            menu_item.addView(view);
//        }
    }
}
