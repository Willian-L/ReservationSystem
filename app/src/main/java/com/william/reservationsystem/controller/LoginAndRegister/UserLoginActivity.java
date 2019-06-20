package com.william.reservationsystem.controller.LoginAndRegister;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.william.reservationsystem.R;
import com.william.reservationsystem.controller.CrashHandle.CrashHandlerUtil;
import com.william.reservationsystem.controller.MasterHomepage.HomepageForMasterActivity;
import com.william.reservationsystem.controller.ResetPassword.ResetVerActivity;
import com.william.reservationsystem.controller.UserHomepage.HomepageForUActivity;
import com.william.reservationsystem.model.DBServerForM;
import com.william.reservationsystem.model.DBServerForU;
import com.william.reservationsystem.model.Master;
import com.william.reservationsystem.model.SharedPreferencesUtils;
import com.william.reservationsystem.model.User;

public class UserLoginActivity extends AppCompatActivity{

    EditText edtUsername, edtPassword;
    Button btnLogin;
    CheckBox checkRemember;
    ImageButton imgBtnScan;

    User user = new User();
    Master master = new Master();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        init();

        checkAutoLogin(checkRemember, edtUsername, edtPassword);

        getUser(edtUsername);

        CrashHandlerUtil.getInstance().init();

        edtPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!TextUtils.isEmpty(edtPassword.getText().toString())&&!TextUtils.isEmpty(edtUsername.getText().toString())){
                        btnLogin.setEnabled(true);
                        btnLogin.setBackgroundResource(R.drawable.button_orange);
                    }
                    else {
                        btnLogin.setEnabled(false);
                        btnLogin.setBackgroundResource(R.drawable.radiu_button);
                    }
                }
            }
        });

        edtUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus){
                    if (!TextUtils.isEmpty(edtPassword.getText().toString())&&!TextUtils.isEmpty(edtUsername.getText().toString())){
                        btnLogin.setEnabled(true);
                        btnLogin.setBackgroundResource(R.drawable.button_orange);
                    }
                    else {
                        btnLogin.setEnabled(false);
                        btnLogin.setBackgroundResource(R.drawable.radiu_button);
                    }
                }
            }
        });
    }

    /**
     * Gets the username that the user just registered
     */
    private void getUser(EditText edtUsername) {
        try {
            Intent intent = getIntent();
            String username = intent.getStringExtra("username");
            edtUsername.setText(username);
        } catch (Exception e) {
        }
    }

    /**
     * Automatic login function
     */
    private void checkAutoLogin(CheckBox checkRemember, EditText edtUsername, EditText edtPassword) {
        String username = SharedPreferencesUtils.getInstance().getString(SharedPreferencesUtils.USER_NAME, "");
        String password = SharedPreferencesUtils.getInstance().getString(SharedPreferencesUtils.PASSWORD, "");
        if (TextUtils.isEmpty(username)) {
            checkRemember.setChecked(false);
            edtUsername.setText(null);
            edtPassword.setText(null);
        } else {
            checkRemember.setChecked(true);
            login(username,password);
            finish();
        }
    }

    /**
     * The login button
     */
    public void btnlogin(View view) {
        String username = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        login(username,password);
    }

    public void login(String username, String password){
        if (!username.equals("") && !password.equals("")) {
            if (MasterLogin(username, password)) {
                Intent intent = new Intent(UserLoginActivity.this, HomepageForMasterActivity.class);
                startActivity(intent);
                finish();
            } else {
                UserLogin(username, password);
            }
        }
    }

    /**
     * User login method
     */
    private void UserLogin(String username, String password) {
        int getResult = 0;
        // Get the username and password
        user.setUsername(username);
        user.setPassword(password);
        DBServerForU dbServerForU = new DBServerForU(getApplicationContext());
        dbServerForU.open();
        // Determine whether the username and password match the one in the database
        getResult = dbServerForU.login(user.getUsername(), user.getPassword());
        switch (getResult) {
            case 2:
                if (checkRemember.isChecked()) {
                    SharedPreferencesUtils.getInstance().putString(SharedPreferencesUtils.USER_NAME, user.getUsername());
                    SharedPreferencesUtils.getInstance().putString(SharedPreferencesUtils.PASSWORD, user.getPassword());
                } else {
                    SharedPreferencesUtils.getInstance().clear();
                }
                Intent intent = new Intent(UserLoginActivity.this, HomepageForUActivity.class);
                intent.putExtra("username", user.getUsername());
                startActivity(intent);
                dbServerForU.close();
                finish();
                break;
            case 1:
                showResetDialog();
                break;
            case 0:
                showRegisterDialog();
                break;
            default:
                break;
        }
        dbServerForU.close();
    }

    /**
     * Master login method
     */
    private boolean MasterLogin(String username, String password) {
        boolean result = false;
        master.setUsername(username);
        master.setPassword(password);
        DBServerForM dbServerForM = new DBServerForM(getApplicationContext());
        dbServerForM.open();
        if (dbServerForM.login(master.getUsername(), master.getPassword())) {
            dbServerForM.close();
            if (checkRemember.isChecked()) {
                SharedPreferencesUtils.getInstance().putString(SharedPreferencesUtils.USER_NAME, master.getUsername());
                SharedPreferencesUtils.getInstance().putString(SharedPreferencesUtils.PASSWORD, master.getPassword());
            } else {
                SharedPreferencesUtils.getInstance().clear();
            }
            result = true;
        }
        dbServerForM.close();
        return result;
    }

    /**
     * Dialog
     */
    private void showRegisterDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserLoginActivity.this);
        alertDialog.setTitle("This username does not exist!");
        alertDialog.setMessage("Do you need to register a new account?");
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toRegister();
                    }
                });
        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        alertDialog.show();
    }

    private void showResetDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserLoginActivity.this);
        alertDialog.setTitle("Password is wrong!");
        alertDialog.setMessage("Do you need to reset your password?");
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toForgot();
                    }
                });
        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        alertDialog.show();
    }

    /**
     * Intention of events
     */
    public void register(View view) {
        toRegister();
    }

    public void forgot(View view) {
        toForgot();
    }

    private void toRegister() {
        Intent intent = new Intent(UserLoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    private void toForgot() {
        Intent intent = new Intent(UserLoginActivity.this, ResetVerActivity.class);
        if (edtUsername != null) {
            intent.putExtra("username", edtUsername.getText().toString().trim());
        }
        startActivity(intent);
    }

    /**
     * Find Controls
     */
    private void init() {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        checkRemember = findViewById(R.id.loginCb_Remember);
        imgBtnScan = findViewById(R.id.imgBtn_scan);
    }

    /**
     * Check the permissions
     */
    private static final int MY_ADD_CASE_CALL_PHONE = 6;

    public void scanMethod(View view) {
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        && PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            showPopupWindow();
        } else {
            //提示用户开户权限
            String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions(this, perms, MY_ADD_CASE_CALL_PHONE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_ADD_CASE_CALL_PHONE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showPopupWindow();
            }
        }
    }

    /**
     * set popupWindow
     */
    private PopupWindow mPopWindow;

    private void showPopupWindow(){
        View contentView = LayoutInflater.from(UserLoginActivity.this).inflate(R.layout.scan_popup, null);
        mPopWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        mPopWindow.setContentView(contentView);
        TextView tv_camera = contentView.findViewById(R.id.pop_camera);
        TextView tv_album = contentView.findViewById(R.id.pop_album);
        View rootview = LayoutInflater.from(UserLoginActivity.this).inflate(R.layout.activity_user_login, null);
        mPopWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        mPopWindow.setAnimationStyle(R.style.pop_animation);
        mPopWindow.showAtLocation(rootview, Gravity.BOTTOM,0,20);
        tv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCamera();
                mPopWindow.dismiss();
            }
        });
        tv_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAlbum();
                mPopWindow.dismiss();
            }
        });
    }

    /**
     * scan QR code
     */
    private int REQUEST_CAMERA = 1;
    private int REQUEST_IMAGE = 2;

    private void openAlbum(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    public void userCamera(){
        Intent intent = new Intent(getApplication(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    qrLogin(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(UserLoginActivity.this, "The resolution of QR code fails!", Toast.LENGTH_LONG).show();
                }
            }
        }
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtil.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            qrLogin(result);
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(UserLoginActivity.this, "The resolution of QR code fails!", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Verify qr code
     */
    private boolean verify(String qr){
        boolean result = false;
        if (qr.matches("^\\#+[\\w]+[@]+[0-9]*+\\#$")) {
            result = true;
        } else {
            Toast.makeText(getApplicationContext(),
                    "Qr code mismatch!", Toast.LENGTH_SHORT).show();
        }
        return result;
    }

    /**
     * Login through qr code information
     */
    private void qrLogin(String qr){
        if (verify(qr)){
            int tap = qr.indexOf("@");
            String username = qr.substring(1,tap);
            String phone = qr.substring(tap+1, qr.length()-1);
            Log.i("qrLogin", username);
            Log.i("qrLogin", phone);
            DBServerForU dbServerForU = new DBServerForU(this);
            dbServerForU.open();
            if (dbServerForU.qrlogin(username, phone)){
                Intent intent = new Intent(UserLoginActivity.this, HomepageForUActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
                dbServerForU.close();
                finish();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This account does not exist!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
