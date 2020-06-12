package com.example.myteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class signActivity extends AppCompatActivity {
private EditText email_txt;
private EditText password_txt;
private EditText confirmpass;
private Button signup;
private TextView al_login;
private Button cphone;
ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("SIGNUP");
        setContentView(R.layout.activity_sign);
        email_txt = (EditText) findViewById(R.id.emails);
        password_txt = (EditText) findViewById(R.id.pass);
        confirmpass = (EditText) findViewById(R.id.confirm);
        signup = (Button) findViewById(R.id.signup);
        cphone = (Button) findViewById(R.id.cnphone);
        al_login = (TextView) findViewById(R.id.allrdy_login);

        final FirebaseAuth fAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(signActivity.this);
                //show dialog
                progressDialog.show();
                //set content view
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                String email = email_txt.getText().toString().trim();
                String password = password_txt.getText().toString().trim();
                String confpass = confirmpass.getText().toString().trim();
                if (TextUtils.isEmpty((email))) {
                    progressDialog.dismiss();
                    Toast.makeText(signActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    progressDialog.dismiss();
                    Toast.makeText(signActivity.this, "Enter the valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty((password))) {
                    progressDialog.dismiss();
                    Toast.makeText(signActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty((confpass))) {
                    progressDialog.dismiss();
                    Toast.makeText(signActivity.this, "Please enter Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    progressDialog.dismiss();
                    Toast.makeText(signActivity.this, "Password length should be greater than 6", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equals(confpass)) {
                    fAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(signActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(signActivity.this, "User successfully registered, Please verify your email", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(signActivity.this, LoginActivity.class);
                                                    startActivity(intent);
                                                    signActivity.this.finish();
                                                }
                                                else{
                                                    progressDialog.dismiss();
                                                    Toast.makeText(signActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(signActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(signActivity.this, "Password and confirm password doesn't match", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
        cphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signActivity.this, phone.class);
                startActivity(intent);
            }
        });
        al_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signActivity.this, LoginActivity.class);
                startActivity(intent);
                signActivity.this.finish();
            }
        });
    }
}