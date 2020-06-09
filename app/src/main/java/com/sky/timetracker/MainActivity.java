package com.sky.timetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sky.timetracker.View.Fragment.DataMainPageFragment;
import com.sky.timetracker.View.Fragment.StartPageFragment;
import com.sky.timetracker.View.Fragment.UserPageFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();
    private BottomNavigationView mBottomNavigationView;
    private StartPageFragment mStartPageFragment;
    private UserPageFragment mUserPageFragment;
    private DataMainPageFragment mDataMainPageFragment;
    private View viewInflate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 动态设置 actionbar 标题 逻辑不完美
        getSupportActionBar().setTitle(R.string.app_start);
        initView();
        setDefaultFragment();
        initInflate();
    }

    private void initInflate() {
        viewInflate = getLayoutInflater().inflate(R.layout.activity_main, null);
    }

    // 设置默认Fragment
    private void setDefaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mStartPageFragment = new StartPageFragment();
        transaction.replace(R.id.fl_content,mStartPageFragment);
        transaction.show(mStartPageFragment);
        transaction.commit();
    }


    private void initView() {
        // 去除actionbar阴影
        getSupportActionBar().setElevation(0);
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
                    getSupportActionBar().show();
                    getSupportActionBar().setTitle(R.string.app_start);
                    if (mStartPageFragment == null){
                        mStartPageFragment = new StartPageFragment();
                        transaction.setCustomAnimations(R.anim.anim_fragment_open,R.anim.anim_fragment_close);
                        transaction.add(R.id.fl_content,mStartPageFragment);
                        transaction.show(mStartPageFragment);
                        transaction.commit();
                    }else {
                        transaction.setCustomAnimations(R.anim.anim_fragment_open,R.anim.anim_fragment_close);
                        transaction.show(mStartPageFragment);
                        transaction.commit();
                    }
                    return true;
                case R.id.navigation_data:
                    if (Constants.TIMER_STATE.equals("WORKING")){
                        Toast.makeText(getApplicationContext(),"学习不要分心哦～",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    getSupportActionBar().show();
                    getSupportActionBar().setTitle(R.string.app_data);
                    if (mDataMainPageFragment == null){
                        mDataMainPageFragment = new DataMainPageFragment();
                        transaction.setCustomAnimations(R.anim.anim_fragment_move_from_top,R.anim.anim_fragment_move_to_top);
                        transaction.add(R.id.fl_content, mDataMainPageFragment);
                        transaction.show(mDataMainPageFragment);
                        transaction.commit();
                    }else {
                        transaction.setCustomAnimations(R.anim.anim_fragment_move_from_top,R.anim.anim_fragment_move_to_top);
                        transaction.show(mDataMainPageFragment);
                        transaction.commit();
                    }
                    return true;
                case R.id.navigation_user:
                    if (Constants.TIMER_STATE.equals("WORKING")){
                        Toast.makeText(getApplicationContext(),"学习不要分心哦～",Toast.LENGTH_SHORT).show();
                        break;
                    }

//                    try {
//                        // 去除隐藏actionbar动画
//                        getSupportActionBar().getClass().getDeclaredMethod("setShowHideAnimationEnabled",
//                                boolean.class).invoke(getSupportActionBar(),false);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    } catch (InvocationTargetException e) {
//                        e.printStackTrace();
//                    } catch (NoSuchMethodException e) {
//                        e.printStackTrace();
//                    }
                    getSupportActionBar().hide();
//                    getSupportActionBar().setTitle(R.string.app_user);
                    if (mUserPageFragment == null){
                        mUserPageFragment = new UserPageFragment();
                        transaction.setCustomAnimations(R.anim.anim_fragment_open,R.anim.anim_fragment_close);
                        transaction.add(R.id.fl_content,mUserPageFragment);
                        transaction.show(mUserPageFragment);
                        transaction.commit();
                    }else {
                        transaction.setCustomAnimations(R.anim.anim_fragment_open,R.anim.anim_fragment_close);
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
//            transaction.setCustomAnimations(R.anim.anim_fragment_open,R.anim.anim_fragment_close);
            transaction.hide(mStartPageFragment);
        }
        if (mDataMainPageFragment != null){
            transaction.setCustomAnimations(R.anim.anim_fragment_move_from_top,R.anim.anim_fragment_move_to_top);
            transaction.hide(mDataMainPageFragment);
            transaction.setCustomAnimations(R.anim.anim_fragment_open,R.anim.anim_fragment_close);
        }
        if (mUserPageFragment != null){
//            transaction.setCustomAnimations(R.anim.anim_fragment_open,R.anim.anim_fragment_close);
            transaction.hide(mUserPageFragment);
        }
    }





}
