package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class signnext extends AppCompatActivity {
    private EditText et_otpNumber;
    private TextView otpVerification;
    private EditText enterOtp;
    private TextView resendOtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        requestSMSPermission();


        et_otpNumber = findViewById(R.id.et_otpNumber);
        new OTP_Receiver().setEditText(et_otpNumber);

    }
    private void requestSMSPermission(){
        String permission = Manifest.permission.RECEIVE_SMS;
        int grant= ContextCompat.checkSelfPermission(this,permission);
        if (grant != PackageManager.PERMISSION_GRANTED)
        {
            String[] permission_List=new  String[1];
            permission_List[0]=permission;
            ActivityCompat.requestPermissions(this,permission_List,1);
        }
    }
}
