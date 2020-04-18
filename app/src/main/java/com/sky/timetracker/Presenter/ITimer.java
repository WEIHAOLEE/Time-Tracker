package com.sky.timetracker.Presenter;

import android.content.Context;
import android.widget.Chronometer;

public interface ITimer {

    /**
     * 开始计时 正向
     */
    void startTiming(Chronometer Timer,Context context);

    /**
     * 倒计时
     * @param Timer timer类
     * @param time 时长
     */
    void startCountDown(Chronometer Timer, int time, Context context);

    /**
     * 结束计时
     */
    void finish(Chronometer Timer);
}
