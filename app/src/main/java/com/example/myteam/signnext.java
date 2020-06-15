package com.example.myteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class signnext extends AppCompatActivity {
    String verificationcodebysystem;
    private EditText et_otpNumber;
    private TextView otpVerification;
    private EditText enterOtp;
    private Button submit;
    private TextView resendOtp;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        //getSupportActionBar().setTitle("OTP Verification");
        getSupportActionBar().hide();


        et_otpNumber = findViewById(R.id.et_otpNumber);
        submit=findViewById(R.id.sub);
        String phone = getIntent().getStringExtra("PhoneNo");
        sendVerificationCodeToUser(phone);

    }
    private void sendVerificationCodeToUser(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationcodebysystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                progressDialog = new ProgressDialog(signnext.this);
                //show dialog
                progressDialog.show();
                //set content view
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                verfyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(signnext.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verfyCode(String codebyUser){
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationcodebysystem, codebyUser);
        signIntheUserbyCredential(credential);
    }

    private <VerifyPhoneNo> void signIntheUserbyCredential(PhoneAuthCredential credential) {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        fAuth.signInWithCredential(credential).addOnCompleteListener(signnext.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Intent intent = new Intent(signnext.this, submit_info.class);
                    startActivity(intent);
                    signnext.this.finish();
                }
                else{
                    progressDialog.dismiss();
                    Toast.makeText(signnext.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
