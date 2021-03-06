package com.example.myteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private EditText emailenter;
    private EditText pass;
    private Button login;
    private TextView ftpass;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailenter = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.passwrd);
        login = (Button) findViewById(R.id.loginid);
        ftpass = (TextView) findViewById(R.id.fgtpass);
        getSupportActionBar().setTitle("Login");

        final FirebaseAuth fAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Initiatializw progress dailogue
                progressDialog = new ProgressDialog(LoginActivity.this);
                //show dialog
                progressDialog.show();
                //set content view
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                String email = emailenter.getText().toString().trim();
                String password = pass.getText().toString().trim();
                if (TextUtils.isEmpty((email))) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty((password))) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fAuth.getCurrentUser() == null) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "User doesn't exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(fAuth.getCurrentUser().isEmailVerified()){
                                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                String user = fAuth.getCurrentUser().getUid();
                                fStore.collection("User").document(user).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        try {
                                            if (task.getResult().exists()) {
                                                Intent intent = new Intent(LoginActivity.this, menu.class);
                                                progressDialog.dismiss();
                                                startActivity(intent);
                                                LoginActivity.this.finish();
                                            } else {
                                                Intent intent = new Intent(LoginActivity.this, submit_info.class);
                                                progressDialog.dismiss();
                                                startActivity(intent);
                                                LoginActivity.this.finish();
                                            }
                                        }
                                        catch (Exception e){
                                            Intent intent = new Intent(LoginActivity.this, submit_info.class);
                                            progressDialog.dismiss();
                                            startActivity(intent);
                                            LoginActivity.this.finish();
                                        }
                                    }
                                });
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Please verify your email address", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        ftpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, fgpass.class);
                startActivity(intent);
                LoginActivity.this.finish();
            }
        });
    }
}