package com.sky.timetracker.View.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.sky.timetracker.View.ShareActivity;

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
    private String mMissionType;

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
                        iTimer.startTiming(mTimer,view.getContext());
                        mBtTiming.setText("完成");
                        isTimming = true;
                        mBtCountDown.setVisibility(View.GONE);
                        Constants.TIMER_STATE = "WORKING";
                    }else if (isTimming){
                        mBtTiming.setText("开始计时");
                        mBtCountDown.setVisibility(View.VISIBLE);
                        iTimer.finish(mTimer);
                        isTimming = false;
                        Constants.TIMER_STATE = "NO_WORK";
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
                                            Constants.TIMER_STATE = "WORKING";
                                        }

                                    }catch (Exception e){
                                        Toast.makeText(view.getContext(),"您没有输入时间",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .show();

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
        LayoutInflater factory = LayoutInflater.from(view.getContext());
        final  View DialogView = factory.inflate(R.layout.item_dialog_mission, null);
        final EditText etMissionName = DialogView.findViewById(R.id.et_mission_name);
        final EditText etMissionType = DialogView.findViewById(R.id.et_mission_type);
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle("请输入任务名称以及类型")
                .setView(DialogView)
                .setPositiveButton("确定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            mMissionName = etMissionName.getText().toString();
                            mMissionType = etMissionType.getText().toString();
                            iTimer.setData(mMissionName,mMissionType);


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

    /**
     * 让p层弹吐司
     */
    @Override
    public void showToast(String content) {
        Toast.makeText(view.getContext(),content,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void startActivity(Class<?> cls, String date, String time, String type) {
        Intent intent = new Intent(view.getContext(), cls);
        SharedPreferences sp = view.getContext().getSharedPreferences("timer_count", Context.MODE_PRIVATE);
        int count = sp.getInt("count", 0);
        count = count + 1;
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt("count",count);
        edit.commit();
        intent.putExtra("count",String.valueOf(count));
        intent.putExtra("date",date);
        intent.putExtra("time",time);
        intent.putExtra("type",type);
        startActivity(intent);
    }
}
