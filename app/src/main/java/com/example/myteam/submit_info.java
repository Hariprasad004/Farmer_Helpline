package com.example.myteam;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hbb20.CountryCodePicker;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class submit_info extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST_ACCESS_LOCATION = 1;
    public static final String TAG = "TAG";
    private static final int GPS_REQUEST_CODE = 9003;
    private EditText Name;
    private EditText number;
    private EditText Age;
    private EditText address;
    private RadioGroup radioGroup;
    private RadioButton radButton;
    private CountryCodePicker ccode;
    private Button submit;
    private LocationManager locationManager;
    private StorageReference SReference;
    ProgressDialog progressDialog;
    private String userId;
    Double final_latitude = 0.0, final_longitude = 0.0;
    boolean addr = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_info);
        Name = (EditText) findViewById(R.id.name);
        number = (EditText) findViewById(R.id.phoneText);
        Age = (EditText) findViewById(R.id.age);
        address = (EditText) findViewById(R.id.add);
        submit = (Button) findViewById(R.id.sub);
        ccode = findViewById(R.id.ccp);
        radioGroup = findViewById(R.id.radbtn);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getSupportActionBar().setTitle("Details of the User");
        isGPSEnabled();
        requestPermission();
        final FirebaseAuth fAuth = FirebaseAuth.getInstance();
        final FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        SReference = FirebaseStorage.getInstance().getReference();
        submit.setOnClickListener(v -> {
            progressDialog = new ProgressDialog(submit_info.this);
            //show dialog
            progressDialog.show();
            //set content view
            progressDialog.setContentView(R.layout.progress_dialog);
            //set transparent background
            progressDialog.getWindow().setBackgroundDrawableResource(
                    android.R.color.transparent
            );
            int radioId = radioGroup.getCheckedRadioButtonId();
            String NAme = Name.getText().toString().trim();
            String num = number.getText().toString().trim();
            String person_age = Age.getText().toString().trim();
            String person_address = address.getText().toString().trim();
            String code = ccode.getSelectedCountryCode();
            String PNum = "+" + code + num;
            userId = fAuth.getCurrentUser().getUid();
            if (NAme.isEmpty()) {
                progressDialog.dismiss();
                Name.setError("Enter the name");
                return;
            }
            if (num.length() != 10) {
                progressDialog.dismiss();
                number.setError("Enter the valid phone number");
                return;
            } else if (person_age.isEmpty()) {
                progressDialog.dismiss();
                Age.setError("Enter the age");
                return;
            } else if (radioId == -1) {
                progressDialog.dismiss();
                ;
                Toast.makeText(submit_info.this, "Select whether lender or farmer", Toast.LENGTH_SHORT).show();
                return;
            } else if (person_address.isEmpty()) {
                progressDialog.dismiss();
                address.setError("Enter the address with pincode");
                return;
            } else {
                addr = false;
                requestPermission();
                radButton = findViewById(radioId);
                String farmlend = radButton.getText().toString();
                DocumentReference documentReference = fStore.collection("User").document(userId);
                Map<String, Object> user = new HashMap<>();
                user.put("Name", NAme);
                user.put("Phone_Number", PNum);
                user.put("Age", person_age);
                user.put("FarmLend", farmlend);
                user.put("Latitude", final_latitude);
                user.put("Longitude", final_longitude);
                user.put("Address", person_address);
                user.put("Image", "https://firebasestorage.googleapis.com/v0/b/my-team-62402.appspot.com/o/profile.jpg?alt=media&token=472e5f25-48b7-4b4e-b601-df7b0a8c96ba");
                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(submit_info.this, "User profile is created", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "User profile is created");
                        Intent intent = new Intent(submit_info.this, menu.class);
                        progressDialog.dismiss();
                        startActivity(intent);
                        submit_info.this.finish();
                    }
                });
            }

        });
    }


    //-----------Location Access------------
    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(submit_info.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_REQUEST_ACCESS_LOCATION);
        } else
            getCurrentLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_ACCESS_LOCATION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                requestPermission();
            }
        }
    }

    private void getCurrentLocation() {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        LocationServices.getFusedLocationProviderClient(submit_info.this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(submit_info.this).removeLocationUpdates(this);
                if(locationResult!= null && locationResult.getLocations().size() > 0){
                    int latestlocationindex = locationResult.getLocations().size() - 1;
                    final_longitude = locationResult.getLocations().get(latestlocationindex).getLongitude();
                    final_latitude = locationResult.getLocations().get(latestlocationindex).getLatitude();
                    Log.d(TAG, String.valueOf(final_latitude));
                    Log.d(TAG, String.valueOf(final_longitude));
                    if(addr)
                    getCompleteAddress(final_latitude, final_longitude);
                }
            }
        }, Looper.getMainLooper());

    }

    private void isGPSEnabled() {
        boolean providerEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (providerEnabled) {
            return;
        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setTitle("GPS Permission")
                    .setMessage("GPS is required for this app to work. Please enable GPS.")
                    .setPositiveButton("OK", ((dialogInterface, i) -> {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, GPS_REQUEST_CODE);
                    }))
                    .setCancelable(false)
                    .show();
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
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}
