package com.sky.timetracker.pojo;

public class DataBean {
    private String name;
    private int id;
    private String date;
    private String time;
    private String type;

    @Override
    public String toString() {
        return "DataBean{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DataBean(String name, int id, String date, String time, String type) {
        this.name = name;
        this.id = id;
        this.date = date;
        this.time = time;
        this.type = type;
    }

    public DataBean() {
    }
}
