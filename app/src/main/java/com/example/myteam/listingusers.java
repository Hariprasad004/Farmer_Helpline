package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class listingusers extends AppCompatActivity {
    private RecyclerView mUsers_RecyclerView;
    private List<User> mUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listingusers);
        mUsers_RecyclerView=(RecyclerView)findViewById(R.id.users_recyclerview);
        mUsers_RecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
class User{
    private String  mName;
    private String mage;
    private String maddress;
    private String mcontact;

    public  String getmName(){
        return  mName;
    }
    public  void  setfirstname(String firstname){
        mName=firstname;
    }
    public  String getmage(){
        return  mage;
    }
    public  void  setListage(String listage){
        mage=listage;
    }
    public  String getmaddress(){
        return  maddress;
    }
    public  void  setaddresslist(String addresslist){
        maddress=addresslist;
    }
    public  String getmcontact(){
        return  mcontact;
    }
    public  void  setcontactlist(String contactlist){
        mcontact=contactlist;
    }
}