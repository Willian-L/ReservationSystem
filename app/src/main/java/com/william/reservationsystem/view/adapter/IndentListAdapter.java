package com.william.reservationsystem.view.adapter;

import android.content.Context;
import android.database.Cursor;
import android.widget.SimpleCursorAdapter;

public class IndentListAdapter extends SimpleCursorAdapter {
    public IndentListAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
    }

    @Override
    public boolean isEnabled(int position) {
        boolean click = false;
        if (position == 0) {
            click = true;
        }
        return click;
    }
}
