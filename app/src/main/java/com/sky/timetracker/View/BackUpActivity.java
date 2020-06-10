package com.sky.timetracker.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.sky.timetracker.IContract;
import com.sky.timetracker.Model.ModelImpl;
import com.sky.timetracker.Presenter.PresenterBackUpImpl;
import com.sky.timetracker.R;

public class BackUpActivity extends AppCompatActivity implements View.OnClickListener, IContract.IViewBackUp {

    private Button mBtExport;
    private Button mBtImport;
    private PresenterBackUpImpl presenter;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_up);
        initView();
        initPresenter();
    }


    private void initPresenter() {
        presenter = new PresenterBackUpImpl(this, ModelImpl.getInstance());
    }

    private void initView() {
        mBtExport = findViewById(R.id.bt_export);
        mBtImport = findViewById(R.id.bt_import);
        mBtExport.setOnClickListener(this);
        mBtImport.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_export:
                presenter.exportData(getApplicationContext());
                break;
            case R.id.bt_import:
                presenter.importData(getApplicationContext());
        }
    }

    @Override
    public void makeToast(String content) {
        Toast.makeText(getApplicationContext(),content,Toast.LENGTH_SHORT).show();
    }
}
