package com.william.reservationsystem.view.adapter.HomeMaster;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.william.reservationsystem.R;
import com.william.reservationsystem.context.MyApplication;
import com.william.reservationsystem.model.DataDailyMenu;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private Context context;
    private List<DataDailyMenu> dataDailyMenuList;
    private LayoutInflater mInflater;
    private OnScrollListener mOnScrollListener;

    public RecyclerAdapter(Context context, List<DataDailyMenu> dataDailyMenusList) {
        this.context = context;
        this.dataDailyMenuList = dataDailyMenusList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        switch (i) {
            case DataDailyMenu.PARENT_ITEM:
                view = mInflater.inflate(R.layout.recycleview_item_parent, viewGroup, false);
                return new ParentViewHolder(context, view);
            case DataDailyMenu.CHILD_ITEM:
                view = mInflater.inflate(R.layout.recycleview_item_child, viewGroup, false);
                return new ChildViewHolder(context, view);
            default:
                view = mInflater.inflate(R.layout.recycleview_item_parent, viewGroup, false);
                return new ParentViewHolder(context, view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        switch (getItemViewType(i)) {
            case DataDailyMenu.PARENT_ITEM:
                ParentViewHolder parentViewHolder = (ParentViewHolder) baseViewHolder;
                parentViewHolder.bindView(dataDailyMenuList.get(i), i, itemClickListener);
                break;
            case DataDailyMenu.CHILD_ITEM:
                ChildViewHolder childViewHolder = (ChildViewHolder) baseViewHolder;
                childViewHolder.bindView(dataDailyMenuList.get(i), i);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dataDailyMenuList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return dataDailyMenuList.get(position).getType();
    }

    private ItemClickListener itemClickListener = new ItemClickListener() {
        @Override
        public void onExpandChildren(DataDailyMenu data) {
            int position = getCurrenPosition(data.getID());
            DataDailyMenu child = getChildDataDailyMenu(data);
            if (child == null) {
                return;
            }
            add(child, position + 1);
            if (position == dataDailyMenuList.size() - 2 && mOnScrollListener != null) {
                mOnScrollListener.scrollTo(position + 1);
            }
        }

        @Override
        public void onHideChildren(DataDailyMenu data) {
            int position = getCurrenPosition(data.getID());
            DataDailyMenu child = data.getChildMenu();
            if (child == null) {
                return;
            }
            remove(position + 1);
            if (mOnScrollListener != null) {
                mOnScrollListener.scrollTo(position);
            }
        }
    };

    public void add(DataDailyMenu dataDailyMenu, int position) {
        dataDailyMenuList.add(position, dataDailyMenu);
        notifyItemInserted(position);
    }

    protected void remove(int position) {
        dataDailyMenuList.remove(position);
        notifyItemRemoved(position);
    }

    protected int getCurrenPosition(String uid) {
        for (int i = 0; i < dataDailyMenuList.size(); i++) {
            if (uid.equalsIgnoreCase(dataDailyMenuList.get(i).getID())) {
                return i;
            }
        }
        return -1;
    }

    private DataDailyMenu getChildDataDailyMenu(DataDailyMenu data) {
        DataDailyMenu child = new DataDailyMenu();
        child.setType(1);
        child.setChildOneDis_one(data.getChildOneDis_one());
        child.setChildOneDis_two(data.getChildOneDis_two());
        child.setChildOneDis_three(data.getChildOneDis_three());
        child.setChildOneDis_four(data.getChildOneDis_four());
        child.setChildOne_Soup(data.getChildOne_Soup());
        child.setChildTwoDis_one(data.getChildTwoDis_one());
        child.setChildTwoDis_two(data.getChildTwoDis_two());
        child.setChildTwoDis_three(data.getChildTwoDis_three());
        child.setChildTwoDis_four(data.getChildTwoDis_four());
        child.setChildTwo_Soup(data.getChildTwo_Soup());
        child.setTwoView(data.isTwoView());
        return child;
    }

    public interface OnScrollListener {
        void scrollTo(int pos);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }
}
