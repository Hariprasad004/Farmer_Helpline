package com.example.myteam.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.myteam.R;

public class aboutFragment extends Fragment {

    private aboutViewModel aboutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        aboutViewModel =
//                ViewModelProviders.of(this).get(aboutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_about, container, false);
//        final TextView textView = root.findViewById(R.id.nav_about);
//        aboutViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return root;
    }
}