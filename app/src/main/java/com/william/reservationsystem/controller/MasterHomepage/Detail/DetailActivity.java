package com.william.reservationsystem.controller.MasterHomepage.Detail;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.william.reservationsystem.R;
import com.william.reservationsystem.model.Bookings;
import com.william.reservationsystem.model.DBServerForBookings;
import com.william.reservationsystem.model.DBServerForMenu;
import com.william.reservationsystem.model.Menus;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private List<DetailData> mydate;

    Bookings booking = new Bookings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            booking.setDate(bundle.getString("date"));
            booking.setMenu(bundle.getString("menu"));

            select();
        }

    }

    private void select(){
        mydate = new ArrayList<DetailData>();


        DBServerForMenu dbMenu = new DBServerForMenu(this);
        dbMenu.open();
        String soup_1 = null;
        String soup_2 = null;
        Menus menu = new Menus();
        Cursor soup_cursor_1 = dbMenu.selectSoup(booking.getDate(), menu.getMENU_ONE());
        Cursor soup_cursor_2 = dbMenu.selectSoup(booking.getDate(), menu.getMENU_TWO());
        if (soup_cursor_1.getCount() > 0){
            soup_cursor_1.moveToNext();
            soup_1 = soup_cursor_1.getString(soup_cursor_1.getColumnIndex("soup"));
        }
        if (soup_cursor_2.getCount() > 0){
            soup_cursor_2.moveToNext();
            soup_2 = soup_cursor_2.getString(soup_cursor_2.getColumnIndex("soup"));
        }
        dbMenu.close();

        DBServerForBookings dbBooking = new DBServerForBookings(this);
        dbBooking.open();

        Cursor cursor = dbBooking.selectDetail(booking.getDate(), booking.getMenu());
        int number = 1;
        while (cursor.moveToNext()) {
            DetailData data = new DetailData();
            data.no = String.valueOf(number);
            number++;
            data.name = cursor.getString(cursor.getColumnIndex("user"));
            String soup = cursor.getString(cursor.getColumnIndex("soup"));
            if (soup.equals(soup_1) && !soup.equals("")) {
                data.have_soup = "1";
            } else if (soup.equals(soup_2) && !soup.equals("")){
                data.have_soup = "2";
            } else {
                data.have_soup = "null";
            }
            mydate.add(data);
        }
        dbBooking.close();

        final DetailListAdapter adapter = new DetailListAdapter();
        final ListView lv = findViewById(R.id.lv_detail);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DetailActivity.this);
                dialog.setTitle("What do you want to do ?");
                dialog.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(DetailActivity.this);
                        deleteDialog.setTitle("Do you want to delete this reservation ?");
                        deleteDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TextView user = view.findViewById(R.id.lv_item_txt_name);
                                DBServerForBookings db = new DBServerForBookings(DetailActivity.this);
                                db.open();
                                if (db.delete(booking.getDate(), user.getText().toString().trim())) {
                                    mydate.remove(position);
                                    adapter.notifyDataSetChanged();
                                    lv.invalidate();
                                }
                            }
                        });
                        deleteDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        deleteDialog.show();
                    }
                });
                dialog.setPositiveButton("Modify", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView user = view.findViewById(R.id.lv_item_txt_name);
                        Bundle bundle = new Bundle();
                        bundle.putString("username", user.getText().toString().trim());
                        bundle.putString("date", booking.getDate());
                        Intent intentToM = new Intent(DetailActivity.this, ModifyMenuActivity.class);
                        intentToM.putExtras(bundle);
                        startActivity(intentToM);
                    }
                });
                dialog.show();
                return true;
            }
        });
    }

    public void back_Edit(View view) {
        this.finish();
    }

    class DetailListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mydate.size();
        }

        @Override
        public Object getItem(int position) {
            return mydate.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_detail_item, null);
            TextView no = itemView.findViewById(R.id.lv_item_txt_no);
            no.setText(mydate.get(position).no);
            TextView name = itemView.findViewById(R.id.lv_item_txt_name);
            name.setText(mydate.get(position).name);
            ImageView soup1 = itemView.findViewById(R.id.lv_item_img_haveSoup_1);
            ImageView soup2 = itemView.findViewById(R.id.lv_item_img_haveSoup_2);
            String have_soup = mydate.get(position).have_soup;
            if (have_soup.equals("1")) {
                soup1.setVisibility(View.VISIBLE);
                soup2.setVisibility(View.GONE);
            } else if (have_soup.equals("2")){
                soup2.setVisibility(View.VISIBLE);
                soup1.setVisibility(View.GONE);
            }else {
                soup1.setVisibility(View.GONE);
                soup2.setVisibility(View.GONE);
            }
            return itemView;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        select();
    }
}
