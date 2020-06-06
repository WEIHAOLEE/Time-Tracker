package com.sky.timetracker.Presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.sky.timetracker.IContract;
import com.sky.timetracker.Model.DAO.Dao;
import com.sky.timetracker.Model.DAO.DaoImpl;
import com.sky.timetracker.Model.ModelImpl;
import com.sky.timetracker.View.ShareActivity;

import java.text.DecimalFormat;
import java.util.Date;

public class TimeImpl implements ITimer, IContract.IPresenterTimer {


    // TODO: 改变数据结构 按秒计时 按分钟计时 数据更改类型
    private final IContract.IView.IViewStartPage view;
    private final IContract.IModel model;
    private Context mContext;
    private int mCountDownTime;
    private int mIntDate;
    private long mTimingStartTime;
    private long mTimingStopTime;
    private int mTimingTime;

    public TimeImpl(IContract.IModel model, IContract.IView.IViewStartPage view) {
        this.model = model;
        this.view = view;
    }


    @Override
    public void startTiming(Chronometer Timer,Context context) {
        mTimingStartTime = SystemClock.elapsedRealtime();
        mContext = context;
        Timer.setOnChronometerTickListener(null);
        Timer.setBase(mTimingStartTime);
        Timer.setCountDown(false);

//        mTimer.setCountDown(true); 代表是倒计时还是正常计时, false就是正常计时, true计时倒计时.
//        Timer.setFormat("计时：%s");
        Timer.start();

    }

    @Override
    public void startCountDown(Chronometer Timer, int time, Context context) {
        mCountDownTime = time;
        Timer.setBase(mCountDownTime + SystemClock.elapsedRealtime());
        Timer.setCountDown(true);
        mContext = context;
//        Timer.setFormat("计时：%s");
        Timer.start();

        Timer.setOnChronometerTickListener(timeListener);


    }

    private Chronometer.OnChronometerTickListener timeListener = new Chronometer.OnChronometerTickListener() {


        @Override
        public void onChronometerTick(Chronometer chronometer) {
//            Log.d("测试", String.valueOf(chronometer.getBase() - SystemClock.elapsedRealtime()));
            // 这里开始判断的是 == 0 后来打log发现他的监听间隔比较长 监听不到0时刻 所以改为 <= 0解决问题
            if (chronometer.getBase() - SystemClock.elapsedRealtime() <= 0) {
                chronometer.stop();
                Toast.makeText(mContext,"时间到啦！",Toast.LENGTH_SHORT).show();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                Date date = new Date();
                mIntDate = Integer.parseInt(dateFormat.format(date));
                view.showDialog(mCountDownTime,mIntDate);

                //结构不够完美 其实 监听事件应该放到view层
                // 以上为重构内容 需要封装方法
            }
        }
    };

    @Override
    public void finish(Chronometer Timer) {
        mTimingStopTime = SystemClock.elapsedRealtime();
        Timer.stop();
        // 归零
        Timer.setBase(SystemClock.elapsedRealtime());
        Double l = (double)(mTimingStopTime - mTimingStartTime);
        Log.d("测试", String.valueOf(l));
        if (l < 60000){
            view.showToast("您的学习时间小于一分钟，无法记录");
        }else if (l >= 60000){
            // 四舍五入
            mTimingTime = Integer.parseInt(new DecimalFormat("0").format(l));
            // TODO：这部分其实也需要模块化
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
            Date date = new Date();
            mIntDate = Integer.parseInt(dateFormat.format(date));
            //
            view.showDialog(mTimingTime,mIntDate);

        }
    }

    @Override
    public void setData(String missionName,String Type) {
        // 传给m层
        if (mCountDownTime != 0){
            model.insertData(mContext,missionName,mCountDownTime,mIntDate);
            String time = (mCountDownTime / 60000) + "分钟";
            String prepareDate = String.valueOf(mIntDate);
            String date = prepareDate.substring(0,4) + "年" + prepareDate.substring(4,6) + "月" + prepareDate.substring(6,8) + "日";
            view.startActivity(ShareActivity.class,date,time,Type);
            mCountDownTime = 0;
        }else if (mTimingTime != 0){
            model.insertData(mContext,missionName,mTimingTime,mIntDate);
            mTimingTime = 0;
        }
    }
}
