package com.example.ex.ui.category;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.ex.R;

public class CategoryFragment extends Fragment {

    private CategoryViewModel categoryViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoryViewModel =
                ViewModelProviders.of(this).get(CategoryViewModel.class);
        View root = inflater.inflate(R.layout.category_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_category);
        final Button button1 =  root.findViewById(R.id.button);
        final Button button2 =  root.findViewById(R.id.button2);
        final Button button3 =  root.findViewById(R.id.button3);

        categoryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                button1.setOnClickListener(categoryViewModel);
                button2.setOnClickListener(categoryViewModel);
                button3.setOnClickListener(categoryViewModel);
            }
        });
        return root;
    }
}