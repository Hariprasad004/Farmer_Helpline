package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class submit_info extends AppCompatActivity {
    private EditText name;
    private EditText number;
    private EditText age;
    private EditText address;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_info);
    }
}
