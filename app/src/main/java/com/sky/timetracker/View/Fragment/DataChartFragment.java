package com.sky.timetracker.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.sky.timetracker.IContract;
import com.sky.timetracker.Model.ModelImpl;
import com.sky.timetracker.Presenter.PresenterChartImpl;
import com.sky.timetracker.R;

import java.util.List;

public class DataChartFragment extends Fragment implements IContract.IViewChart {

    private View mView;
    private List<PieEntry> pieEntry;
    private PresenterChartImpl presenterChart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_data_chart_page, container, false);
        initPresenter();
        initView();
        return mView;
    }

    private void initPresenter() {
        presenterChart = new PresenterChartImpl(this, ModelImpl.getInstance());

    }

    private void initView() {
        PieChart mPieChart = mView.findViewById(R.id.pie_chart);
        // 获得数据
        presenterChart.getChartDataList(mView.getContext());
        if (pieEntry != null) {
            PieDataSet set = new PieDataSet(pieEntry, "事件记录");
            PieData pieData = new PieData(set);
            mPieChart.setData(pieData);
            mPieChart.invalidate();
        }
    }

    @Override
    public void chartDataReturn(List<com.github.mikephil.charting.data.PieEntry> pieEntryList) {
        pieEntry = pieEntryList;
    }
}
