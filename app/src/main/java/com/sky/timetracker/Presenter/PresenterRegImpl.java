package com.sky.timetracker.Presenter;

import android.util.Log;

import com.sky.timetracker.IContract;


import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class PresenterRegImpl implements IContract.IPresenterReg {
    private static final String TAG =  PresenterRegImpl.class.getName();
    private final IContract.IViewReg view;

    public PresenterRegImpl(IContract.IViewReg view) {
        this.view = view;
    }

    @Override
    public void register(String uid, String uPwd, String uImage) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(1000, TimeUnit.MILLISECONDS)
                .build();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = new FormBody.Builder()
                .add("userImage",uImage)
                .add("userName",uid)
                .add("userPassword",uPwd)
                .build();
//        RequestBody body = RequestBody.create(mediaType, "userImage=" + uImage + "&userName="+ uid + "&userPassword="+ uPwd);
        Request request = new Request.Builder()
                .url("http://47.101.190.152:8080/appServer/res")
                .post(body)
//                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG,"onFailure --> " + e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                if (code == HttpURLConnection.HTTP_OK) {
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        // .string()方法只能用一次
                        String responseString = responseBody.string();
                        if (responseString.equals("1\n")){
                            view.finishActivity();
                        }
                    }
                }
            }
        });
    }
}
