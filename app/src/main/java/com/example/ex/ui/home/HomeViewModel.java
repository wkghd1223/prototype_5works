package com.example.ex.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Best contents\n" +
                "...\n" +
                "New\n" +
                "...\n" +
                "Magazine\n" +
                "...");
    }

    public LiveData<String> getText() {
        return mText;
    }
}