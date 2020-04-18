package com.sky.timetracker.Presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.sky.timetracker.IContract;
import com.sky.timetracker.Model.DAO.Dao;
import com.sky.timetracker.Model.DAO.DaoImpl;
import com.sky.timetracker.Model.ModelImpl;

import java.util.Date;

public class TimeImpl implements ITimer, IContract.IPresenterTimer {


    private final IContract.IView.IViewStartPage view;
    private final IContract.IModel model;
    private Context mContext;
    private int mCountDownTime;
    private int mIntDate;

    public TimeImpl(IContract.IModel model, IContract.IView.IViewStartPage view) {
        this.model = model;
        this.view = view;
    }


    @Override
    public void startTiming(Chronometer Timer) {
        Timer.setBase(SystemClock.elapsedRealtime());
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
        Timer.stop();
    }

    @Override
    public void setData(String missionName) {
        // 传给m层
        model.insertData(mContext,missionName,mCountDownTime,mIntDate);
    }
}
