package com.william.reservationsystem.view.adapter.HomeMaster;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.william.reservationsystem.R;
import com.william.reservationsystem.controller.MasterHomepage.EditMenuActivity;
import com.william.reservationsystem.controller.MasterHomepage.HomepageForMasterActivity;
import com.william.reservationsystem.model.DBServerForM;
import com.william.reservationsystem.model.DBServerForMenu;
import com.william.reservationsystem.model.DataDailyMenu;

/**
 * 父布局ViewHolder
 */
public class ParentViewHolder extends BaseViewHolder {

    private Context context;
    private View view;
    private RelativeLayout containerLayout;
    public TextView date, count;
    public ImageButton edit, delete;
    private ImageView expand;
    private View parentDashedView;

    public ParentViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.view = itemView;
    }

    String item_date;

    public void bindView(final DataDailyMenu dataDailyMenu, final int pos, final ItemClickListener listener) {
        containerLayout = view.findViewById(R.id.parent_container);
        date = view.findViewById(R.id.parentDate);
        count = view.findViewById(R.id.parent_CountData);
        edit = view.findViewById(R.id.parentEdit);
        expand = view.findViewById(R.id.img_expend);
        parentDashedView = view.findViewById(R.id.parent_dashed_view);
        delete = view.findViewById(R.id.parentDelete);

        date.setText(dataDailyMenu.getDate());

        Log.i("get", dataDailyMenu.getDate());

        if (dataDailyMenu.isExpand()) {
            expand.setRotation(90);
            parentDashedView.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
        } else {
            expand.setRotation(0);
            parentDashedView.setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
        }

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditMenuActivity.class);
                intent.putExtra("date",item_date);
                context.startActivity(intent);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBServerForMenu dbServerForMenu = new DBServerForMenu(context);
                dbServerForMenu.open();
                dbServerForMenu.delete(item_date);
                dbServerForMenu.close();
                Intent intent = new Intent(context,HomepageForMasterActivity.class);
                context.startActivity(intent);
            }
        });

        containerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (dataDailyMenu.isExpand()) {
                        listener.onHideChildren(dataDailyMenu);
                        parentDashedView.setVisibility(View.VISIBLE);
                        edit.setVisibility(View.GONE);
                        delete.setVisibility(View.GONE);
                        dataDailyMenu.setExpand(false);
                        rotationExpandIcon(90, 0);
                        containerLayout.setBackgroundResource(0);
                    } else {
                        listener.onExpandChildren(dataDailyMenu);
                        parentDashedView.setVisibility(View.INVISIBLE);
                        edit.setVisibility(View.VISIBLE);
                        delete.setVisibility(View.VISIBLE);
                        dataDailyMenu.setExpand(true);
                        item_date = date.getText().toString().trim();
                        rotationExpandIcon(0, 90);
                        containerLayout.setBackgroundResource(R.drawable.bg_menu_item);
                    }
                }
            }
        });
    }

    private void rotationExpandIcon(float from, float to) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            final ValueAnimator valueAnimator = ValueAnimator.ofFloat(from, to);  //Animator
            valueAnimator.setDuration(500);
            valueAnimator.setInterpolator(new DecelerateInterpolator());
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    expand.setRotation((Float) valueAnimator.getAnimatedValue());
                }
            });
            valueAnimator.start();
        }
    }
}
