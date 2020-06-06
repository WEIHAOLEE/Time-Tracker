package com.sky.timetracker.View.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.sky.timetracker.IContract;
import com.sky.timetracker.Model.ModelImpl;
import com.sky.timetracker.Presenter.PresenterImpl;
import com.sky.timetracker.R;
import com.sky.timetracker.View.adapter.ListRecylerViewAdapter;
import com.sky.timetracker.pojo.DataBean;

import java.util.List;

public class DataPageFragment extends Fragment implements IContract.IView {

    private RecyclerView mRvList;
    private List<DataBean> mDataList;
    private View mView;
    private PresenterImpl presenter;
    private int mDataId;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    /**
     * Fragment调用show 和hide 时候会回调这个方法
     * 这里用来刷新 RecyclerView
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            // 隐藏
        }else {
            // 显示
            setRecycleview(mView);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_data_page, container, false);

        initView();
        initPresenter();
        setRecycleview(mView);
        return mView;

    }

    private void initView() {
        mSwipeRefreshLayout = mView.findViewById(R.id.refresh);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setRecycleview(mView);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }


    private void setRecycleview(final View view) {
        // 找到控件
        mRvList = view.findViewById(R.id.rv_list);
        // 准备 得到数据
        presenter.getDataBeanList(view.getContext());
        // RecyclerView 需要设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        mRvList.setLayoutManager(layoutManager);
        // 创建适配器 adapter
        final ListRecylerViewAdapter adapter = new ListRecylerViewAdapter(mDataList);

        adapter.setLongClickListener(new ListRecylerViewAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(int position) {
                mDataId = mDataList.get(position).getId();
                Log.d("测试","position is -->" + position + "data id is -->" + mDataId);
                mDataList.remove(position);
                boolean deleteState = presenter.deleteData(view.getContext());

                if (deleteState){
                    Log.d("list", String.valueOf(mDataList));

                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position,mDataList.size());
                }
            }
        });
        // 设置adapter 到 RecyclerView
        mRvList.setAdapter(adapter);


    }



    private void initPresenter(){
        presenter = new PresenterImpl(ModelImpl.getInstance(),this);
    }
    @Override
    public void dataBeanListReturn(List<DataBean> dataBeanList) {
        mDataList =dataBeanList;
    }

    @Override
    public int userDelete() {
        if (mDataId != 0){
            return mDataId;
        }
        return 0;
    }
}
