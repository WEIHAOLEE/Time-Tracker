package com.sky.timetracker.Model;

import android.content.Context;

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
    public void insertData(Context context, String missionName, int time, int date) {
        dao = new DaoImpl(context);
        dao.insert(missionName,time,date);
    }

    @Override
    public void deleteData(Context context,int id) {
        dao = new DaoImpl(context);
        dao.delete(id);
    }
}
