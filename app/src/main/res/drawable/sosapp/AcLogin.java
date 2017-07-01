package com.example.admin.sosapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Admin on 4/4/2017.
 */

public class AcLogin extends AppCompatActivity {
    EditText phonenumber, pass;
    Button btnlogin;
    TextView tvsignup, forgotPass;
    // nhiu do dc k anh
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvsignup = (TextView) findViewById(R.id.createAccount);
        forgotPass = (TextView) findViewById(R.id.forgot_password);
        btnlogin = (Button) findViewById(R.id.loginBtn);
        /////
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.admin.sosapp.AcLogin.this, AcSignUp.class);
                startActivity(intent);
            }
        });
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.admin.sosapp.AcLogin.this, com.example.admin.sosapp.AcForgotPassword.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.admin.sosapp.AcLogin.this, com.example.admin.sosapp.MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
