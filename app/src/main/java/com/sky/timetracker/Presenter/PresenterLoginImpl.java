package com.sky.timetracker.Presenter;

import android.util.Log;

import com.sky.timetracker.IContract;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PresenterLoginImpl implements IContract.IPresenterLogin {
    private static final String TAG = PresenterLoginImpl.class.getName();
    private final IContract.IViewLogin view;

    public PresenterLoginImpl(IContract.IViewLogin view) {
        this.view = view;
    }

    @Override
    public void login(String uid, String uPwd) {

        // 新建client
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .build();

        // 请求体
        RequestBody body = new FormBody.Builder()
                .add("userName",uid)
                .add("userPassword",uPwd)
                .build();

        // 建立request
        Request request = new Request.Builder()
                .url("http://47.101.190.152:8080/appServer/login")
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();

        // client发送请求 异步请求 回调
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"onFailure --> " + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                ResponseBody responseBody = response.body();
                String string = responseBody.string();
                Log.d(TAG,"response --> " + string);
                view.saveUserData(string);
            }
        });


    }
}
