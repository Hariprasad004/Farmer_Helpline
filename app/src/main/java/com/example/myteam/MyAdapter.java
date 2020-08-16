package com.example.myteam;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    Context context;
    ArrayList<prof> profiles;

    public MyAdapter(Context c, ArrayList<prof> p){
        context = c;
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
                    Toast.makeText(context, position+" is checked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
