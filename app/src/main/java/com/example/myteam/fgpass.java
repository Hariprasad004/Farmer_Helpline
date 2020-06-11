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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class fgpass extends AppCompatActivity {
    private EditText emailenter;
    private Button resetpass;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);
        emailenter=(EditText)findViewById(R.id.emailreset);
        resetpass=(Button)findViewById(R.id.login);

        final FirebaseAuth fAuth = FirebaseAuth.getInstance();

        resetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(fgpass.this);
                //show dialog
                progressDialog.show();
                //set content view
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                String mail = emailenter.getText().toString().trim();
                if (TextUtils.isEmpty((mail))) {
                    progressDialog.dismiss();
                    Toast.makeText(fgpass.this, "Please enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                fAuth.sendPasswordResetEmail(mail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(fgpass.this, "Password reset email has been sent", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(fgpass.this, LoginActivity.class);
                            startActivity(intent);
                            fgpass.this.finish();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(fgpass.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}
