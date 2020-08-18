package com.example.myteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class listview extends AppCompatActivity {
    private static final String TAG = "TAG";
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    RecyclerView recyclerView;
    ArrayList<prof> list;
    MyAdapter adapter;
    ProgressDialog progressDialog;
    Double centerLatitude;
    Double centerLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);
        progressDialog = new ProgressDialog(listview.this);
        //show dialog
        progressDialog.show();
        //set content view
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        String dec = getIntent().getStringExtra("Decision");
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<prof>();
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        CollectionReference cRef = fStore.collection("User");
        if (dec.equals("No")) {
            Query query = cRef.whereEqualTo("FarmLend", "Lender");
            query.get().addOnSuccessListener(queryDocumentSnapshots -> {
                if (!queryDocumentSnapshots.isEmpty()) {
                    for (DocumentSnapshot dataSnapshot : queryDocumentSnapshots.getDocuments()) {
                        prof p = new prof();
                        p.setid(dataSnapshot.getId());
                        p.setName(dataSnapshot.getString("Name"));
                        p.setAddres(dataSnapshot.getString("Address"));
                        p.setAge(dataSnapshot.getString("Age"));
                        p.setContact(dataSnapshot.getString("Phone_Number"));
                        p.setPROFILE_PIC(dataSnapshot.getString("Image"));
                        list.add(p);
                    }
                    adapter = new MyAdapter(listview.this, this, list);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                } else {
                    Intent intent = new Intent(listview.this, noLend.class);
                    intent.putExtra("User", "lender");
                    progressDialog.dismiss();
                    startActivity(intent);
                    listview.this.finish();
                }
            }).addOnFailureListener(e -> {
                Intent intent = new Intent(listview.this, noLend.class);
                intent.putExtra("User", "lender");
                progressDialog.dismiss();
                startActivity(intent);
                listview.this.finish();
            });
        }
        else{
            String userid = fAuth.getCurrentUser().getUid();
            DocumentReference dr = fStore.collection("User").document(userid);
            dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                private static final String TAG = "TAG";

                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    centerLatitude = documentSnapshot.getDouble("Latitude");
                    centerLongitude = documentSnapshot.getDouble("Longitude");
                    Log.d(TAG, String.valueOf(centerLongitude));
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Intent intent = new Intent(listview.this, noLend.class);
                    intent.putExtra("User", "lender");
                    progressDialog.dismiss();
                    startActivity(intent);
                    listview.this.finish();
                }
            });
            Query query = cRef.whereEqualTo("FarmLend", "Lender");
            query.get().addOnSuccessListener(queryDocumentSnapshots -> {
                if (!queryDocumentSnapshots.isEmpty()) {
                    try {
                        for (DocumentSnapshot dataSnapshot : queryDocumentSnapshots.getDocuments()) {
                            float[] results = new float[1];
                            Location.distanceBetween(centerLatitude, centerLongitude, dataSnapshot.getDouble("Latitude"), dataSnapshot.getDouble("Longitude"), results);
                            float distanceInMeters = results[0];
                            boolean isWithin20km = distanceInMeters < 20000;
                            if (isWithin20km) {
                                prof p = new prof();
                                p.setid(dataSnapshot.getId());
                                p.setName(dataSnapshot.getString("Name"));
                                p.setAddres(dataSnapshot.getString("Address"));
                                p.setAge(dataSnapshot.getString("Age"));
                                p.setContact(dataSnapshot.getString("Phone_Number"));
                                p.setPROFILE_PIC(dataSnapshot.getString("Image"));
                                list.add(p);
                            }
                        }
                    }
                    catch(Exception e){
                        Intent intent = new Intent(listview.this, noLend.class);
                        intent.putExtra("User", "lender");
                        progressDialog.dismiss();
                        startActivity(intent);
                        listview.this.finish();
                    }
                    adapter = new MyAdapter(listview.this, this, list);
                    recyclerView.setAdapter(adapter);
                    progressDialog.dismiss();
                } else {
                    Intent intent = new Intent(listview.this, noLend.class);
                    intent.putExtra("User", "lender");
                    progressDialog.dismiss();
                    startActivity(intent);
                    listview.this.finish();
                }
            }).addOnFailureListener(e -> {
                Intent intent = new Intent(listview.this, noLend.class);
                intent.putExtra("User", "lender");
                progressDialog.dismiss();
                startActivity(intent);
                listview.this.finish();
            });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0){
            if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            }
            else {
                Toast.makeText(this, "Failed to send the message", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Failed to send the message", Toast.LENGTH_SHORT).show();
        }
    }
}