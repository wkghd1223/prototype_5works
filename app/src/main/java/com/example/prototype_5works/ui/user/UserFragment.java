package com.example.prototype_5works.ui.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.prototype_5works.R;
import com.example.prototype_5works.User;
import com.example.prototype_5works.ui.user.mypage.CartActivity;

public class UserFragment extends Fragment implements View.OnClickListener {

    private UserViewModel mViewModel;
    private ImageButton cartImage;
    private User user= new User("김정현",R.drawable.kim_img);
    public static UserFragment newInstance() {
        return new UserFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.user_fragment, container, false);

        cartImage = root.findViewById(R.id.CartImage);
        cartImage.setOnClickListener(this);

        ImageView userImage = root.findViewById(R.id.UserImage);
        TextView userName = root.findViewById(R.id.UserName);

        userImage.setImageResource(user.getUserImage());
        userName.setText(user.getName());
        return root;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity().getApplicationContext(), CartActivity.class);
        startActivity(intent);
    }
}
