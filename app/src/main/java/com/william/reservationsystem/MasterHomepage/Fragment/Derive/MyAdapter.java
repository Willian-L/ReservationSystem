package com.william.reservationsystem.MasterHomepage.Fragment.Derive;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.william.reservationsystem.R;

import java.io.File;
import java.util.List;

class MyAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Bitmap mIconBackRoot;
    private Bitmap mIconBackUp;
    private Bitmap mIconFilePage;
    private Bitmap mIconFile;
    private List<String> items;
    private List<String> paths;

    public MyAdapter(Context context, List<String> items, List<String> paths) {
        mInflater = LayoutInflater.from(context);
        this.items = items;
        this.paths = paths;

        mIconBackRoot = BitmapFactory.decodeResource(context.getResources(), R.drawable.back_root);
        mIconBackUp = BitmapFactory.decodeResource(context.getResources(), R.drawable.back_up);
        mIconFilePage = BitmapFactory.decodeResource(context.getResources(), R.drawable.file_page);
        mIconFile = BitmapFactory.decodeResource(context.getResources(), R.drawable.file);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.file_row, null);
            holder = new ViewHolder();
            holder.text = convertView.findViewById(R.id.seleTxt_row);
            holder.icon = convertView.findViewById(R.id.seleImg_icon);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        File file = new File(paths.get(position).toString());
        if (items.get(position).toString().equals("back_root")) {
            holder.text.setText("Back to root..");
            holder.icon.setImageBitmap(mIconBackRoot);
        } else if (items.get(position).toString().equals("back_up")) {
            holder.text.setText("Return to previous directory..");
            holder.icon.setImageBitmap(mIconBackUp);
        } else {
            holder.text.setText(file.getName());
            if (file.isDirectory()) {
                holder.icon.setImageBitmap(mIconFilePage);
            } else {
                holder.icon.setImageBitmap(mIconFile);
            }
        }
        return null;
    }

    private class ViewHolder {
        TextView text;
        ImageView icon;
    }
}