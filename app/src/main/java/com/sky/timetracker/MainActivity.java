package com.sky.timetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sky.timetracker.View.Fragment.DataPageFragment;
import com.sky.timetracker.View.Fragment.StartPageFragment;
import com.sky.timetracker.View.Fragment.UserPageFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private BottomNavigationView mBottomNavigationView;
    private StartPageFragment mStartPageFragment;
    private DataPageFragment mDataPageFragment;
    private UserPageFragment mUserPageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 动态设置 actionbar 标题 逻辑不完美
        getSupportActionBar().setTitle(R.string.app_start);
        initView();
        setDefaultFragment();
    }


    private void setDefaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mStartPageFragment = new StartPageFragment();
        transaction.replace(R.id.fl_content,mStartPageFragment);
        transaction.show(mStartPageFragment);
        transaction.commit();
    }


    private void initView() {

        mBottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationClick navigationClick = new NavigationClick();
        mBottomNavigationView.setOnNavigationItemSelectedListener(navigationClick);

    }


    private class NavigationClick implements BottomNavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            hideFragment(transaction);
            switch (item.getItemId()){
                case R.id.navigation_start:
                    getSupportActionBar().setTitle(R.string.app_start);
                    if (mStartPageFragment == null){
                        mStartPageFragment = new StartPageFragment();
                        transaction.add(R.id.fl_content,mStartPageFragment);
                        transaction.show(mStartPageFragment);
                        transaction.commit();
                    }else {
                        transaction.show(mStartPageFragment);
                        transaction.commit();
                    }
                    return true;
                case R.id.navigation_data:
                    getSupportActionBar().setTitle(R.string.app_data);
                    if (mDataPageFragment == null){
                        mDataPageFragment = new DataPageFragment();
                        transaction.add(R.id.fl_content,mDataPageFragment);
                        transaction.show(mDataPageFragment);
                        transaction.commit();
                    }else {
                        transaction.show(mDataPageFragment);
                        transaction.commit();
                    }
                    return true;
                case R.id.navigation_user:
                    getSupportActionBar().setTitle(R.string.app_user);
                    if (mUserPageFragment == null){
                        mUserPageFragment = new UserPageFragment();
                        transaction.add(R.id.fl_content,mUserPageFragment);
                        transaction.show(mUserPageFragment);
                        transaction.commit();
                    }else {
                        transaction.show(mUserPageFragment);
                        transaction.commit();
                    }
                    return true;
            }
            return false;
        }

    }

    private void hideFragment(FragmentTransaction transaction){
        if (mStartPageFragment != null){
            transaction.hide(mStartPageFragment);
        }
        if (mDataPageFragment != null){
            transaction.hide(mDataPageFragment);
        }
        if (mUserPageFragment != null){
            transaction.hide(mUserPageFragment);
        }
    }
}
