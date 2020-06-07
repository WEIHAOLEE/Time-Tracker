package com.sky.timetracker.Model.DAO;

import java.util.List;

public interface Dao {
    // 增
    void insert(String name, int time, int date, String type);
    // 改
    void update();
    // 删
    void delete(int id);
    // 查
    void query();

    List dataList();
}
