package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class signup extends AppCompatActivity {
    private EditText email;
    private  EditText password;
    private EditText conformPassword;
    private RadioButton farmer;
    private RadioButton Lender;
    private Button signUp;
    private Button continueWithEmail;
    private Button continueWithPhone;
    private TextView alreadyHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle("Sign up");
    }
}
