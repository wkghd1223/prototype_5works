package com.example.prototype_5works.ui.dashboard;

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

import com.example.prototype_5works.R;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        final Button button1 =  root.findViewById(R.id.button);
        final Button button2 =  root.findViewById(R.id.button2);
        final Button button3 =  root.findViewById(R.id.button3);

        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                button1.setOnClickListener(dashboardViewModel);
                button2.setOnClickListener(dashboardViewModel);
                button3.setOnClickListener(dashboardViewModel);
            }
        });
        return root;
    }
}