package com.sky.timetracker.Presenter;

import android.content.Context;

import com.sky.timetracker.IContract;

public class PresenterImpl implements IContract.IPresenter {
    private final IContract.IView view;
    private final IContract.IModel model;

    public PresenterImpl(IContract.IModel model, IContract.IView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getDataBeanList(Context context) {

        view.dataBeanListReturn(model.queryDataList(context));
    }

    @Override
    public boolean deleteData(Context context) {
        model.deleteData(context,view.userDelete());
        // refresh
//        view.dataBeanListReturn(model.queryDataList(context));

        return true;
    }

}
