package com.sky.timetracker.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.sky.timetracker.IContract;
import com.sky.timetracker.Model.ModelImpl;
import com.sky.timetracker.Presenter.PresenterChartImpl;
import com.sky.timetracker.R;

import java.util.ArrayList;
import java.util.List;

public class DataChartFragment extends Fragment implements IContract.IViewChart {

    private View mView;
    private List<PieEntry> pieEntry;
    private PresenterChartImpl presenterChart;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private PieChart mPieChart;

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
        mPieChart = mView.findViewById(R.id.pie_chart);
        setChart();



        mSwipeRefreshLayout = mView.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setChart();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setChart() {
        // 获得数据
        presenterChart.getChartDataList(mView.getContext());
        if (pieEntry != null) {
            PieDataSet set = new PieDataSet(pieEntry, "事件记录");
            // 颜色
            ArrayList<Integer> colors = new ArrayList<Integer>();
            colors.add(getResources().getColor(R.color.colorPrimary));
            colors.add(getResources().getColor(R.color.colorAccent));
            colors.add(getResources().getColor(R.color.chartColor1));
            colors.add(getResources().getColor(R.color.chartColor2));
            colors.add(getResources().getColor(R.color.chartColor3));
            colors.add(getResources().getColor(R.color.chartColor4));
            set.setColors(colors);
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
