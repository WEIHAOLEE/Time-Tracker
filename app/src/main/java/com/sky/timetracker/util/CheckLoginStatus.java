package com.sky.timetracker.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.sky.timetracker.Constants;

public class CheckLoginStatus {
    // 是否有必要设为final
    private Activity activity;

    public CheckLoginStatus(Activity activity) {
        this.activity = activity;
    }

    public void getSharedPreferences(){
        // TODO：检查文件是否存在
        SharedPreferences sp = activity.getSharedPreferences(Constants.SP_USER_DATA, Context.MODE_PRIVATE);
        boolean loginStatus = sp.getBoolean("loginStatus", false);
        if (loginStatus){
            Constants.USER_ID = sp.getInt("uId",0);
            Constants.USER_NAME = sp.getString("uName", "");
            Constants.IMAGE_PATH = Constants.SERVER_BASE_URL + sp.getString("imagePath", "");
            Constants.LOGIN_STATUS = true;
        }
    }
}
