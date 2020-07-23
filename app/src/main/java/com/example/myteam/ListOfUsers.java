package com.example.myteam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.jar.Attributes;

public class ListOfUsers extends AppCompatActivity {
    ListView listView;
    String NAMES[]={"name1","name2"};
    int PHONE[]={12333,1234};
    String ADDRESS[]={"qwwe","dfgh"};
    int AGE[]={29,37};
    int IMAGES[]={R.drawable.farm1,R.drawable.farm2};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_users);
        ListView listView=(ListView)findViewById(R.id.ListView);
        CustomAdapter customAdapter=new CustomAdapter();
        listView.setAdapter(customAdapter);


    }
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return IMAGES.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup parent) {
            view= getLayoutInflater().inflate(R.layout.row,null);
            ImageView imageView=(ImageView)view.findViewById(R.id.image);
            TextView textView_name=(TextView)view.findViewById(R.id.textView1);
            TextView textView_phone=(TextView)view.findViewById(R.id.textView2);
            TextView textView_address=(TextView)view.findViewById(R.id.textView3);
            TextView textView_age=(TextView)view.findViewById(R.id.textView4);

            imageView.setImageResource(IMAGES[i]);
            textView_name.setText(NAMES[i]);
            textView_phone.setText(PHONE[i]);
            textView_address.setText(ADDRESS[i]);
            textView_age.setText(AGE[i]);
            return view;
        }
    }
}