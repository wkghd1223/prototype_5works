package com.example.prototype_5works.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
// 지금 안씀
import com.example.prototype_5works.R;

public class CategoryFragment extends Fragment {

    private CategoryViewModel categoryViewModel;
    private WebView mWebView;
    private WebSettings mWebSettings;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        categoryViewModel =
                ViewModelProviders.of(this).get(CategoryViewModel.class);
        View root = inflater.inflate(R.layout.category_fragment, container, false);
        final Button button1 =  root.findViewById(R.id.button);
        final Button button2 =  root.findViewById(R.id.button2);
        final Button button3 =  root.findViewById(R.id.button3);

        mWebView = root.findViewById(R.id.webViewCategory);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        button1.setOnClickListener(categoryViewModel);
        button2.setOnClickListener(categoryViewModel);
        button3.setOnClickListener(categoryViewModel);

        categoryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mWebView.loadUrl("https://www.google.com/search?q="+s);
            }
        });
        return root;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        // TODO: Use the ViewModel
    }

}