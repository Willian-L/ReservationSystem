package com.william.reservationsystem.view.adapter.HomeMaster;

import com.william.reservationsystem.model.DataDailyMenu;

/**
 * 父布局Item点击监听接口
 */

public interface ItemClickListener {
    /**
     * 展开子Item
     * @param data
     */
    void onExpandChildren(DataDailyMenu data);

    /**
     * 隐藏子Item
     * @param data
     */
    void onHideChildren(DataDailyMenu data);
}
