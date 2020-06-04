package com.sky.timetracker.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.sky.timetracker.Constants;
import com.sky.timetracker.R;
import com.sky.timetracker.View.LoginActivity;
import com.sky.timetracker.View.ProfileActivity;
import com.sky.timetracker.util.CheckLoginStatus;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserPageFragment extends Fragment {

    private View view;
    private TextView mTvLoginName;
    private Button mBtLogin;
    private CircleImageView mIvPhoto;
    private TextView mTvAbout;
    private TextView mTvUserId;
    private TextView mTvUsername;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_page, container, false);
        initView();
        return view;


    }

    private void initView() {
        mIvPhoto = view.findViewById(R.id.iv_pf_photo);
        mIvPhoto.setOnClickListener(profileOnClickListener);
        mTvUsername = view.findViewById(R.id.tv_username);
        mTvUsername.setOnClickListener(profileOnClickListener);
        mTvUserId = view.findViewById(R.id.tv_userid);
        mTvUserId.setOnClickListener(profileOnClickListener);
        mTvAbout = view.findViewById(R.id.tv_about);
    }

    private View.OnClickListener profileOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: 判断登录状态 未登录则跳转登录页面 登录则跳转到用户详情页？）
            if (Constants.LOGIN_STATUS){
                Log.d("Login ", "already login");
                Intent intent = new Intent(view.getContext(), ProfileActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent(view.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        }
    };

    @Override
    public void onResume() {
        if (!Constants.LOGIN_STATUS){
            CheckLoginStatus checkLoginStatus = new CheckLoginStatus(getActivity());
            // 检查是否登录 并设置常量
            checkLoginStatus.getSharedPreferences();
            // 如果还是等于空 那就清空信息
            if (!Constants.LOGIN_STATUS){
                mTvUserId.setText("UserID");
                mTvUsername.setText("UserName");
                mIvPhoto.setImageResource(R.drawable.profile_photo);
            }

        }
        if (Constants.LOGIN_STATUS){
            mTvUserId.setText(String.valueOf(Constants.USER_ID));
            mTvUsername.setText(Constants.USER_NAME);
            Glide.with(view).load(Constants.IMAGE_PATH).into(mIvPhoto);
        }
        super.onResume();
    }
}
