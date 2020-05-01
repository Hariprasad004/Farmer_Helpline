package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class phone extends AppCompatActivity {
    private EditText phonenum;
    private Button otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        phonenum=(EditText)findViewById(R.id.num);
        otp=(Button)findViewById(R.id.otpnum);
    }
}
