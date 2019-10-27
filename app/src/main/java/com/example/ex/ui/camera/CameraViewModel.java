package com.example.ex.ui.camera;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ex.R;

import org.opencv.core.Mat;

public class CameraViewModel extends ViewModel implements View.OnClickListener {

    private MutableLiveData<String> mText;
    public CameraViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("lenze.jpg");
    }
    public LiveData<String> getText() {
        return mText;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.default_lenze) mText.setValue("default_lenze");
        else if(view.getId() == R.id.lenze1_jpg) mText.setValue("lenze.jpg");
    }
}
