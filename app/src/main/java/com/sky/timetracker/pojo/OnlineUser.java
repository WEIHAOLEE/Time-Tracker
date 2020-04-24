package com.sky.timetracker.pojo;

public class OnlineUser {
    private String uId;
    private String uName;
    private String uPwd;
    private String uGender;

    @Override
    public String toString() {
        return "OnlineUser{" +
                "uId='" + uId + '\'' +
                ", uName='" + uName + '\'' +
                ", uPwd='" + uPwd + '\'' +
                ", uGender='" + uGender + '\'' +
                '}';
    }

    public OnlineUser(String uId, String uName, String uPwd, String uGender) {
        this.uId = uId;
        this.uName = uName;
        this.uPwd = uPwd;
        this.uGender = uGender;
    }

    public OnlineUser() {
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuPwd() {
        return uPwd;
    }

    public void setuPwd(String uPwd) {
        this.uPwd = uPwd;
    }

    public String getuGender() {
        return uGender;
    }

    public void setuGender(String uGender) {
        this.uGender = uGender;
    }
}
