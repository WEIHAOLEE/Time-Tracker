package com.sky.timetracker.View.Fragment;

import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.sky.timetracker.Constants;
import com.sky.timetracker.IContract;
import com.sky.timetracker.Model.ModelImpl;
import com.sky.timetracker.Presenter.ITimer;
import com.sky.timetracker.Presenter.TimeImpl;
import com.sky.timetracker.R;

import java.util.Date;

public class StartPageFragment extends Fragment implements IContract.IView.IViewStartPage {

    private Chronometer mTimer;
    private View view;
    private Button mBtTiming;
    private Button mBtCountDown;
    private EditText editText;
    private int mTime;
    private TimeImpl iTimer;
    private boolean isTimming;
    private EditText mEditText;
    private String mMissionName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_start_page, container, false);
        iTimer = new TimeImpl(ModelImpl.getInstance(),this);
        initView();
        setListener();
        return view;

    }

    private void setListener() {
        isTimming = false;
        mBtTiming.setOnClickListener(btListener);
        mBtCountDown.setOnClickListener(btListener);
    }

    private View.OnClickListener btListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.bt_timing:

                    if (!isTimming){
                        iTimer.startTiming(mTimer);
                        mBtTiming.setText("完成");
                        isTimming = true;
                        mBtCountDown.setVisibility(View.GONE);
                    }else if (isTimming){
                        mBtTiming.setText("开始计时");
                        mBtCountDown.setVisibility(View.VISIBLE);
                        iTimer.finish(mTimer);
                        isTimming = false;
                    }
                    break;
                case R.id.bt_count_down:
                    //TODO: 写一个UI contorl

                    editText = new EditText(view.getContext());
                    editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("请输入倒计时长")
                            .setView(editText)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        String stringTime = editText.getText().toString();
                                        mTime = 60000 * Integer.parseInt(stringTime);
                                        if (mTime != 0){
                                            iTimer.startCountDown(mTimer,mTime,view.getContext());
                                        }

                                    }catch (Exception e){
                                        Toast.makeText(view.getContext(),"您没有输入时间",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .show();
                    Constants.TIMER_STATE = "WORKING";

                    break;
            }
        }
    };



    private void initView() {
        mTimer = view.findViewById(R.id.cm_timer);
        mBtTiming = view.findViewById(R.id.bt_timing);
        mBtCountDown = view.findViewById(R.id.bt_count_down);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * 传给p层任务名称
     * @return
     */
    @Override
    public void showDialog(int time, int date) {
        // 在其他fragment上不会显示 其实这个应该放到activity下
        final EditText editText1 = new EditText(view.getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("请输入倒计时长")
                .setView(editText1)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            mMissionName = editText1.getText().toString();
                            iTimer.setData(mMissionName);
                            // p调用v方法 要数据  v调用p方法传数据?
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(view.getContext(),"输入错误" ,Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .show();
        Constants.TIMER_STATE = "NO_WORK";
    }
}
