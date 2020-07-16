package com.example.myteam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.example.myteam.R.id.full_name;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Profileu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Profileu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // Declared Variables
//    private TextView name1, name2, phone, age, addr;
//    private Button edit, change, req;
//    private ImageView img;
//    private FirebaseAuth fAuth;
//    private FirebaseFirestore fStore;
//    private String dec;

    public Profileu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Profileu.
     */
    // TODO: Rename and change types and number of parameters
    public static Profileu newInstance(String param1, String param2) {
        Profileu fragment = new Profileu();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profileu, container, false);
//        name1 = view.findViewById(R.id.full_name);
//        name2 = view.findViewById(R.id.na);
//        age = view.findViewById(R.id.age);
//        phone = view.findViewById(R.id.ph_num);
//        addr = view.findViewById(R.id.add);
//        img = view.findViewById(R.id.profileimage);
//        edit = view.findViewById(R.id.edb);
//        change = view.findViewById(R.id.edc);
//        req = view.findViewById(R.id.edr);
//        fAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();


//        edit.setOnClickListener(v -> {
//            Intent intent = new Intent(getActivity(),EditActivity.class);
//            startActivity(intent);
//        });
//
//        req.setOnClickListener(v -> {
//            AlertDialog.Builder alert= new AlertDialog.Builder(getActivity());
//            alert.setTitle("Request");
//            alert.setMessage("Search nearby lenders ?");
//            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
////                    dec = "Yes";
////                    Intent intent = new Intent(getActivity(), ListOfUsers.class);
////                    intent.putExtra("Decision",dec);
////                    startActivity(intent);
//                }
//            });
//            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
////                    dec = "No";
////                    Intent intent = new Intent(getActivity(), ListOfUsers.class);
////                    intent.putExtra("Decision",dec);
////                    startActivity(intent);
//                }
//            });
//            alert.create().show();
//        });
        return view;
    }
}