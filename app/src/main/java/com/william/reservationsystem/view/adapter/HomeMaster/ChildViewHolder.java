package com.william.reservationsystem.view.adapter.HomeMaster;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.william.reservationsystem.R;
import com.william.reservationsystem.model.DataDailyMenu;

public class ChildViewHolder extends BaseViewHolder {
    private Context context;
    private View view;
    private RelativeLayout menu_one, menu_two;
    private TextView one_disOne, one_disTwo, one_disThree, one_disFour, one_soup,
            two_disOne, two_disTwo, two_disThree, two_disFour, two_soup,
            one_count, two_count, one_detail, two_detail;

    public ChildViewHolder(Context context, @NonNull View itemView) {
        super(itemView);
        this.context = context;
        this.view = itemView;
    }

    public void bindView(final DataDailyMenu dataDailyMenu, final int pos) {
        menu_one = view.findViewById(R.id.menu_one);
        menu_two = view.findViewById(R.id.menu_two);
        one_disOne = view.findViewById(R.id.one_Dishes_one);
        one_disTwo = view.findViewById(R.id.one_Dishes_two);
        one_disThree = view.findViewById(R.id.one_Dishes_three);
        one_disFour = view.findViewById(R.id.one_Dishes_four);
        one_soup = view.findViewById(R.id.one_Soup);
        one_count = view.findViewById(R.id.menu_one_count);
        one_detail = view.findViewById(R.id.one_detail);
        two_disOne = view.findViewById(R.id.two_Dishes_one);
        two_disTwo = view.findViewById(R.id.two_Dishes_two);
        two_disThree = view.findViewById(R.id.two_Dishes_three);
        two_disFour = view.findViewById(R.id.two_Dishes_four);
        two_soup = view.findViewById(R.id.two_Soup);
        two_count = view.findViewById(R.id.menu_two_count);
        two_detail = view.findViewById(R.id.two_detail);

        one_detail.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        two_detail.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }
}
