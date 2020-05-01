package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class frgetpass extends AppCompatActivity {
    private EditText emailenter;
    private Button resetpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frgetpass);
        emailenter=(EditText)findViewById(R.id.emailreset);
        resetpass=(Button)findViewById(R.id.resetpassbtn);
    }
}
