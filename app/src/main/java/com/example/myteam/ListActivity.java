package com.example.myteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ListActivity extends AppCompatActivity {
    private TextView name, phone, age, addr;
    private FirebaseFirestore fStore;
    private StorageReference SReference;
    private ImageView profileimg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        name = findViewById(R.id.na);
        phone = findViewById(R.id.ph_num);
        age = findViewById(R.id.age);
        addr = findViewById(R.id.add);
        profileimg = findViewById(R.id.profileimage);
        String id = getIntent().getStringExtra("Id");
        fStore = FirebaseFirestore.getInstance();
        SReference = FirebaseStorage.getInstance().getReference();

        StorageReference profileRef = SReference.child("Users/"+id+"/Profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(uri -> {
            Picasso.get().load(uri).into(profileimg);
        });

        DocumentReference dr = fStore.collection("User").document(id);
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name.setText(documentSnapshot.getString("Name"));
                phone.setText(documentSnapshot.getString("Phone_Number"));
                age.setText(documentSnapshot.getString("Age"));
                addr.setText(documentSnapshot.getString("Address"));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ListActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }
}