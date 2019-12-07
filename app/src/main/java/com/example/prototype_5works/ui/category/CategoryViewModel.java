package com.example.prototype_5works.ui.category;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.prototype_5works.R;

public class CategoryViewModel extends ViewModel implements View.OnClickListener {

    private MutableLiveData<String> mText;
    public CategoryViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("원하는 카테고리를 선택해 주세요");
    }

    public LiveData<String> getText() {
        return mText;
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button) mText.setValue("best");
        else if (view.getId() == R.id.button2) mText.setValue("brand");
        else if(view.getId() == R.id.button3) mText.setValue("style");
    }
}