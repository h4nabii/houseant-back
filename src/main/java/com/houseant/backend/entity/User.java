package com.houseant.backend.entity;

import java.util.Map;

public class User {
    private int userId;
    private String account;
    private String username;
    private String password;
    private String tel;
    private Boolean status;
    private String avatar;

    public User(int userId, String account, String username, String password, String tel, Boolean status, String avatar) {
        this.userId = userId;
        this.account = account;
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.status = status;
        this.avatar = avatar;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + userId +
                ", account='" + account + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                ", status=" + status +
                ", avatar='" + avatar + '\'' +
                '}';
    }

    public Map<String, Object> getUserMsgExceptPasswd() {
        return Map.of(
                "account", getAccount(),
                "username", getAccount(),
                "tel", getTel(),
                "status", getStatus()
        );
    }
}



