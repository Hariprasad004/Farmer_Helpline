package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class noLend extends AppCompatActivity {
    private TextView dis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_lend);
        dis = findViewById(R.id.res);
        String user = getIntent().getStringExtra("User");
        if(user.equals("user")){
            dis.setText("No user found with that id");
        }
        else {
            dis.setText("No Lenders found");
        }
    }
}