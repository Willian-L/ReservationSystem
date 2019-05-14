package com.william.reservationsystem.UserHomepage.Fragment;

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
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.william.reservationsystem.R;
import com.william.reservationsystem.SQLite.DBServerForU;
import com.william.reservationsystem.Information.User;

import java.io.File;
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
    TextView txtAge, txtEmail;
    SeekBar sbAge;
    Spinner spEmail;

    private File currentImageFile = null;

    private String sex = null;

    private String age = null;

    private String email_suffix = null;
    private String email_top = null;
    private String email_suf = null;

    private Uri imageUri = null;

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
                txtEmail.setText(user.getEmail());
                user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                edtAddress.setText(user.getAddress());
            }
        }

        spEmail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] suffix = getResources().getStringArray(R.array.email_suffix);
                email_suffix = suffix[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                email_suffix = email_suf;
            }
        });

        ibtn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String backcolor = "#1fffffff";
                edtName.setEnabled(true);
                edtSex.setVisibility(View.GONE);
                radSex.setVisibility(View.VISIBLE);
                txtAge.setEnabled(true);
                txtEmail.setVisibility(View.GONE);
                edtPhone.setEnabled(true);
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
                spEmail.setVisibility(View.VISIBLE);
                edtEmail.setVisibility(View.VISIBLE);
                String txtemail = txtEmail.getText().toString().trim();
                if (!txtemail.equals("")) {
                    final String Email = txtemail;
                    int place = Email.indexOf("@");
                    email_top = Email.substring(0, place);
                    email_suf = Email.substring(place);
                    edtEmail.setText(email_top);
                    setSpinnerDefaultValue(spEmail, email_suf);
                }
                if (!txtAge.getText().toString().trim().equals("")) {
                    sbAge.setProgress(Integer.valueOf(txtAge.getText().toString().trim()));
                }
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
                txtAge.setText("" + progress);
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
                final String email_input = edtEmail.getText().toString().trim() + email_suffix;
                final String address = edtAddress.getText().toString().trim();
                final String sex = edtSex.getText().toString().trim();
                final String age = txtAge.getText().toString().trim();
                String name_db = user.getName();
                String phone_db = user.getPhone();
                String email_db = user.getEmail();
                String address_db = user.getAddress();
                String age_db = user.getAge();
                String sex_db = user.getSex();
                if (name.equals(name_db) &&
                        phone.equals(phone_db) &&
                        email_input.equals(email_db) &&
                        address.equals(address_db) &&
                        age.equals(age_db) &&
                        sex.equals(sex_db) &&
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
                    txtEmail.setVisibility(View.VISIBLE);
                    radSex.setVisibility(View.GONE);
                    sbAge.setVisibility(View.GONE);
                    spEmail.setVisibility(View.GONE);
                    edtEmail.setVisibility(View.GONE);
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Please confirm the change information!");
                    builder.setMessage("Name：" + name +
                            "\nSex：" + sex +
                            "\nAge：" + age +
                            "\nPhone：" + phone +
                            "\nE-mail：" + email_input +
                            "\nAddress：" + address);
                    builder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    user.setName(name);
                                    user.setSex(sex);
                                    user.setAge(age);
                                    user.setPhone(phone);
                                    user.setEmail(email_input);
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
                                        txtEmail.setText(email_input);
                                        txtEmail.setVisibility(View.VISIBLE);
                                        radSex.setVisibility(View.GONE);
                                        sbAge.setVisibility(View.GONE);
                                        spEmail.setVisibility(View.GONE);
                                        edtEmail.setVisibility(View.GONE);
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
                                txtEmail.setText(user.getEmail());
                                edtAddress.setText(user.getAddress());
                                edtName.setEnabled(false);
                                txtAge.setEnabled(false);
                                edtPhone.setEnabled(false);
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
                                txtEmail.setVisibility(View.VISIBLE);
                                sbAge.setVisibility(View.GONE);
                                spEmail.setVisibility(View.GONE);
                                edtEmail.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Cancel successful", Toast.LENGTH_SHORT).show();
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
        });

        ibtn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(MyFragment.this.getContext().getExternalCacheDir(), "pictures");
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
        txtEmail = view.findViewById(R.id.txt_email);
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
        spEmail = view.findViewById(R.id.mySpi_email);
    }

    private void setSpinnerDefaultValue(Spinner spinner, String value) {
        SpinnerAdapter apsAdapter = spinner.getAdapter();
        int size = apsAdapter.getCount();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(value, apsAdapter.getItem(i).toString())) {
                spinner.setSelection(i, true);
                break;
            }
        }
    }

//    private void openCamare() {
//        File outputImg = new File(getContext().getExternalCacheDir(), "output_image.jpg");
//        try {
//            if (outputImg.exists()) {
//                outputImg.delete();
//            }
//            outputImg.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (Build.VERSION.SDK_INT >= 24) {
//            imageUri = FileProvider.getUriForFile(getContext(), "com.example.cameraalbumtest.fileprovider", outputImg);
//        } else {
//            imageUri = Uri.fromFile(outputImg);
//        }
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, 1);
//    }
}