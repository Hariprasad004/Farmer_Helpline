package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListOfUsers extends AppCompatActivity {
    String items[]= new String[] {"user1","user2","user3"};
    private String dec;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_users);
        dec = getIntent().getStringExtra("Decision");
        items[3]=dec;
        ListView listView=(ListView) findViewById(R.id.ListView);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.activity_list_item,items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {

        });

    }
}