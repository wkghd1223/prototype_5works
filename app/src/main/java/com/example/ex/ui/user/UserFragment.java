package com.example.ex.ui.user;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.ex.R;
import com.example.ex.User;

public class UserFragment extends Fragment {

    private UserViewModel mViewModel;
    private ImageButton ibtn;
    private User user= new User("김정현","/res/kim_img.jpg");
    public static UserFragment newInstance() {
        return new UserFragment();
    }
    ProductFragment productFragment;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.user_fragment, container, false);
//        ibtn = root.findViewById(R.id.CartImage);
//        ibtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        productFragment = new ProductFragment();
        getFragmentManager().beginTransaction().add(R.id.Product_Fragment, productFragment).commit();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        // TODO: Use the ViewModel
    }

}
