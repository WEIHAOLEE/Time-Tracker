package com.sky.timetracker.Model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.mikephil.charting.data.PieEntry;
import com.sky.timetracker.Constants;
import com.sky.timetracker.db.DatabaseHelper;
import com.sky.timetracker.pojo.DataBean;
import com.sky.timetracker.pojo.TypeBean;

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
                data.setType(cursor.getString(4));
                dataBeanList.add(data);
//            }
        }
        cursor.close();
        db.close();

        return dataBeanList;
    }

    @Override
    public List<com.github.mikephil.charting.data.PieEntry> pieDataList() {
        List<PieEntry> pieEntryList = null;
        pieEntryList = new ArrayList<>();
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        Cursor totalTimeCursor = db.query(Constants.TABLE_NAME_RECORD, null, null, null, null, null, null);
        float totalTime = 0.0f;
        while (totalTimeCursor.moveToNext()) {
            float time = totalTimeCursor.getInt(2) / 60000;
            totalTime = totalTime + time;
        }

        Cursor typeCursor = db.query(Constants.TABLE_NAME_RECORD_TYPE, null, null, null, null, null, null);
        while (typeCursor.moveToNext()) {
            String type = typeCursor.getString(1);
            Cursor typeTimeCursor = db.query(Constants.TABLE_NAME_RECORD, null, "record_type = ?", new String[]{type}, null, null, null);
            float typeTotalTime = 0.0f;
            while (typeTimeCursor.moveToNext()) {
                float time = typeTimeCursor.getInt(2) / 60000;
                typeTotalTime = typeTotalTime + time;
            }

            float typeRate = (typeTotalTime / totalTime) * 100;
            com.github.mikephil.charting.data.PieEntry pieEntry = new com.github.mikephil.charting.data.PieEntry(typeRate, type);
//            PieEntry pieEntry = new PieEntry(typeRate, type);
            pieEntryList.add(pieEntry);
            typeTimeCursor.close();
        }
        totalTimeCursor.close();
        typeCursor.close();
        db.close();
        return pieEntryList;
    }

    @Override
    public String queryType(String type) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        Cursor cursor = db.query(Constants.TABLE_NAME_RECORD_TYPE, null, "type_name = ?", new String[]{type}, null, null, null);
        while (cursor.moveToNext()) {
            String string = cursor.getString(1);
            return string;
        }
        cursor.close();
        db.close();
        return null;
    }

    @Override
    public void insertType(String type) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("type_name", type);
        db.insert(Constants.TABLE_NAME_RECORD_TYPE,null,values);
        db.close();
    }
}
