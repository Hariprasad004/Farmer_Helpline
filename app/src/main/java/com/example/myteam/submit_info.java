package com.example.myteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class submit_info extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_ACCESS_LOCATION = 1;
    private EditText Name;
    private EditText number;
    private EditText Age;
    private EditText address;
    private RadioGroup radioGroup;
    private RadioButton radButton;
    private CountryCodePicker ccode;
    private Button submit;
    private FusedLocationProviderClient fusedLocationProviderClient;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_info);
        Name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.phoneText);
        Age = (EditText) findViewById(R.id.age);
        address = (EditText) findViewById(R.id.add);
        submit = (Button) findViewById(R.id.sub);
        ccode=findViewById(R.id.ccp);
        radioGroup = findViewById(R.id.radbtn);
        getSupportActionBar().setTitle("Details of the User");

        //Initialize fusedLocationProviderClient
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();

        final FirebaseAuth fAuth = FirebaseAuth.getInstance();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(submit_info.this);
                //show dialog
                progressDialog.show();
                //set content view
                progressDialog.setContentView(R.layout.progress_dialog);
                //set transparent background
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
                String NAme = Name.getText().toString().trim();
                String num = number.getText().toString().trim();
                String person_age = Age.getText().toString().trim();
                String person_adress = address.getText().toString().trim();
                String code = ccode.getSelectedCountryCode();
                String PNum = "+"+code+num;

            }
        });
    }

    private void fetchLocation() {
        //If permission is not granted
        if (ContextCompat.checkSelfPermission(
                submit_info.this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                submit_info.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(submit_info.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION},
                MY_PERMISSION_REQUEST_ACCESS_LOCATION);
        }else{
            //If permission is already granted
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                Double latitude = location.getLatitude();
                                Double longitude = location.getLongitude();
                                getCompleteAddress(latitude, longitude);

                            }
                        }
                    });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == MY_PERMISSION_REQUEST_ACCESS_LOCATION){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                fetchLocation();
            }else{

            }
        }
    }

    private void getCompleteAddress(double Latitude, double Longitude){
        String add="";
        Geocoder geocoder = new Geocoder(submit_info.this, Locale.getDefault());
        try{
            List<Address> addr = geocoder.getFromLocation(Latitude, Longitude, 1);
            if(add!=null){
                Address returnAddress = addr.get(0);
                StringBuilder retaddr = new StringBuilder("");
                for(int i=0; i<=returnAddress.getMaxAddressLineIndex(); i++){
                    retaddr.append(returnAddress.getAddressLine(i)).append("\n");
                }
                add =  retaddr.toString();
                address.setText(add);
            }
        }
        catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }

    }
}
