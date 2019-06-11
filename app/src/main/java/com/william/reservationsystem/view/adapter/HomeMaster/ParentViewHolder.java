package com.william.reservationsystem.view.adapter.HomeMaster;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.william.reservationsystem.R;
import com.william.reservationsystem.model.DataDailyMenu;

/**
 * 父布局ViewHolder
 */
public class ParentViewHolder extends BaseViewHolder {

    private Context context;
    private View view;
    private RelativeLayout containerLayout;
    private TextView date, count;
    private ImageButton edit;
    private ImageView expand;
    private View parentDashedView;

    public ParentViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        this.view = itemView;
    }

    public void bindView(final DataDailyMenu dataDailyMenu, final int pos, final ItemClickListener listener) {
        containerLayout = view.findViewById(R.id.parent_container);
        date = view.findViewById(R.id.parentDate);
        count = view.findViewById(R.id.parent_CountData);
        edit = view.findViewById(R.id.parentEdit);
        expand = view.findViewById(R.id.img_expend);
        parentDashedView = view.findViewById(R.id.parent_dashed_view);

        date.setText(dataDailyMenu.getDate());

        if (dataDailyMenu.isExpand()) {
            expand.setRotation(90);
            parentDashedView.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
        } else {
            expand.setRotation(0);
            parentDashedView.setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE);
        }

        containerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    if (dataDailyMenu.isExpand()) {
                        listener.onHideChildren(dataDailyMenu);
                        parentDashedView.setVisibility(View.VISIBLE);
                        edit.setVisibility(View.GONE);
                        dataDailyMenu.setExpand(false);
                        rotationExpandIcon(90, 0);
                        containerLayout.setBackgroundResource(0);
                    } else {
                        listener.onExpandChildren(dataDailyMenu);
                        parentDashedView.setVisibility(View.INVISIBLE);
                        dataDailyMenu.setExpand(true);
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
