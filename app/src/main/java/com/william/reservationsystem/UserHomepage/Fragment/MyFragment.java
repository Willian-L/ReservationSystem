package com.william.reservationsystem.UserHomepage.Fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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

import com.william.reservationsystem.R;
import com.william.reservationsystem.SQLite.DBServerForU;
import com.william.reservationsystem.Information.User;
import com.william.reservationsystem.UI.MainActivity;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileWithBitmapCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    private String sex = null;

    private String age = null;

    private String email_suffix = null;
    private String email_top = null;
    private String email_suf = null;

    private String photoPath = null;


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
                user.setPhoto(cursor.getString(cursor.getColumnIndex("photo")));
                photoPath = user.getPhoto();
                Uri uri = Uri.parse(user.getPhoto());
                imgPhoto.setImageURI(uri);
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
                        photoPath.equals(user.getPhoto())) {
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
                                    if (!photoPath.equals(user.getPhoto())){
                                        final AlertDialog.Builder buil = new AlertDialog.Builder(getContext());
                                        buil.setTitle("Please confirm the change information!");
                                        buil.setMessage("Do you want to change the picture?");
                                        buil.setPositiveButton("Yes",
                                                new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        user.setPhoto(photoPath);
                                                    }
                                                });
                                    }
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
                //"点击了照相";
                //  6.0之后动态申请权限 摄像头调取权限,SD卡写入权限
//                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
//                        && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(getActivity(),
//                            new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                            MY_ADD_CASE_CALL_PHONE);
//                } else {
//
//                }
                if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA)) {
                    try {
                        //有权限,去打开摄像头
                        takePhoto();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    //提示用户开户权限   拍照和读写sd卡权限
                    String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
                    ActivityCompat.requestPermissions(getActivity(), perms, MY_ADD_CASE_CALL_PHONE);

                }
            }

        });

        ibtn_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //"点击了相册");
                //  6.0之后动态申请权限 SD卡写入权限
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
        });

        return view;
    }

    //调取系统摄像头的请求码
    private static final int MY_ADD_CASE_CALL_PHONE = 6;
    //打开相册的请求码
    private static final int MY_ADD_CASE_CALL_PHONE2 = 7;

    private void takePhoto() throws IOException {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        // 获取文件
        File file = createFileIfNeed("UserIcon.png");
        //拍照后原图回存入此路径下
        Uri imageUri;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            imageUri = Uri.fromFile(file);
        } else {
            /**
             * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
             * 并且这样可以解决MIUI系统上拍照返回size为0的情况
             */
            imageUri = FileProvider.getUriForFile(getActivity(), "com.william.reservationsystem.fileprovider", file);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(intent, 1);
    }

    // 在sd卡中创建一保存图片（原图和缩略图共用的）文件夹
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
     * 打开相册
     */
    private void choosePhoto() {
        //这是打开系统默认的相册(就是你系统怎么分类,就怎么显示,首先展示分类列表)
        Intent picture = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(picture, 2);
    }

    /**
     * 申请权限回调
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
                // TODO: 2018/12/4 这里可以给用户一个提示,请求权限被拒绝了
            }
        }


        if (requestCode == MY_ADD_CASE_CALL_PHONE2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto();
            } else {
                //"权限拒绝");
                // TODO: 2018/12/4 这里可以给用户一个提示,请求权限被拒绝了
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
                    saveImageToServer(bitmap, outfile);
                }
            });

        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK
                && null != data) {
            try {
                Uri selectedImage = data.getData();
                Tiny.FileCompressOptions options = new Tiny.FileCompressOptions();
                Tiny.getInstance().source(selectedImage).asFile().withOptions(options).compress(new FileWithBitmapCallback() {
                    @Override
                    public void callback(boolean isSuccess, Bitmap bitmap, String outfile, Throwable t) {
                        saveImageToServer(bitmap, outfile);
                    }
                });
                photoPath = selectedImage.toString();
                Log.i("path",photoPath+"");
            } catch (Exception e) {
                //"上传失败");
            }
        }
    }

    /**
     * 从保存原图的地址读取图片
     */
    private String readpic() {
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/photo/" + "UserIcon.png";

        photoPath = filePath;
        Log.i("path",photoPath+"");
        return filePath;
    }

    private void saveImageToServer(final Bitmap bitmap, String outfile) {
//        File file = new File(outfile);
        
        imgPhoto.setImageBitmap(bitmap);
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
}