package com.example.myteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signActivity extends AppCompatActivity {
private EditText email_txt;
private EditText password_txt;
private EditText confirmpass;
private Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        email_txt = (EditText) findViewById(R.id.emails);
        password_txt = (EditText) findViewById(R.id.pass);
        confirmpass = (EditText) findViewById(R.id.confirm);
        signup = (Button) findViewById(R.id.signup);

        final FirebaseAuth fAuth = FirebaseAuth.getInstance();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_txt.getText().toString().trim();
                String password = password_txt.getText().toString().trim();
                String confpass = confirmpass.getText().toString().trim();
                if (TextUtils.isEmpty((email))) {
                    Toast.makeText(signActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty((password))) {
                    Toast.makeText(signActivity.this, "Please enter Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty((confpass))) {
                    Toast.makeText(signActivity.this, "Please enter Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6)
                    Toast.makeText(signActivity.this, "Password length should be greater than 6", Toast.LENGTH_SHORT).show();
                if (password.equals(confpass)) {
                    fAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(signActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(signActivity.this, "User successfully registered", Toast.LENGTH_SHORT).show();
                                        Intent intent =new Intent(signActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        signActivity.this.finish();
                                    } else {
                                        Toast.makeText(signActivity.this, "Aunthentication failed", Toast.LENGTH_SHORT).show();
                                    }

                                    // ...
                                }
                            });
                }
                else
                    Toast.makeText(signActivity.this, "Password and confirm password doesn't match", Toast.LENGTH_SHORT).show();
            }
        });
    }
}