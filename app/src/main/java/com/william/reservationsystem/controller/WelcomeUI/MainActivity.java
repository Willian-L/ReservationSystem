package com.william.reservationsystem.controller.WelcomeUI;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.william.reservationsystem.R;
import com.william.reservationsystem.controller.LoginAndRegister.UserLoginActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static final long DELAY = 3500;
    private TimerTask task;

    private TextView title;
    private RelativeLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.main_title);

        AlphaAnimation titleAni = new AlphaAnimation(0,1.0f);
        titleAni.setStartOffset(500);
        titleAni.setDuration(2500);
        title.setAnimation(titleAni);

        automatic();
    }

    /**
     * Automatic switching after a certain time
     */
    private void automatic(){
        final Intent intent = new Intent(this, UserLoginActivity.class);
        Timer timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        };
        timer.schedule(task, DELAY);
    }
}
