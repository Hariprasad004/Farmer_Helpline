package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hbb20.CountryCodePicker;

public class phone extends AppCompatActivity {
    private EditText phonenum;
    private Button otp;
    private CountryCodePicker ccp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        phonenum=(EditText)findViewById(R.id.phoneText);
        otp=(Button)findViewById(R.id.otpnum);
        init();



        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phonenumber = phonenum.getText().toString().trim();
            }
        });
    }

    private void init() {
        ccp=findViewById(R.id.ccp);
        phonenum=findViewById(R.id.phoneText);
        otp=findViewById(R.id.otpnum);

        otp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                //set text
                getNumber();
            }
        });


    }

    private void getNumber() {

        String fullNumber=ccp.getFullNumber()+phonenum.getText().toString();

    }
}
