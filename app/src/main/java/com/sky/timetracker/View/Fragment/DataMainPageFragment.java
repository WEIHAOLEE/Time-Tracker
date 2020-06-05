package com.sky.timetracker.View.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.sky.timetracker.R;
import com.sky.timetracker.View.adapter.DataViewPagerAdapter;
import com.sky.timetracker.View.adapter.IndicatorAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;

public class DataMainPageFragment extends Fragment {

    private View mView;
    private MagicIndicator mMagicIndicator;
    private ViewPager mVpData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main_data_page, container, false);
        initView();
        return mView;
    }

    private void initView() {
        mMagicIndicator = mView.findViewById(R.id.indicator_data);
        mMagicIndicator.setBackgroundColor(mView.getResources().getColor(R.color.colorPrimary));
        // 创建indicator的适配器
        IndicatorAdapter indicatorAdapter = new IndicatorAdapter(mView.getContext());
        CommonNavigator commonNavigator = new CommonNavigator(mView.getContext());
        commonNavigator.setAdapter(indicatorAdapter);
        // ViewPager
        mVpData = mView.findViewById(R.id.vp_data);
        DataViewPagerAdapter dataViewPagerAdapter = new DataViewPagerAdapter(getChildFragmentManager());
        mVpData.setAdapter(dataViewPagerAdapter);
        // 绑定indicator和viewpager
        mMagicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mMagicIndicator,mVpData);
    }
}
