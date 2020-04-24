package com.sky.timetracker.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sky.timetracker.R;
import com.sky.timetracker.View.LoginActivity;

public class UserPageFragment extends Fragment {

    private View view;
    private TextView mTvLoginName;
    private Button mBtLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_page, container, false);
        initView();
        return view;


    }

    private void initView() {
        mTvLoginName = view.findViewById(R.id.tv_login_name);
        mBtLogin = view.findViewById(R.id.bt_login);
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivityForResult(intent,1);
            }
        });
    }
}
