package com.example.myteam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

public class listview extends AppCompatActivity {
    FirebaseFirestore fStore;
    RecyclerView recyclerView;
    ArrayList<prof> list;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        String dec = getIntent().getStringExtra("dec");
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<prof>();
        fStore = FirebaseFirestore.getInstance();
        CollectionReference cRef = fStore.collection("User");
        Query query = cRef.whereEqualTo("FarmLend", "Farmer");
        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
           if(!queryDocumentSnapshots.isEmpty()) {
                for(DocumentSnapshot dataSnapshot:queryDocumentSnapshots.getDocuments()){
                    prof p = new prof();
                    p.setName(dataSnapshot.getString("Name"));
                    p.setAddres(dataSnapshot.getString("Address"));
                    p.setAge(dataSnapshot.getString("Age"));
                    p.setContact(dataSnapshot.getString("Phone"));
                    p.setPROFILE_PIC(dataSnapshot.getString("Image"));
                    list.add(p);
                }
                adapter = new MyAdapter(listview.this, list);
                recyclerView.setAdapter(adapter);
           }
           else{
               Intent intent = new Intent(listview.this, noLend.class);
               intent.putExtra("User", "lender");
               startActivity(intent);
               listview.this.finish();
           }
        }).addOnFailureListener(e -> {
            Intent intent = new Intent(listview.this, noLend.class);
            intent.putExtra("User", "lender");
            startActivity(intent);
            listview.this.finish();
        });

    }
}