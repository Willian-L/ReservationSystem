package com.william.reservationsystem.controller.WelcomeUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.william.reservationsystem.R;
import com.william.reservationsystem.controller.LoginAndRegister.UserLoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final long DELAY = 3000;
    private TimerTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        automatic();
    }

    /*
    Automatic switching after a certain time
     */
    private void automatic(){
        final Intent intent = new Intent(this, UserLoginActivity.class);
        Timer timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
            }
        };
        timer.schedule(task, DELAY);
    }
}
