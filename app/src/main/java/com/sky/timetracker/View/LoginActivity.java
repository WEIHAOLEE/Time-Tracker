package com.sky.timetracker.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.sky.timetracker.R;
import com.sky.timetracker.pojo.OnlineUser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.LogRecord;

public class LoginActivity extends AppCompatActivity {

    private Button mBtLogin;
    private TextView mTvUserName;
    private TextView mTvUserPassword;
    private OnlineUser mUser;
    private static Handler mHandler;
    private static final int WHAT_LOADER_RESULT = 1;

//    private static class Handler extends android.os.Handler{
//        private static final int WHAT_LOADER_RESULT = 1;
//
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            switch (msg.what){
//                case WHAT_LOADER_RESULT:
//                    OnlineUser user = (OnlineUser) msg.obj;
////                    Toast.makeText(LoginActivity.this,"登录成功 用户名：" + user.toString(),Toast.LENGTH_SHORT).show();
//                    break;
//            }
//
//        }
//    }


    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what) {
                    case WHAT_LOADER_RESULT:
                        OnlineUser user = (OnlineUser) msg.obj;
                        makeTost(user.toString());
                        break;
                }
            }
        };
        initView();
    }

    private void initView() {
        mBtLogin = findViewById(R.id.bt_login);
        mTvUserName = findViewById(R.id.et_id);
        mTvUserPassword = findViewById(R.id.et_pwd);
        mBtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        loadData();
                    }
                }).start();


            }
        });
    }

    private void makeTost(String state) {
        Toast.makeText(getApplicationContext(),state,Toast.LENGTH_SHORT).show();
    }

    private void loadData() {
        try {
            String parpreUrl = "http://47.101.190.152:8080/appServer/login?userName=" + mTvUserName.getText().toString().trim() + "&userPassword=" + mTvUserPassword.getText().toString().trim();
            URL url = new URL(parpreUrl);
            HttpURLConnection httpURLConnection  = (HttpURLConnection) url.openConnection();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200){
                httpURLConnection.setConnectTimeout(1000);
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line;
                while((line = bufferedReader.readLine()) != null) {
                    Log.d("测试",line);
                    Gson gson = new Gson();
                    Message message = mHandler.obtainMessage();
                    message.what = WHAT_LOADER_RESULT;
                    message.obj = gson.fromJson(line, OnlineUser.class);
                    mHandler.sendMessage(message);
                }
                bufferedReader.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
