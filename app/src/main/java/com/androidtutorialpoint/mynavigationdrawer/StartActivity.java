package com.androidtutorialpoint.mynavigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.androidtutorialpoint.mynavigationdrawer.common.AppContanst;
import com.libre.mylibs.MyUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Admin on 5/5/2017.
 */

public class StartActivity extends AppCompatActivity {
    private Timer myTimer;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(StartActivity.this, LoginActivity.class));
            }

        }, 2500);
    }
}
