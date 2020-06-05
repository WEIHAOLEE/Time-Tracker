package com.sky.timetracker.View.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.sky.timetracker.View.Fragment.DataChartFragment;
import com.sky.timetracker.View.Fragment.DataPageFragment;

public class DataViewPagerAdapter extends FragmentPagerAdapter {


    private DataPageFragment mDataPageFragment;
    private DataChartFragment mDataChartFragment;

    public DataViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                if (mDataPageFragment == null) {
                    mDataPageFragment = new DataPageFragment();
                }
                return mDataPageFragment;
            case 1:
                if (mDataChartFragment == null) {
                    mDataChartFragment = new DataChartFragment();
                }
                return mDataChartFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
