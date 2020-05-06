package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;

public class signActivity extends AppCompatActivity {
private EditText email;
private EditText password;
private EditText confirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        email=(EditText)findViewById(R.id.emails);
        password=(EditText)findViewById(R.id.pass);
        confirmpass=(EditText)findViewById(R.id.confirm);
    }
}
