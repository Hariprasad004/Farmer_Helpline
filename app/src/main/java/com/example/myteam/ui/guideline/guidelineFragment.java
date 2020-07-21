package com.example.myteam.ui.guideline;

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

public class guidelineFragment extends Fragment {

    private guidelineViewModel guidelineViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        guidelineViewModel =
//                ViewModelProviders.of(this).get(guidelineViewModel.class);
        View root = inflater.inflate(R.layout.fragment_guideline, container, false);
//        final TextView textView = root.findViewById(R.id.nav_guideline);
//        guidelineViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
}