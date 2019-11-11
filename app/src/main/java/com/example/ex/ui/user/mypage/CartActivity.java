package com.example.ex.ui.user.mypage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.ex.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        RecyclerView pRecyclerView =  findViewById(R.id.pRecycler);

        ArrayList<ProductList> pList = new ArrayList<ProductList>();
        pRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        pList.add(new ProductList(getResources().getDrawable(R.drawable.ic_lense),"lens","10,000","hallo"));
        pList.add(new ProductList(getResources().getDrawable(R.drawable.ic_hamberger_menu),"lens_set","50,000","hallow"));

        ProductAdapter pAdapter = new ProductAdapter(pList);
        pRecyclerView.setAdapter(pAdapter);
    }
}
