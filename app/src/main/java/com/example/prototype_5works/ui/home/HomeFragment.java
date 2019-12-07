package com.example.prototype_5works.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.prototype_5works.R;
import com.example.prototype_5works.ui.serach.SerachViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private SerachViewModel mViewModel;
    private WebView mWebView;
    private WebSettings mWebSettings;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mWebView = root.findViewById(R.id.webViewHome);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);

        mWebView.loadUrl("https://ec2-13-125-251-84.ap-northeast-2.compute.amazonaws.com/");
        return root;
    }
}