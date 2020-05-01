package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private EditText emailenter;
    private EditText pass;
    private Button login;
    private TextView ftpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try{
            this.getActionBar().hide();
        }catch (Exception e) {

        }
        setContentView(R.layout.activity_login);
        emailenter=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.passwrd);
        login=(Button)findViewById(R.id.loginid);
        ftpass=(TextView)findViewById(R.id.fgtpass);

    }
}
