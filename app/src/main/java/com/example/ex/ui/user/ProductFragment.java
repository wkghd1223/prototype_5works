package com.example.ex.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ex.R;

import java.util.ArrayList;

public class ProductFragment extends Fragment {
    //장바구니
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.product_view , container, false);
        RecyclerView pRecyclerView =  root.findViewById(R.id.pRecycler);

        ArrayList<ProductList> pList = new ArrayList<ProductList>();
        pRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        pList.add(new ProductList(getResources().getDrawable(R.drawable.ic_lense),"lens","10,000","hallo"));
        pList.add(new ProductList(getResources().getDrawable(R.drawable.ic_hamberger_menu),"lens_set","50,000","hallow"));

        ProductAdapter pAdapter = new ProductAdapter(pList);
        pRecyclerView.setAdapter(pAdapter);

        return root;
    }
}
