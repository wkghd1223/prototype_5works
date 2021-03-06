package com.example.prototype_5works.ui.camera;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prototype_5works.R;

public class CameraViewModel extends ViewModel implements View.OnClickListener {

    private MutableLiveData<String> mText;
    public CameraViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("default_lenze");
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
