package com.houseant.backend.entity;

public class Reservation {
    private int res_id;
    private String account;
    private String time;
    private String type;
    private String content;

    public Reservation(int res_id, String account, String time, String type, String content) {
        this.res_id = res_id;
        this.account = account;
        this.time = time;
        this.type = type;
        this.content = content;
    }

    public int getRes_id() {
        return res_id;
    }

    public void setRes_id(int res_id) {
        this.res_id = res_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "res_id=" + res_id +
                ", account='" + account + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
