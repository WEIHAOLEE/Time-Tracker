package com.sky.timetracker.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.sky.timetracker.R;

public class ShareActivity extends AppCompatActivity {

    private TextView mTvTimerCount;
    private TextView mTvDate;
    private TextView mTvTime;
    private TextView mTvType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        String count = intent.getStringExtra("count");
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
    }
}
