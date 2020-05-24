package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private EditText emailenter;
    private EditText pass;
    private Button login;
    private TextView ftpass;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            this.getActionBar().hide();
        } catch (Exception e) {

        }
        setContentView(R.layout.activity_login);
        emailenter = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.passwrd);
        login = (Button) findViewById(R.id.loginid);
        ftpass = (TextView) findViewById(R.id.fgtpass);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailenter.getText().toString().trim();
                String password = pass.getText().toString().trim();
                String forget = ftpass.getText().toString().trim();
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initiatializw progress dailogue
                progressDialog = new ProgressDialog(LoginActivity.this);
                //show dialog
                progressDialog.show();
                //set content view
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
            }
        });
    }
        @Override
        public void onBackPressed() {
            //dismiss progress dialog
            progressDialog.dismiss();
        }

    }

