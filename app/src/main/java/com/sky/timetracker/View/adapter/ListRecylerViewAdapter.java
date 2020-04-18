package com.sky.timetracker.View.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sky.timetracker.R;
import com.sky.timetracker.pojo.DataBean;

import java.util.List;

public class ListRecylerViewAdapter extends RecyclerView.Adapter<ListRecylerViewAdapter.InnerHolder> {
    private final List<DataBean> mData;
    private OnLongClickListener mLongClickListener;

    public ListRecylerViewAdapter(List<DataBean> dataBeanList) {
        this.mData = dataBeanList;
    }


    public void setLongClickListener(OnLongClickListener longClickListener){
        this.mLongClickListener = longClickListener;
    }
    public interface OnLongClickListener {
        void onLongClick(int position);
    }

    /**
     * 这个方法用于创建条目的view
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public InnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 传进去的view就是条目的界面 你想显示为什么样子
        // 拿到view
        // 创建 InnerHolder
        View view = View.inflate(parent.getContext(), R.layout.item_list_view, null);
        return new InnerHolder(view);
    }

    /**
     * 绑定holder 用来设置数据
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull InnerHolder holder, final int position) {
        // 设置数据
        holder.setData(mData.get(position));
        if (mLongClickListener != null){

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    mLongClickListener.onLongClick(position);
                    return false;
                }
            });
        }
    }

    /**
     * 返回条目个数
     * @return
     */
    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        }
        return 0;
    }

    public class InnerHolder extends RecyclerView.ViewHolder {

        private  TextView mTvMissionName;
        private  TextView mTvMissionTime;
        private  TextView mTvMissionDate;
        private  View mCiewById;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            // 找到条目控件 findViewById
            mCiewById = itemView.findViewById(R.id.item_main_view);
            mTvMissionName = itemView.findViewById(R.id.item_tv_mission_name);
            mTvMissionTime = itemView.findViewById(R.id.item_tv_mission_time);
            mTvMissionDate = itemView.findViewById(R.id.item_tv_mission_date);
        }

        /**
         * 设置数据
         * @param dataBean
         */
        public void setData(DataBean dataBean) {
            mTvMissionName.setText(dataBean.getName());
            mTvMissionTime.setText(dataBean.getTime());
            mTvMissionDate.setText(dataBean.getDate());
        }
    }
}
