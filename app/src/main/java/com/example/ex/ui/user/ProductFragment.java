package com.example.ex.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ex.R;

public class ProductFragment extends Fragment {
    //장바구니
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.product_list , container, false);
        // Recycler View - adapter - product.xml
        // 인 adapter 구현 그것만 하면 장바구니 끝 그리고 default 구현하면 완전 끝

        return root;

    }

}
