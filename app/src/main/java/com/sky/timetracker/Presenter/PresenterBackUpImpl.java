package com.sky.timetracker.Presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.sky.timetracker.IContract;
import com.sky.timetracker.pojo.BackUpBean;
import com.sky.timetracker.pojo.DataBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;

public class PresenterBackUpImpl implements IContract.IPresenterBackUp {
    private final IContract.IViewBackUp view;
    private final IContract.IModel model;

    public PresenterBackUpImpl(IContract.IViewBackUp view, IContract.IModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void exportData(Context context) {
        // 获取count
        SharedPreferences sp = context.getSharedPreferences("timer_count", Context.MODE_PRIVATE);
        int count = sp.getInt("count", 0);
        // 查询数据
        List<DataBean> dataBeanList = model.queryDataList(context);
        BackUpBean backUpBean = new BackUpBean(dataBeanList, count);
        // 转换json
        Gson gson = new Gson();
        String backupJson = gson.toJson(backUpBean);
        Log.d("备份",backupJson);
        // 输出
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        String savePathName = externalFilesDir + "/TimeTackerBackup.json";
        try {
            File file = new File(savePathName);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(backupJson.getBytes());
            fos.flush();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        view.makeToast("导出成功！文件目录在Android/data/com.sky.timetracker/files/Download");


    }

    @Override
    public void importData(Context context) {
        // 输入
        File externalFilesDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        String savePathName = externalFilesDir + "/TimeTackerBackup.json";
        File file = new File(savePathName);
        try {
            FileInputStream fis = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis));
            String readJson = bufferedReader.readLine();
            Gson gson = new Gson();
            Log.d("import",readJson);
            BackUpBean backUpBean = gson.fromJson(readJson, BackUpBean.class);
            // 先设置记录时间次数通过sp记录
            SharedPreferences sp = context.getSharedPreferences("timer_count", Context.MODE_PRIVATE);
            // 先读取一下看看有没有记录
            int count = sp.getInt("count", 0);
            count = count + backUpBean.getCount();
            SharedPreferences.Editor edit = sp.edit();
            edit.putInt("count",count);
            edit.commit();
            Log.d("import","sp储存成功");
            // 进行数据库存储
            List<DataBean> dataBeanList = backUpBean.getDataBeanList();
            for (DataBean dataBean : dataBeanList){
                // 分割字符串
                String[] prepareTime = dataBean.getTime().split("\\D");
                String[] prepareDate = dataBean.getDate().split("\\D");
                String stringTime = "";
                String stringDate = "";
                for (String m : prepareTime){
                    stringTime += m;
                }
                for (String m : prepareDate){
                    stringDate += m;
                }
                int time = Integer.parseInt(stringTime) * 60000;
                int date = Integer.parseInt(stringDate);
//                int time = Integer.parseInt(dataBean.getTime());
                String name = dataBean.getName();
                String type = dataBean.getType();
                model.insertData(context,name,time,date,type);
            }

            // 完成 弹窗

            view.makeToast("读取成功");

        }catch (Exception e){
            e.printStackTrace();
            view.makeToast("文件读取错误，请检查您的文件是否存在");
        }
    }
}
