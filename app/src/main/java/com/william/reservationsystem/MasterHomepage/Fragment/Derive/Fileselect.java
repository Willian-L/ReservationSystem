package com.william.reservationsystem.MasterHomepage.Fragment.Derive;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.william.reservationsystem.MasterHomepage.Fragment.UserInfoFragment;
import com.william.reservationsystem.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Fileselect extends ListActivity {

    private TextView txtPath;
    private Button btnConfirm, btnCancle;

    private List<String> items = null;
    private List<String> paths = null;
    private String rootPath = "/";
    private String curPath = "/";

    @Override
    protected void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fileselect);

        inti();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(Fileselect.this, UserInfoFragment.class);
                Bundle bundle = new Bundle();
                bundle.putString("file", curPath);
                data.putExtras(bundle);
                setResult(2, data);
                finish();
            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getFileDir(rootPath);
    }

    private void getFileDir(String filePath) {
        txtPath.setText(filePath);
        items = new ArrayList<String>();
        paths = new ArrayList<String>();
        File f = new File(filePath);
        File[] files = f.listFiles();
        if (!filePath.equals(rootPath)) {
            items.add("back_root");
            paths.add(rootPath);
            items.add("back_up");
            paths.add(f.getParent());
        }
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            items.add(file.getName());
            paths.add(file.getPath());
        }
        setListAdapter(new MyAdapter(this, items, paths));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        File file = new File(paths.get(position));
        if (file.isDirectory()) {
            curPath = paths.get(position);
            getFileDir(paths.get(position));
        }
    }

    private void inti() {
        txtPath = findViewById(R.id.seleTxt_Path);
        btnConfirm = findViewById(R.id.seleBtn_yes);
        btnCancle = findViewById(R.id.seleBtn_no);
    }
}
