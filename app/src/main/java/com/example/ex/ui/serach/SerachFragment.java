package com.example.ex.ui.serach;

import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.ViewModelProviders;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;

import com.example.ex.R;

public class SerachFragment extends Fragment
        implements SearchView.OnQueryTextListener{

    private SerachViewModel mViewModel;
    private WebView mWebView;
    private WebSettings mWebSettings;
    private SearchView searchView;
//    private String words[] = {"렌즈1", "렌즈2"};
//    private ArrayAdapter<String> adapter;

    public static SerachFragment newInstance() {
        return new SerachFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.serach_fragment, container, false);

        searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        mWebView = root.findViewById(R.id.webView);
        mWebView.setWebViewClient(new WebViewClient());
        mWebSettings = mWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
//
//        androidx.appcompat.widget.SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
//        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, words);
//        searchAutoComplete.setAdapter(adapter);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SerachViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        mWebView.loadUrl("https://www.google.com/search?q="+s);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}
