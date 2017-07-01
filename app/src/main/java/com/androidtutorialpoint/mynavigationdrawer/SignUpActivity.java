package com.androidtutorialpoint.mynavigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Admin on 5/5/2017.
 */

public class SignUpActivity extends AppCompatActivity implements OnClickListener {
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private TextView already_user;
    private FirebaseAuth auth;
    private Button btnSignUp;
    private CheckBox ckTerms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        auth = FirebaseAuth.getInstance();
        init();
    }

    public void init() {
        edtEmail = (EditText) findViewById(R.id.signUpMail);
        edtPassword = (EditText) findViewById(R.id.password);
        edtConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
        btnSignUp = (Button) findViewById(R.id.signUpBtn);
        btnSignUp.setOnClickListener(this);
        ckTerms = (CheckBox) findViewById(R.id.terms_conditions);
        already_user = (TextView) findViewById(R.id.already_user);
        already_user.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signUpAction() {
        // getting email, password, phoneNumber, location, confirmpassword from edittext
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        // checking if any values are empty
        if(email.isEmpty()){
            Toast.makeText(SignUpActivity.this,"Email can't empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.isEmpty()){
            Toast.makeText(SignUpActivity.this,"Password can't empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(confirmPassword.isEmpty()){
            Toast.makeText(SignUpActivity.this,"Password confirmed can't empty.", Toast.LENGTH_SHORT).show();
            return;
        }
        // checking email value
        if (!emailValidator(edtEmail.getText() + "")){
            Toast.makeText(SignUpActivity.this, "Please enter your email again!", Toast.LENGTH_SHORT).show();
            return;
        }
        // checking if password too short
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return;
        }
        // checking password and confirmPassword be the same
        if(!password.equals(confirmPassword)){
            Toast.makeText(SignUpActivity.this,"Password confirmed is not correct", Toast.LENGTH_SHORT).show();
            return;
        }
        // checking checkbox value
        if(!ckTerms.isChecked()){
            Toast.makeText(SignUpActivity.this,"You have to agree all the Tems and Conditions", Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Toast.makeText(SignUpActivity.this, "Authentication failed." + task.getException(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUpActivity.this, "Successful registration. Please log in to start!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });
    }

public boolean emailValidator(String email)
{
    Pattern pattern;
    Matcher matcher;
    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    pattern = Pattern.compile(EMAIL_PATTERN);
    matcher = pattern.matcher(email);
    return matcher.matches();
}

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUpBtn:
                    signUpAction();
                break;
            case R.id.already_user:
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
