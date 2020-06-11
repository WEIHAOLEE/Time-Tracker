package com.sky.timetracker.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.sky.timetracker.R;


public class AboutActivity extends AppCompatActivity {

    private TextView mTvOpenSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }

    private void initView() {
        mTvOpenSource = findViewById(R.id.tv_open_source);
        mTvOpenSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OpenSourceActivity.class);
                startActivity(intent);
            }
        });
    }
}
