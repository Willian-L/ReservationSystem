package com.william.reservationsystem.UI;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.william.reservationsystem.R;
import com.william.reservationsystem.Fragment.UserOrderFragment;

public class HomepageForUActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView topBar;
    private TextView tabOrder;
    private TextView tabShopping;
    private TextView tabMy;

    private FrameLayout ly_content;

    private UserOrderFragment f1, f2, f3, f4;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_for_u);

        bindView();
    }

    private void bindView() {
        topBar = findViewById(R.id.txt_top);
        tabOrder = findViewById(R.id.txt_order);
        tabShopping = findViewById(R.id.txt_shopping);
        tabMy = findViewById(R.id.txt_mine);
        ly_content = findViewById(R.id.fragment_container);

        tabOrder.setOnClickListener(this);
        tabShopping.setOnClickListener(this);
        tabMy.setOnClickListener(this);
    }

    public void selected() {
        tabOrder.setSelected(false);
        tabShopping.setSelected(false);
        tabMy.setSelected(false);
    }

    public void hideAllFragment(FragmentTransaction transaction) {
        if (f1 != null) {
            transaction.hide(f1);
        }
        if (f2 != null) {
            transaction.hide(f2);
        }
        if (f3 != null) {
            transaction.hide(f3);
        }
        if (f4 != null) {
            transaction.hide(f4);
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        switch (v.getId()) {
            case R.id.txt_order:
                selected();
                tabOrder.setSelected(true);
                if (f1 == null) {
                    f1 = new UserOrderFragment();
                    fragmentTransaction.add(R.id.fragment_container, f1);
                } else {
                    fragmentTransaction.show(f1);
                }
                break;

            case R.id.txt_shopping:
                selected();
                tabShopping.setSelected(true);
                if (f2 == null) {
                    f2 = new UserOrderFragment();
                    fragmentTransaction.add(R.id.fragment_container, f2);
                } else {
                    fragmentTransaction.show(f2);
                }
                break;

            case R.id.txt_mine:
                selected();
                tabMy.setSelected(true);
                if (f3 == null) {
                    f3 = new UserOrderFragment();
                    fragmentTransaction.add(R.id.fragment_container, f3);
                } else {
                    fragmentTransaction.show(f3);
                }
                break;
        }

        fragmentTransaction.commit();
    }
}

