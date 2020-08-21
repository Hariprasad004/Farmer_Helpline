package com.example.myteam;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    private FirebaseAuth fAuth;
    private StorageReference SReference;
    private ImageView profileimg;
    private EditText name;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EditActivity.this, menu.class);
        startActivity(intent);
        EditActivity.this.finish();
    }


    private EditText phone;
    private EditText addr;
    private EditText age;
    private Button update;
    private String userid, url;
    private  boolean flag = false;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        fAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        SReference = FirebaseStorage.getInstance().getReference();
        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        age = findViewById(R.id.age);
        addr = findViewById(R.id.address);
        update = (Button) findViewById(R.id.update);
        profileimg = findViewById(R.id.profileimage);
        userid = fAuth.getCurrentUser().getUid();

        DocumentReference dr = fStore.collection("User").document(userid);
        dr.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String uri = documentSnapshot.getString("Image");
                Picasso.get().load(uri).into(profileimg);
                name.setText(documentSnapshot.getString("Name"));
                phone.setText(documentSnapshot.getString("Phone_Number"));
                age.setText(documentSnapshot.getString("Age"));
                addr.setText(documentSnapshot.getString("Address"));
            }
        }).addOnFailureListener(e ->
                Toast.makeText(EditActivity.this, "Error", Toast.LENGTH_SHORT).show());


        profileimg.setOnClickListener(v -> {
            //Open Gallery
            Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGallery, 1000);

        });


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
            DocumentReference docRef = fStore.collection("User").document(userid);
            Map<String, Object> edited = new HashMap<>();
            edited.put("Name", Name);
            edited.put("Phone_Number", Phone);
            edited.put("Age", Age);
            edited.put("Address", Address);
            if(flag)
                edited.put("Image", url);
            docRef.update(edited).addOnSuccessListener(aVoid -> {
                progressDialog.dismiss();
                Toast.makeText(EditActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(EditActivity.this, menu.class);
                startActivity(intent);
                EditActivity.this.finish();;
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(EditActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageuri = data.getData();
                uploadImage(imageuri);
            }
        }
    }

    private void uploadImage(Uri imageuri) {
        progressDialog = new ProgressDialog(EditActivity.this);
        //show dialog
        progressDialog.show();
        //set content view
        progressDialog.setContentView(R.layout.progress_dialog);
        //set transparent background
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        StorageReference fileRef = SReference.child("Users/"+userid+"/Profile.jpg");
        fileRef.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    Picasso.get().load(uri).into(profileimg);
                    url = uri.toString();
                    flag = true;
                    progressDialog.dismiss();
                });
                Toast.makeText(EditActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(EditActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }
}