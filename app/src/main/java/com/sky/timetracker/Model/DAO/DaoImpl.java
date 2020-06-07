package com.sky.timetracker.Model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sky.timetracker.Constants;
import com.sky.timetracker.db.DatabaseHelper;
import com.sky.timetracker.pojo.DataBean;

import java.util.ArrayList;
import java.util.List;

public class DaoImpl implements Dao {

    private final DatabaseHelper mDatabaseHelper;
    private List list;
    private List<DataBean> dataBeanList;

    public DaoImpl(Context context) {
        mDatabaseHelper = new DatabaseHelper(context);
    }

    @Override
    public void insert(String name, int time, int date, String type) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("record_name",name);
        values.put("record_time",time);
        values.put("record_date",date);
        values.put("record_type",type);
        db.insert(Constants.TABLE_NAME_RECORD,null,values);
        db.close();
    }

    @Override
    public void update() {

    }

    @Override
    public void delete(int id) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        String clause = "record_id = " + id;
        db.delete(Constants.TABLE_NAME_RECORD,clause,null);
        db.close();
    }

    @Override
    public void query() {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME_RECORD, null, null, null, null, null, null);
        if (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String time = String.valueOf((cursor.getInt(2) / 60000));
            String prepareDate = String.valueOf(cursor.getInt(3));
            String date = prepareDate.substring(0,4) + "年" + prepareDate.substring(4,6) + "月" + prepareDate.substring(6,8) + "日";
            Log.d("测试","id --> " + id + " 时间 --> " + time + " 名称 --> " + name + "日期 --> " + date );
        }
        db.close();
    }

    @Override
    public List dataList() {
        List<DataBean> dataBeanList = null;
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME_RECORD, null, null, null, null, null, null);
        dataBeanList = new ArrayList<>();
        //试试之前的方法可不可以遍历成功
        // 结论 单独一个while 循环是可以的 故删除for循环
        while (cursor.moveToNext()) {
//            for (int i = 0;i < cursor.getCount(); i ++){
//                cursor.moveToPosition(i);
                DataBean data = new DataBean();
                data.setId(cursor.getInt(0));
                data.setName(cursor.getString(1));
                data.setTime((cursor.getInt(2) / 60000) + "分钟");
                String prepareDate = String.valueOf(cursor.getInt(3));
                String date = prepareDate.substring(0,4) + "年" + prepareDate.substring(4,6) + "月" + prepareDate.substring(6,8) + "日";
                data.setDate(date);
                dataBeanList.add(data);
//            }
        }
        cursor.close();
        db.close();

        return dataBeanList;
    }
}
