package com.houseant.backend.entity;

import java.util.Map;

public class User {
    private int id;
    private String account;
    private String username;
    private String password;
    private String tel;
    private Boolean status;
    private String avatar;

    public User(int id, String account, String username, String password, String tel, Boolean status, String avatar) {
        this.id = id;
        this.account = account;
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.status = status;
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTel() {
        return tel;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + id +
                ", account='" + account + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                ", status=" + status +
                ", avatar='" + avatar + '\'' +
                '}';
    }
    public Map getUserMsgExceptPasswd()
    {
        return Map.of(
                "account",getAccount(),
                "username",getAccount(),
                "tel",getTel(),
                "status",getStatus()
        );
    }
}



