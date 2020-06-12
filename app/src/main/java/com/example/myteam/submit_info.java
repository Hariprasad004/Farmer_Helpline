package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class submit_info extends AppCompatActivity {
    private EditText Name;
    private EditText number;
    private EditText Age;
    private EditText address;
    private RadioButton farmer;
    private RadioButton Lender;

    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_info);
        Name=(EditText)findViewById(R.id.name);
        number=(EditText)findViewById(R.id.ph);
        Age=(EditText)findViewById(R.id.age);
        address=(EditText)findViewById(R.id.add);
        submit=(Button)findViewById(R.id.sub);
        getSupportActionBar().setTitle("Details of User");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String NAme = Name.getText().toString().trim();
                String num = number.getText().toString().trim();
                String person_age = Age.getText().toString().trim();
                String person_adress = address.getText().toString().trim();
            }
        });
    }
}
