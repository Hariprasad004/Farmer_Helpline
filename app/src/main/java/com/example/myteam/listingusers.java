package com.example.myteam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class listingusers extends AppCompatActivity {
    private RecyclerView mUsers_RecyclerView;
    List<User> mUser;
    private FirebaseFirestore fStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listingusers);
        mUsers_RecyclerView=(RecyclerView)findViewById(R.id.users_recyclerview);
        mUsers_RecyclerView.setLayoutManager(new LinearLayoutManager(this));
        String dec = getIntent().getStringExtra("dec");
//        fStore = FirebaseFirestore.getInstance();
//        CollectionReference cRef = fStore.collection("User");
//        Query query = cRef.whereEqualTo("FarmLend", "Farmer");
//        query.get().addOnSuccessListener(queryDocumentSnapshots -> {
//           if(!queryDocumentSnapshots.isEmpty()) {
//               List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
//               for (DocumentSnapshot document : list) {
//                   User user = new User();
//                   user.setfirstname(document.getString("Name"));
//                   user.setaddresslist(document.getString("Address"));
//                   user.setListage(document.getString("Age"));
//                   user.setcontactlist(document.getString("Phone"));
//                   user.setprofileImage(document.getString("Image"));
//                   mUser.add(user);
//               }
//               mUsers_RecyclerView.setAdapter(new UserAdapter(mUser));
//           }
//           else{
//               Intent intent = new Intent(listingusers.this, noLend.class);
//               intent.putExtra("User", "user");
//               startActivity(intent);
//               listingusers.this.finish();
//           }
//        }).addOnFailureListener(e -> {
//            Intent intent = new Intent(listingusers.this, noLend.class);
//            intent.putExtra("User", "user");
//            startActivity(intent);
//            listingusers.this.finish();
//        });

    }
    class UserAdapter extends RecyclerView.Adapter<UserViewHolder>{
        private List<User> users;
        public UserAdapter(List<User> mUsers){
            super();
            this.users = mUsers;
        }
        @NonNull
        @Override
        public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UserViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
            holder.bind(this.users.get(position));
        }

        @Override
        public int getItemCount() {
            return this.users.size();
        }
    }
    class UserViewHolder extends RecyclerView.ViewHolder{
        private TextView Name, Phone, Address, Age;
        private ImageView ProfileImg;
        public UserViewHolder(ViewGroup container) {
            super(LayoutInflater.from(listingusers.this).inflate(R.layout.list_item, container, false));
            Name = (TextView) itemView.findViewById(R.id.name);
            Age = (TextView) itemView.findViewById(R.id.listage);
            Address = (TextView) itemView.findViewById(R.id.addresslist);
            Phone = (TextView) itemView.findViewById(R.id.contactlist);
            ProfileImg = (ImageView) itemView.findViewById(R.id.listprof);
        }
        public  void bind(User user){
            Name.setText(user.getmName());
            Age.setText(user.getmage());
            Phone.setText(user.getmcontact());
            Address.setText(user.getmaddress());
            String uri = user.getimage();
            Picasso.get().load(uri).into(ProfileImg);
        }
    }
}
class User{
    private String  mName;
    private String mage;
    private String maddress;
    private String mcontact;
    private String mimage;

    public  String getmName(){

        return  mName;
    }
    public  void  setfirstname(String firstname){

        mName=firstname;
    }
    public  String getmage(){

        return  mage;
    }
    public  void  setListage(String listage){

        mage=listage;
    }
    public  String getmaddress(){

        return  maddress;
    }
    public  void  setaddresslist(String addresslist){

        maddress=addresslist;
    }
    public  String getmcontact(){

        return  mcontact;
    }
    public  void  setcontactlist(String contactlist){

        mcontact=contactlist;
    }
    public  String getimage(){

        return  mimage;
    }
    public  void  setprofileImage(String profile){

        mimage=profile;
    }
}

