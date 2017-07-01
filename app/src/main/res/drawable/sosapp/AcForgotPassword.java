package com.example.admin.sosapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Admin on 4/8/2017.
 */

public class AcForgotPassword extends AppCompatActivity {
    TextView back;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        back = (TextView) findViewById(R.id.backToLoginBtn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.admin.sosapp.AcForgotPassword.this, AcLogin.class);
                startActivity(intent);
            }
        });
    }
}
