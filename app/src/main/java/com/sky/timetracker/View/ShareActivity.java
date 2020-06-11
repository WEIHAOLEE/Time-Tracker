package com.sky.timetracker.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sky.timetracker.Constants;
import com.sky.timetracker.R;
import com.sky.timetracker.util.BitmapToByteArray;
import com.sky.timetracker.util.CheckAppAvilible;
import com.sky.timetracker.util.ViewUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;

public class ShareActivity extends AppCompatActivity {

    private TextView mTvTimerCount;
    private TextView mTvDate;
    private TextView mTvTime;
    private TextView mTvType;
    private Button mBtShare;
    private RelativeLayout mView;
    private String count;
    // APP_ID 替换为你的应用从官方网站申请到的合法appID
    private static final String APP_ID = "wx9f8d0e41f2b99f2b";

    // IWXAPI 是第三方app和微信通信的openApi接口
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        regToWx();
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        count = intent.getStringExtra("count");
        String date = intent.getStringExtra("date");
        String time = intent.getStringExtra("time");
        String type = intent.getStringExtra("type");
        mTvTimerCount.setText("这是您第" + count + "次记录时间");
        mTvTime.setText(time);
        mTvDate.setText(date);
        mTvType.setText(type);

    }

    private void initView() {
        mTvTimerCount = findViewById(R.id.tv_timer_count);
        mTvDate = findViewById(R.id.tv_date_value);
        mTvTime = findViewById(R.id.tv_time_value);
        mTvType = findViewById(R.id.tv_type_value);
        mBtShare = findViewById(R.id.bt_share);
        mView = findViewById(R.id.rl_1);
        mBtShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 检测微信是否存在
                if (CheckAppAvilible.isWeixinAvilible(getApplicationContext())) {
                    try {
//                    getExternalFilesDir()
//                    File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//                    ViewUtils.saveView(ShareActivity.this,mView,externalFilesDir +"/" + count + ".png");
                        Bitmap bitmap = ViewUtils.viewConversionBitmap(mView);

                        //初始化 WXImageObject 和 WXMediaMessage 对象
                        WXImageObject imgObj = new WXImageObject(bitmap);
                        WXMediaMessage msg = new WXMediaMessage();
                        msg.mediaObject = imgObj;
                        // 设置缩略图
                        Bitmap thumbBmp = Bitmap.createScaledBitmap(bitmap, 10, 10, true);
                        bitmap.recycle();
                        msg.thumbData = BitmapToByteArray.toByteArray(thumbBmp);

                        //构造一个Req
                        SendMessageToWX.Req req = new SendMessageToWX.Req();
                        req.transaction = buildTransaction("img");
                        req.message = msg;
                        /**
                         * 分享到对话:
                         * SendMessageToWX.Req.WXSceneSession
                         * 分享到朋友圈:
                         * SendMessageToWX.Req.WXSceneTimeline ;
                         * 分享到收藏:
                         * SendMessageToWX.Req.WXSceneFavorite
                         * transaction	String	对应该请求的事务 ID，通常由 Req 发起，回复 Resp 时应填入对应事务 ID
                         */
                        req.scene = SendMessageToWX.Req.WXSceneTimeline;
                        req.userOpenId = "fuck the document";
                        //调用api接口，发送数据到微信
                        api.sendReq(req);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"您没有安装微信",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void regToWx() {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);

        // 将应用的appId注册到微信
        api.registerApp(APP_ID);

        //建议动态监听微信启动广播进行注册到微信
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // 将该app注册到微信
                api.registerApp(Constants.APP_ID);
            }
        }, new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

    }

    /**
     * 构建一个唯一标志
     *
     * @param type 分享的类型分字符串
     * @return 返回唯一字符串
     */
    private static String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


}
