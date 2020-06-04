package com.sky.timetracker.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sky.timetracker.Constants;
import com.sky.timetracker.IContract;
import com.sky.timetracker.Presenter.PresenterLoginImpl;
import com.sky.timetracker.R;
import com.sky.timetracker.pojo.UserBean;
import com.sky.timetracker.util.MyMD5Util;

public class LoginActivity extends AppCompatActivity implements IContract.IViewLogin {

    private Button mBtRegister;
    private Button mBtLogin;
    private EditText mEtUid;
    private EditText mEtUpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        mEtUid = findViewById(R.id.et_id);
        mEtUpwd = findViewById(R.id.et_pwd);
        mBtLogin = findViewById(R.id.bt_login);
        mBtRegister = findViewById(R.id.bt_register);
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uId = mEtUid.getText().toString().trim();
                String pwd = mEtUpwd.getText().toString().trim();
                // 判断
                if (!TextUtils.isEmpty(uId) && !TextUtils.isEmpty(pwd)) {
                    String uPwd = MyMD5Util.encrypt(pwd);
                    PresenterLoginImpl presenterLogin = new PresenterLoginImpl(LoginActivity.this);
                    presenterLogin.login(uId,uPwd);
                }
            }
        });
        mBtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void saveUserData(String response) {
        Gson gson = new Gson();
        // 判断是否正确返回 密码用户是否存在
        if (response.equals("\"null\"\n")) {
            // 因为okHttp异步请求不再主线程 无法直接弹toast
            // 用looper包一下
            Looper.prepare();
            Toast.makeText(getApplicationContext(),"Account information error",Toast.LENGTH_SHORT).show();
            Looper.loop();
        }else {
            UserBean userBean = gson.fromJson(response, UserBean.class);
            SharedPreferences sp = getSharedPreferences(Constants.SP_USER_DATA, MODE_PRIVATE);
            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("loginStatus", true);
            edit.putInt("uId",userBean.getuId());
            edit.putString("uName",userBean.getuName());
            edit.putString("uPwd",userBean.getuPwd());
            edit.putString("imagePath",userBean.getImagePath());
            edit.commit();
            finish();
        }
    }
}