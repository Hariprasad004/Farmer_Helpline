package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class phone extends AppCompatActivity {
    private EditText phonenum;
    private Button otp;
    private CountryCodePicker ccode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        phonenum=(EditText)findViewById(R.id.phoneText);
        otp=(Button)findViewById(R.id.otpnum);
        ccode=findViewById(R.id.ccp);
        getSupportActionBar().setTitle("Phone");
        otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phonenumber = phonenum.getText().toString().trim();
                if(!phonenumber.isEmpty() && phonenumber.length()==10){
                    //if(checkvalid(phonenumber)){
                        String pNo=ccode.getSelectedCountryCode()+phonenumber;
                        Intent intent = new Intent(getApplicationContext(), signnext.class);
                        intent.putExtra("PhoneNo",pNo);
                        startActivity(intent);
                    //}
                    //else
                      //  phonenum.setError("Phone number is not valid");
                }
                else{
                    phonenum.setError("Phone number is not valid");
                }
            }
        });
    }
    private boolean checkvalid(String p) {
       try{
           int a=Integer.parseInt(p);
           return true;
       }
       catch (Exception e){
           return false;
       }
    }
}
