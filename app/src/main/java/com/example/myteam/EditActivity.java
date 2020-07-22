package com.example.myteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditActivity extends AppCompatActivity {
    private FirebaseFirestore fStore;
    private FirebaseAuth fAuth;
    private ImageView profileimg;
    private EditText name;
    private EditText phone;
    private EditText addr;
    private EditText age;
    private Button update;
    private String userid;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        age = findViewById(R.id.age);
        addr = findViewById(R.id.address);
        update = findViewById(R.id.update);


        userid = fAuth.getCurrentUser().getUid();
        DocumentReference dr = fStore.collection("User").document(userid);
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                name.setText(documentSnapshot.getString("Name"));
                phone.setText(documentSnapshot.getString("Phone_Number"));
                age.setText(documentSnapshot.getString("Age"));
                addr.setText(documentSnapshot.getString("Address"));
            }
        }).addOnFailureListener(e ->
                Toast.makeText(EditActivity.this, "Error", Toast.LENGTH_SHORT).show());


        update.setOnClickListener(v -> {
            progressDialog = new ProgressDialog(EditActivity.this);
            //show dialog
            progressDialog.show();
            //set content view
            progressDialog.setContentView(R.layout.progress_dialog);
            //set transparent background
            progressDialog.getWindow().setBackgroundDrawableResource(
                    android.R.color.transparent
            );
            String Name = name.getText().toString().trim();
            String Phone = phone.getText().toString().trim();
            String Age = age.getText().toString().trim();
            String Address = addr.getText().toString().trim();

            if (Name.isEmpty()) {
                progressDialog.dismiss();
                name.setError("Name cannot be empty");
                return;
            }
            if (Phone.isEmpty()) {
                progressDialog.dismiss();
                phone.setError("Phone cannot be empty");
                return;
            }
            if (Age.isEmpty()) {
                progressDialog.dismiss();
                age.setError("Age cannot be empty");
                return;
            }
            if (Address.isEmpty()) {
                progressDialog.dismiss();
                addr.setError("Address cannot be empty");
                return;
            }
        });

    }
}