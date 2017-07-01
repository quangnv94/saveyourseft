package com.androidtutorialpoint.mynavigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidtutorialpoint.mynavigationdrawer.common.AppContanst;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.libre.mylibs.MyUtils;

/**
 * Created by Admin on 5/5/2017.
 */

public class LoginActivity extends AppCompatActivity {
    EditText login_mail, login_pass;
    Button btnlogin;
    TextView tvsignup, forgotPass;
    CheckBox ckShowPassword;
    private static FirebaseAuth auth;
    private static FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        checkLogin();
        init();
    }

    public void checkLogin() {
        if (MyUtils.getStringData(getApplicationContext(), AppContanst.TOKEN) == null || !MyUtils.getStringData(getApplicationContext(), AppContanst.TOKEN).equals("")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void init() {
        login_mail = (EditText) findViewById(R.id.login_email);
        login_pass = (EditText) findViewById(R.id.login_password);
        tvsignup = (TextView) findViewById(R.id.createAccount);
        forgotPass = (TextView) findViewById(R.id.forgot_password);
        btnlogin = (Button) findViewById(R.id.loginBtn);
        ckShowPassword = (CheckBox) findViewById(R.id.show_hide_password);
        if (MyUtils.getIntData(getApplicationContext(), String.valueOf(AppContanst.COUNT)) == 1) {
            login_mail.setText(MyUtils.getStringData(getApplicationContext(), AppContanst.EMAIL));
        }
        tvsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        ckShowPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    login_pass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                } else {
                    login_pass.setInputType(129);
                }
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String login_email = login_mail.getText().toString();
                final String login_password = login_pass.getText().toString();

                if (TextUtils.isEmpty(login_email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(login_password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //authenticate user
                auth.signInWithEmailAndPassword(login_email, login_password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (login_password.length() < 6) {
                                login_pass.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            MyUtils.insertStringData(getApplicationContext(), AppContanst.TOKEN, task.getResult().getUser().getUid());
                            Log.d("checktoken", task.getResult().getUser().getUid());

                            MyUtils.insertIntData(getApplicationContext(), String.valueOf(AppContanst.COUNT), 1);
                            MyUtils.insertStringData(getApplicationContext(), AppContanst.EMAIL, login_email);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });
            }
        });

    }

    public static FirebaseAuth getAuth() {
        return auth;
    }

}



