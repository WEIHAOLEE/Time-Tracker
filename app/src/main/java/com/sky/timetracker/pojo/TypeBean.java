package com.sky.timetracker.pojo;

public class TypeBean {
    private String type;
    private float time;

    public TypeBean(String type, float time) {
        this.type = type;
        this.time = time;
    }

    @Override
    public String toString() {
        return "TypeBean{" +
                "type='" + type + '\'' +
                ", time=" + time +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }
}
