package com.example.myteam.ui.guideline;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GuidelineViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GuidelineViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is guideline fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}