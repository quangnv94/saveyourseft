package com.example.admin.sosapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Admin on 4/8/2017.
 */

public class AcSignUp extends AppCompatActivity {
    TextView already_user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        already_user = (TextView) findViewById(R.id.already_user);

        //////
        already_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.admin.sosapp.AcSignUp.this, com.example.admin.sosapp.AcLogin.class);
                startActivity(intent);
            }
        });


    }
}
