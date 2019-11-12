package com.example.prototype_5works.ui.user.mypage;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.prototype_5works.R;
import java.util.ArrayList;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private ArrayList<ProductList> pList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ProductImage;
        TextView ProductName;
        TextView ProductPrice;
        TextView ProductInfo;
        ViewHolder(View view){
            super(view);
            ProductImage = view.findViewById(R.id.ProductImage);
            ProductName = view.findViewById(R.id.ProductName);
            ProductPrice = view.findViewById(R.id.ProductPrice);
            ProductInfo = view.findViewById(R.id.ProductInfo);
        }
    }

    ProductAdapter(ArrayList<ProductList> list){
        pList = list;
    }

    @NonNull
    @Override
    public  ProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.product_list, parent, false);
        ProductAdapter.ViewHolder vh = new ProductAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ViewHolder holder, int position){
        ProductList productList = pList.get(position);

        holder.ProductImage.setImageDrawable(productList.getProductImage());
        holder.ProductName.setText(productList.getProductName());
        holder.ProductPrice.setText(productList.getProductPrice());
        holder.ProductInfo.setText(productList.getProductInfo());

        }

    @Override
    public int getItemCount() {
        return pList.size();
    }


}