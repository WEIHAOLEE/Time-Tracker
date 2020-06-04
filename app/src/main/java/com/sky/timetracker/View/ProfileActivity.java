package com.sky.timetracker.View;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sky.timetracker.Constants;
import com.sky.timetracker.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private CircleImageView mIvUserPhoto;
    private TextView mTvUid;
    private TextView mTvUname;
    private Button mBtLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initView();
        initData();
    }

    private void initData() {
        mTvUid.setText(String.valueOf(Constants.USER_ID));
        mTvUname.setText(String.valueOf(Constants.USER_NAME));
        Glide.with(this).load(Constants.IMAGE_PATH).into(mIvUserPhoto);
    }

    private void initView() {
        getSupportActionBar().setTitle( R.string.user_info);
        mIvUserPhoto = findViewById(R.id.iv_user_photo);
        mTvUid = findViewById(R.id.tv_userid_num);
        mTvUname = findViewById(R.id.tv_uname_num);
        mBtLogout = findViewById(R.id.bt_logout);
        mBtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences(Constants.SP_USER_DATA, MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("loginStatus", false);
                edit.putInt("uId",0);
                edit.putString("uName","");
                edit.putString("uPwd","");
                edit.putString("imagePath","");
                edit.commit();
                Constants.LOGIN_STATUS =false;
                Constants.IMAGE_PATH = "";
                Constants.USER_NAME = "";
                Constants.USER_ID = 0;
                finish();
            }
        });
    }
}
