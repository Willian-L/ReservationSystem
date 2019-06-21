package com.william.reservationsystem.controller.UserHomepage.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.william.reservationsystem.R;
import com.william.reservationsystem.controller.LoginAndRegister.UserLoginActivity;
import com.william.reservationsystem.model.DBServerForU;
import com.william.reservationsystem.model.SharedPreferencesUtils;
import com.william.reservationsystem.model.User;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileWithBitmapCallback;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.william.reservationsystem.R.mipmap.ic_treker;

public class MyFragment extends Fragment {

    ImageView imgPhoto, imgQR;
    ImageButton ibtn_edit, ibtn_camera, ibtn_album, ibtn_qr;
    EditText edtName, edtSex, edtPhone, edtEmail, edtAddress;
    Button btnModify, btnCancel, btnLogout;
    User user = new User();
    TextView txt_title;
    RadioGroup radSex;
    TextView txtAge, txtEmail;
    SeekBar sbAge;
    Spinner spEmail;

    private String sex = null;
    private String getSex = null;

    private String age = null;

    private String email_suffix = null;
    private String email_top = null;
    private String email_suf = null;

    private String photoPath = null;
    private Uri getPhotoURI = null;
    private Uri photoURI = null;

    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_my, container, false);
        inti(view);

        getUser();
        getData();

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
                openEdit();
                setEmailEdit();
                setAgeEdit();
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
                modify();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });


        ibtn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera();
            }

        });

        ibtn_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                album();
            }
        });

        ibtn_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createQR();
            }
        });

        imgQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibtn_qr.setVisibility(View.VISIBLE);
                imgQR.setVisibility(View.GONE);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        return view;
    }

    private void getUser() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            user.setUsername(bundle.getString("username"));
        }
        txt_title.setText(user.getUsername() + "'s Information");
    }

    private void getData() {
        DBServerForU dbServerForU = new DBServerForU(getContext());
        dbServerForU.open();
        Cursor cursor = dbServerForU.selectByUsername(user.getUsername());
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                user.setName(cursor.getString(cursor.getColumnIndex("name")));
                edtName.setText(user.getName());
                user.setSex(cursor.getString(cursor.getColumnIndex("sex")));
                getSex = user.getSex();
                edtSex.setText(getSex);
                user.setAge(cursor.getString(cursor.getColumnIndex("age")));
                txtAge.setText(user.getAge());
                user.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                edtPhone.setText(user.getPhone());
                user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                txtEmail.setText(user.getEmail());
                user.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                edtAddress.setText(user.getAddress());
                user.setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
                photoPath = user.getPhoto();
                if (photoPath != null) {
                    getPhotoURI = Uri.parse(photoPath);
                    photoURI = getPhotoURI;
                    imgPhoto.setImageURI(getPhotoURI);
                }
            }
        }
        dbServerForU.close();
    }

    private void openEdit() {
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
        if (getSex != null) {
            switch (getSex) {
                case "Man":
                    radSex.check(R.id.user_radBtnman);
                    break;
                case "Woman":
                    radSex.check(R.id.user_radBtnwoman);
                    break;
            }
        }
        btnModify.setVisibility(View.VISIBLE);
        btnCancel.setVisibility(View.VISIBLE);
        ibtn_album.setVisibility(View.VISIBLE);
        ibtn_camera.setVisibility(View.VISIBLE);
        sbAge.setVisibility(View.VISIBLE);
        spEmail.setVisibility(View.VISIBLE);
        edtEmail.setVisibility(View.VISIBLE);
        btnLogout.setVisibility(View.GONE);
        ibtn_qr.setVisibility(View.GONE);
    }

    private void setEmailEdit() {
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

    private void setAgeEdit() {
        if (!txtAge.getText().toString().trim().equals("")) {
            sbAge.setProgress(Integer.valueOf(txtAge.getText().toString().trim()));
        }
    }

    private void closeEdit() {
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
        txtEmail.setVisibility(View.VISIBLE);
        radSex.setVisibility(View.GONE);
        sbAge.setVisibility(View.GONE);
        spEmail.setVisibility(View.GONE);
        edtEmail.setVisibility(View.GONE);
        btnLogout.setVisibility(View.VISIBLE);
        ibtn_qr.setVisibility(View.VISIBLE);
    }

    private void modify() {
        final String name = edtName.getText().toString().trim();
        final String phone = edtPhone.getText().toString().trim();
        final String email_input = edtEmail.getText().toString().trim() + email_suffix;
        final String address = edtAddress.getText().toString().trim();
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
                sex.equals(sex_db)) {
            if (photoURI.equals(getPhotoURI)) {
                Toast.makeText(getContext(), "Content unchanged", Toast.LENGTH_SHORT).show();
                closeEdit();
            } else {
                Log.i("getPathToDB", "yes");
                AlertDialog.Builder buil = new AlertDialog.Builder(getContext());
                buil.setTitle("Please confirm the change information!");
                buil.setMessage("Do you want to change the picture?");
                buil.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String photoU = photoURI.toString();
                                user.setPhoto(photoU);
                                getPhotoURI = photoURI;
                                updateDB();
                                closeEdit();
                            }
                        });
                buil.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                imgPhoto.setImageURI(getPhotoURI);
                                photoURI = null;
                                closeEdit();
                                Toast.makeText(getContext(), "Content unchanged", Toast.LENGTH_SHORT).show();
                            }
                        });
                buil.show();
            }
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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
                            if (photoURI != null) {
                                String photoU = photoURI.toString();
                                user.setPhoto(photoU);
                            }
                            updateDB();
                            txtEmail.setText(email_input);
                            getSex = user.getSex();
                            edtSex.setText(getSex);
                            closeEdit();
                        }
                    });
            builder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "Content unchanged", Toast.LENGTH_SHORT).show();
                            closeEdit();
                        }
                    });
            builder.show();
        }
    }

    private void updateDB() {
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
        }
    }

    private void cancel() {
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
                        if (getPhotoURI == null) {
                            imgPhoto.setImageDrawable(null);
                        }
                        imgPhoto.setImageURI(getPhotoURI);
                        photoURI = null;
                        closeEdit();
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

    private void camera() {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA)) {
            try {
                takePhoto();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
            ActivityCompat.requestPermissions(getActivity(), perms, MY_ADD_CASE_CALL_PHONE);
        }
    }

    private void album() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_ADD_CASE_CALL_PHONE2);

        } else {
            choosePhoto();
        }
    }

    private static final int MY_ADD_CASE_CALL_PHONE = 6;
    private static final int MY_ADD_CASE_CALL_PHONE2 = 7;

    private void takePhoto() throws IOException {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        // 获取文件
        String filename = fileName();
        photoName = filename + ".png";
        File file = createFileIfNeed(photoName);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            photoURI = Uri.fromFile(file);
        } else {
            /**
             * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
             * 并且这样可以解决MIUI系统上拍照返回size为0的情况
             */
            photoURI = FileProvider.getUriForFile(getActivity(), "com.william.reservationsystem.fileprovider", file);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, 1);
    }

    /**
     * Create folders in sd card
     */
    private File createFileIfNeed(String fileName) throws IOException {
        String fileA = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/photo";
        File fileJA = new File(fileA);
        if (!fileJA.exists()) {
            fileJA.mkdirs();
        }
        File file = new File(fileA, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }

        return file;
    }

    /**
     * Oppo album
     */
    private void choosePhoto() {
        //这是打开系统默认的相册(就是你系统怎么分类,就怎么显示,首先展示分类列表)
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 2);
    }

    /**
     * Request permission callback
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode == MY_ADD_CASE_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    takePhoto();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                //"权限拒绝");
                // 这里可以给用户一个提示,请求权限被拒绝了
            }
        }


        if (requestCode == MY_ADD_CASE_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto();
            } else {
                //"权限拒绝");
                // 这里可以给用户一个提示,请求权限被拒绝了
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode != Activity.RESULT_CANCELED) {
            String state = Environment.getExternalStorageState();
            if (!state.equals(Environment.MEDIA_MOUNTED)) return;
            // 把原图显示到界面上
            Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
            Tiny.getInstance().source(readpic()).asFile().withOptions(options).compress(new FileWithBitmapCallback() {
                @Override
                public void callback(boolean isSuccess, Bitmap bitmap, String outfile, Throwable t) {
                    photoClip(bitmap);
                }
            });

        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK
                && null != data) {
            try {
                photoURI = data.getData();
                Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                Tiny.getInstance().source(photoURI).asFile().withOptions(options).compress(new FileWithBitmapCallback() {
                    @Override
                    public void callback(boolean isSuccess, Bitmap bitmap, String outfile, Throwable t) {
                        photoClip(bitmap);
                    }
                });
                Log.i("path", photoPath + "");
            } catch (Exception e) {
                //"上传失败");
            }
        } else if (requestCode == 3 && resultCode == Activity.RESULT_OK) {
            photoURI = data.getData();
            imgPhoto.setImageURI(photoURI);
        }
    }

    /**
     * Reads the image from the address where the original image was saved
     */
    private String fileName() {
        SimpleDateFormat timesdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String FileTime = timesdf.format(new Date()).toString();//获取系统时间
        String filename = FileTime.replace(":", "");
        return filename;
    }

    private String photoName = null;

    private String readpic() {
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/photo/" + photoName;
        Log.d("filename", "" + filePath);
        return filePath;
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

    /**
     * Call the system's own photo clipping
     */
    private void photoClip(Bitmap bitmap) {
        Uri uri = Uri.parse(MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, null, null));
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 3);
    }

    /**
     * Create QR code
     */
    private void createQR() {
        ibtn_qr.setVisibility(View.GONE);
        String qr = selectDB();
        Bitmap mBitmap = null;
        if (getPhotoURI != null) {
            try {
                mBitmap = CodeUtils.createImage(qr, 140, 140, BitmapFactory.decodeStream(getContext().getContentResolver().openInputStream(getPhotoURI)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            mBitmap = CodeUtils.createImage(qr, 140, 140, BitmapFactory.decodeResource(getResources(), ic_treker));
        }
        imgQR.setImageBitmap(mBitmap);
        imgQR.setVisibility(View.VISIBLE);
    }

    private String selectDB() {
        String qr = "#" + user.getUsername() + "@" + user.getPhone() + "#";
        return qr;
    }

    /**
     * Logout method
     */
    private void logout() {
        SharedPreferencesUtils.getInstance().clear();
        Intent intent = new Intent(getActivity(), UserLoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void inti(View view) {
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
        btnLogout = view.findViewById(R.id.my_btnLogout);
        ibtn_qr = view.findViewById(R.id.imgBtn_qr);
        imgQR = view.findViewById(R.id.img_QR);
    }
}