package com.sky.timetracker.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sky.timetracker.Constants;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VESION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建数据库
        // 创建table
        String sql = "create table " + Constants.TABLE_NAME_RECORD + "(record_id integer PRIMARY KEY AUTOINCREMENT, record_name varchar, record_time integer, record_date integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 升级更新数据库

    }
}
