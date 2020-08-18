package com.example.myteam;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;
    ArrayList<prof> profiles;
    Activity activity;

    public MyAdapter(Context c, Activity a, ArrayList<prof> p){
        context = c;
        activity = a;
        profiles = p;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.user.setText(profiles.get(position).getid());
        holder.Name.setText(profiles.get(position).getName());
        holder.Age.setText(profiles.get(position).getAge());
        holder.Phone.setText(profiles.get(position).getContact());
        holder.Address.setText(profiles.get(position).getAddres());
        Picasso.get().load(profiles.get(position).getPROFILE_PIC()).into(holder.photo);
        holder.full.setVisibility(View.VISIBLE);
        holder.onClick(position);
    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView Name, Phone, Age, Address,user;
        ImageView photo;
        LinearLayout full;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            user = (TextView) itemView.findViewById(R.id.userid) ;
            Name = (TextView) itemView.findViewById(R.id.name);
            Phone = (TextView) itemView.findViewById(R.id.contact);
            Age = (TextView) itemView.findViewById(R.id.age);
            Address = (TextView) itemView.findViewById(R.id.addr);
            photo = (ImageView) itemView.findViewById(R.id.profile_pic);
            full = (LinearLayout) itemView.findViewById(R.id.list);
        }
        public void onClick(final int position){
            full.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    String userid = firebaseAuth.getCurrentUser().getUid();
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("Request");
                    alert.setMessage("Do you want to request "+Name.getText().toString().trim() +"?");
                    alert.setPositiveButton("Yes", (dialog, which) -> {
                        int permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS);
                        if(permissionCheck == PackageManager.PERMISSION_GRANTED){
                            String phoneNo = Phone.getText().toString().trim();
                            String SMS = "Hello " + Name.getText().toString().trim() +".\nThere is a request by " + userid;
                            try {
                                SmsManager smsManager = SmsManager.getDefault();
                                smsManager.sendTextMessage(phoneNo, null, SMS, null, null);
                                Toast.makeText(context, "Message is sent.", Toast.LENGTH_SHORT).show();
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(context, "Failed to send message.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, 0);
                        }
                    });
                    alert.setNegativeButton("No", (dialog, which) -> {

                    });
                    alert.create().show();
                }
            });
        }
    }
}
