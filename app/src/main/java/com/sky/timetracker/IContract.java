package com.sky.timetracker;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.sky.timetracker.pojo.DataBean;

import java.util.List;

public interface IContract {

    interface IModel{
        // 使用Dao查询datalist
        List<DataBean> queryDataList(Context context);
        // 用Dao插入数据
        void insertData(Context context, String missionName, int time, int date);
        // 根据删除data数据
        void deleteData(Context context,int id);
    }

    interface IPresenter{
        // 获取dataBeanList
        void getDataBeanList(Context context);

        boolean deleteData(Context context);



    }

    interface IPresenterTimer{
        /**
         * 输入data给M层
         * @param missionName insert 的 任务名称
         */
        void setData(String missionName);

    }

    interface IView{
        // 从p层返回dataBeanList
        void dataBeanListReturn(List<DataBean> dataBeanList);

        int userDelete();



        interface IViewStartPage{
            //  展示弹窗 返回传给p层任务名字
            void showDialog(int time, int date);

            void showToast(String content);

//            void hideDialog();
        }
    }
}
