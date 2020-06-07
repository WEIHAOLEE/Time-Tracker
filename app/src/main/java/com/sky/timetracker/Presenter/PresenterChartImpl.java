package com.sky.timetracker.Presenter;

import android.content.Context;

import com.github.mikephil.charting.data.PieEntry;
import com.sky.timetracker.IContract;

import java.util.List;

public class PresenterChartImpl implements IContract.IPresenterChart {
    private final IContract.IViewChart view;
    private final IContract.IModel model;

    public PresenterChartImpl(IContract.IViewChart view, IContract.IModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void getChartDataList(Context context) {
        view.chartDataReturn(model.queryPieChart(context));
    }
}
