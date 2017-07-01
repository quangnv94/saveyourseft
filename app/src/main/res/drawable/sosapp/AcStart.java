package com.example.admin.sosapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Admin on 4/4/2017.
 */

public class AcStart extends AppCompatActivity {
    private Timer myTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(com.example.admin.sosapp.AcStart.this, AcLogin.class));
            }

        }, 2500);
    }
}
