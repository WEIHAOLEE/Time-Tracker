package com.sky.timetracker.pojo;

import java.util.List;

public class BackUpBean {
    private List<DataBean> dataBeanList;

    public BackUpBean() {
    }

    public BackUpBean(List<DataBean> dataBeanList, int count) {
        this.dataBeanList = dataBeanList;
        this.count = count;
    }

    @Override
    public String toString() {
        return "BackUpBean{" +
                "dataBeanList=" + dataBeanList +
                ", count=" + count +
                '}';
    }

    public List<DataBean> getDataBeanList() {
        return dataBeanList;
    }

    public void setDataBeanList(List<DataBean> dataBeanList) {
        this.dataBeanList = dataBeanList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    private int count;
}
