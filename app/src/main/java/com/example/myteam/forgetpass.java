package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class forgetpass extends AppCompatActivity {
    private EditText emailenter;
    private Button resetpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);
        emailenter=(EditText)findViewById(R.id.emailreset);
        resetpass=(Button)findViewById(R.id.login);

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = emailenter.getText().toString().trim();
            }
        });
    }
}
