package com.william.reservationsystem.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.william.reservationsystem.R;
import com.william.reservationsystem.SQLite.DBServerForU;
import com.william.reservationsystem.SQLite.User;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyFragment extends Fragment {

    ImageView imgPhoto;
    ImageButton ibtn_edit, ibtn_camera, ibtn_album;
    EditText edtName, edtSex, edtPhone, edtEmail, edtAddress;
    Button btnModify, btnCancel;
    User user = new User();
    TextView txt_title;
    RadioGroup radSex;
    TextView txtAge;
    SeekBar sbAge;

    private File currentImageFile = null;

    public String sex = null;

    public String age = null;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_my, null);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            user.setUsername(bundle.getString("username"));
        }

        inti(view);

        txt_title.setText(user.getUsername() + "'s Information");

        DBServerForU dbServerForU = new DBServerForU(getContext());
        dbServerForU.open();
        Cursor cursor = dbServerForU.selectByUsername(user.getUsername());

        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                user.setName(cursor.getString(cursor.getColumnIndex("name")));
                edtName.setText(user.getName());
                user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
                edtSex.setText(user.getSex());
                user.setAge(cursor.getString(cursor.getColumnIndex("age")));
                txtAge.setText(user.getAge());
                user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                edtPhone.setText(user.getPhone());
                user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                edtEmail.setText(user.getEmail());
                user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                edtAddress.setText(user.getAddress());
            }
        }

        ibtn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String backcolor = "#1fffffFF";
                edtName.setEnabled(true);
                edtSex.setVisibility(View.GONE);
                radSex.setVisibility(View.VISIBLE);
                txtAge.setEnabled(true);
                edtPhone.setEnabled(true);
                edtEmail.setEnabled(true);
                edtAddress.setEnabled(true);
                edtName.setBackgroundColor(Color.parseColor(backcolor));
                txtAge.setBackgroundColor(Color.parseColor(backcolor));
                edtPhone.setBackgroundColor(Color.parseColor(backcolor));
                edtEmail.setBackgroundColor(Color.parseColor(backcolor));
                edtAddress.setBackgroundColor(Color.parseColor(backcolor));
                btnModify.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
                ibtn_album.setVisibility(View.VISIBLE);
                ibtn_camera.setVisibility(View.VISIBLE);
                sbAge.setVisibility(View.VISIBLE);
            }
        });

        radSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = view.findViewById(radSex.getCheckedRadioButtonId());
                sex = radioButton.getText().toString();
            }
        });

        sbAge.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txtAge.setText(""+progress);
                age = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = edtName.getText().toString().trim();
                final String phone = edtPhone.getText().toString().trim();
                final String email = edtEmail.getText().toString().trim();
                final String address = edtAddress.getText().toString().trim();
                if (name.equals(user.getName()) &&
                        phone.equals(user.getPhone()) &&
                        email.equals(user.getEmail()) &&
                        address.equals(user.getAddress()) &&
                        age.equals(user.getAge()) &&
                        sex.equals(user.getSex()) &&
                        photo == null) {
                    Toast.makeText(getContext(), "Content unchanged", Toast.LENGTH_SHORT).show();
                    edtName.setEnabled(false);
                    txtAge.setEnabled(false);
                    edtSex.setEnabled(false);
                    edtPhone.setEnabled(false);
                    edtEmail.setEnabled(false);
                    edtAddress.setEnabled(false);
                    imgPhoto.setEnabled(false);
                    edtName.setBackground(null);
                    txtAge.setBackground(null);
                    edtPhone.setBackground(null);
                    edtEmail.setBackground(null);
                    edtAddress.setBackground(null);
                    btnModify.setVisibility(View.GONE);
                    btnCancel.setVisibility(View.GONE);
                    ibtn_camera.setVisibility(View.GONE);
                    ibtn_album.setVisibility(View.GONE);
                    edtSex.setVisibility(View.VISIBLE);
                    radSex.setVisibility(View.GONE);
                    sbAge.setVisibility(View.GONE);
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Please confirm the change information!");
                    builder.setMessage("Name：" + name +
                            "\nSex：" + sex +
                            "\nAge：" + age +
                            "\nPhone：" + phone +
                            "\nE-mail：" + email +
                            "\nAddress：" + address);
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    user.setName(name);
                                    user.setSex(sex);
                                    user.setAge(age);
                                    user.setPhone(phone);
                                    user.setEmail(email);
                                    user.setAddress(address);
                                    user.setPhoto(photo);
                                    DBServerForU dbServerForU = new DBServerForU(getContext());
                                    dbServerForU.open();
                                    if (dbServerForU.updataUsername(
                                            user.getUsername(),
                                            user.getName(),
                                            user.getSex(),
                                            user.getAge(),
                                            user.getPhone(),
                                            user.getEmail(),
                                            user.getAddress(),
                                            user.getPhoto())) {
                                        Toast.makeText(getActivity(), "Modify successfully!", Toast.LENGTH_SHORT).show();
                                        edtName.setEnabled(false);
                                        edtSex.setText(user.getSex());
                                        txtAge.setEnabled(false);
                                        edtPhone.setEnabled(false);
                                        edtEmail.setEnabled(false);
                                        edtAddress.setEnabled(false);
                                        imgPhoto.setEnabled(false);
                                        edtName.setBackground(null);
                                        txtAge.setBackground(null);
                                        edtSex.setBackground(null);
                                        edtPhone.setBackground(null);
                                        edtEmail.setBackground(null);
                                        edtAddress.setBackground(null);
                                        btnModify.setVisibility(View.GONE);
                                        btnCancel.setVisibility(View.GONE);
                                        ibtn_camera.setVisibility(View.GONE);
                                        ibtn_album.setVisibility(View.GONE);
                                        edtSex.setVisibility(View.VISIBLE);
                                        radSex.setVisibility(View.GONE);
                                        sbAge.setVisibility(View.GONE);
                                    }
                                }
                            });
                    builder.setNegativeButton("No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    builder.show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Cancel and Reset");
                builder.setMessage("Would you need to cancel the changes?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                edtName.setText(user.getName());
                                edtSex.setText(user.getSex());
                                txtAge.setText(user.getAge());
                                edtPhone.setText(user.getPhone());
                                edtEmail.setText(user.getEmail());
                                edtAddress.setText(user.getAddress());
                                edtName.setEnabled(false);
                                txtAge.setEnabled(false);
                                edtPhone.setEnabled(false);
                                edtEmail.setEnabled(false);
                                edtAddress.setEnabled(false);
                                imgPhoto.setEnabled(false);
                                edtName.setBackground(null);
                                txtAge.setBackground(null);
                                edtSex.setBackground(null);
                                edtPhone.setBackground(null);
                                edtEmail.setBackground(null);
                                edtAddress.setBackground(null);
                                btnModify.setVisibility(View.GONE);
                                btnCancel.setVisibility(View.GONE);
                                ibtn_camera.setVisibility(View.GONE);
                                ibtn_album.setVisibility(View.GONE);
                                edtSex.setVisibility(View.VISIBLE);
                                radSex.setVisibility(View.GONE);
                                sbAge.setVisibility(View.GONE);
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getContext(), "Cancel successful", Toast.LENGTH_SHORT).show();
                                ;
                            }
                        });
                builder.show();
            }
        });

        ibtn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory(), "pictures");
                if (file.exists()) {
                    file.mkdirs();
                }
                currentImageFile = new File(file, System.currentTimeMillis() + ".jpg");
                if (!currentImageFile.exists()) {
                    try {
                        currentImageFile.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());
                builder.detectFileUriExposure();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(currentImageFile));
                startActivityForResult(intent, 1);
            }
        });

        ibtn_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra("crop", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, 0);
            }
        });

        return view;
    }

    private String photo = null;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_my, null);
        imgPhoto = view.findViewById(R.id.userImg_photo);
        Log.i("file", currentImageFile + "");
        ContentResolver cr = getContext().getContentResolver();
        Bitmap bitmap = null;
        if (requestCode == 0) {
            Uri uri = data.getData();
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContext().getContentResolver().query(uri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                photo = cursor.getString(columnIndex);
                cursor.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == 1) {
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(Uri.fromFile(currentImageFile)));
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContext().getContentResolver().query(Uri.fromFile(currentImageFile),
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                photo = cursor.getString(columnIndex);
                cursor.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        imgPhoto.setImageBitmap(bitmap);
    }

    public void inti(View view) {
        txtAge = view.findViewById(R.id.userTxt_age);
        sbAge = view.findViewById(R.id.userSB_age);
        radSex = view.findViewById(R.id.userMy_Radsex);
        imgPhoto = view.findViewById(R.id.userImg_photo);
        ibtn_edit = view.findViewById(R.id.userIbtn_edit);
        edtName = view.findViewById(R.id.userMy_name);
        edtSex = view.findViewById(R.id.userMy_sex);
        edtPhone = view.findViewById(R.id.userMy_phone);
        edtEmail = view.findViewById(R.id.userMy_email);
        edtAddress = view.findViewById(R.id.userMy_address);
        btnModify = view.findViewById(R.id.my_btnModify);
        btnCancel = view.findViewById(R.id.my_btnCancel);
        ibtn_camera = view.findViewById(R.id.userIbtn_camera);
        ibtn_album = view.findViewById(R.id.userIbtn_album);
        txt_title = view.findViewById(R.id.myTxt_title);
    }
}
