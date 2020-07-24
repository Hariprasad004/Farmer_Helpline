package com.example.myteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class signActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 104;
    private EditText email_txt;
private EditText password_txt;
private EditText confirmpass;
private Button signup;
private TextView al_login;
private Button cphone;
private Button cemail;
private FirebaseFirestore fStore;
ProgressDialog progressDialog;
GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        getSupportActionBar().setTitle("SIGNUP");
        email_txt = (EditText) findViewById(R.id.emails);
        password_txt = (EditText) findViewById(R.id.pass);
        confirmpass = (EditText) findViewById(R.id.confirm);
        signup = (Button) findViewById(R.id.signup);
        cphone = (Button) findViewById(R.id.cnphone);
        cemail = (Button) findViewById(R.id.cnemail);
        al_login = (TextView) findViewById(R.id.allrdy_login);

        final FirebaseAuth fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(signActivity.this);
                //show dialog
                progressDialog.show();
                //set content view
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                String email = email_txt.getText().toString().trim();
                String password = password_txt.getText().toString().trim();
                String confpass = confirmpass.getText().toString().trim();
                if (TextUtils.isEmpty((email))) {
                    progressDialog.dismiss();
                    Toast.makeText(signActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    progressDialog.dismiss();
                    Toast.makeText(signActivity.this, "Enter the valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty((password))) {
                    progressDialog.dismiss();
                    Toast.makeText(signActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty((confpass))) {
                    progressDialog.dismiss();
                    Toast.makeText(signActivity.this, "Please enter Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6) {
                    progressDialog.dismiss();
                    Toast.makeText(signActivity.this, "Password length should be greater than 6", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.equals(confpass)) {
                    fAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(signActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()) {
                                                    progressDialog.dismiss();
                                                    Toast.makeText(signActivity.this, "User successfully registered, Please verify your email", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(signActivity.this, LoginActivity.class);
                                                    startActivity(intent);
                                                    signActivity.this.finish();
                                                }
                                                else{
                                                    progressDialog.dismiss();
                                                    Toast.makeText(signActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(signActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(signActivity.this, "Password and confirm password doesn't match", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
        cphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signActivity.this, phone.class);
                startActivity(intent);
            }
        });
        al_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(signActivity.this, LoginActivity.class);
                startActivity(intent);
                signActivity.this.finish();
            }
        });
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        cemail.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                progressDialog = new ProgressDialog(signActivity.this);
                //show dialog
                progressDialog.show();
                //set content view
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                signIn();
            }
        });
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        final FirebaseAuth fAuth = FirebaseAuth.getInstance();
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(signActivity.this, user.getEmail()+" : "+user.getDisplayName(), Toast.LENGTH_SHORT).show();
                            String us = user.getUid();
                            fStore.collection("User").document(us).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.getResult().exists()){
                                        Intent intent = new Intent(signActivity.this, menu.class);
                                        progressDialog.dismiss();
                                        startActivity(intent);
                                        signActivity.this.finish();
                                    }else{
                                        Intent intent = new Intent(signActivity.this, submit_info.class);
                                        progressDialog.dismiss();
                                        startActivity(intent);
                                        signActivity.this.finish();
                                    }
                                }
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(signActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}