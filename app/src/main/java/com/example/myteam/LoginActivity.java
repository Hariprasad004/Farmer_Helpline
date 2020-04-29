package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            this.getActionBar().hide();
        }catch (Exception e) {

        }
        setContentView(R.layout.activity_login);
    }
}
