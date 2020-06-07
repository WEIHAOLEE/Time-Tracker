package com.sky.timetracker.Model;

import android.content.Context;
import android.util.Log;

import com.github.mikephil.charting.data.PieEntry;
import com.sky.timetracker.IContract;
import com.sky.timetracker.Model.DAO.DaoImpl;
import com.sky.timetracker.pojo.DataBean;

import java.util.List;

public class ModelImpl implements IContract.IModel {

    private static ModelImpl model;
    private DaoImpl dao;

    public static ModelImpl getInstance(){
        if (model == null) {
            model = new ModelImpl();
        }
        return model;
    }
    @Override
    public List<DataBean> queryDataList(Context context) {
        dao = new DaoImpl(context);
        List dataList = dao.dataList();
        return dataList;
    }

    @Override
    public void insertData(Context context, String missionName, int time, int date, String type) {
        dao = new DaoImpl(context);
        dao.insert(missionName,time,date,type);
        String s = dao.queryType(type);
        if (s == null){
            Log.d("查询", "是空的");
            dao.insertType(type);
        }
    }

    @Override
    public void deleteData(Context context,int id) {
        dao = new DaoImpl(context);
        dao.delete(id);
    }

    @Override
    public List<com.github.mikephil.charting.data.PieEntry> queryPieChart(Context context) {
        DaoImpl dao = new DaoImpl(context);
        List<PieEntry> pieEntryList = dao.pieDataList();
        return pieEntryList;
    }
}
