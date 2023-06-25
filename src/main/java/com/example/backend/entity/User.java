package com.example.backend.entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int user_id;
    private String account;
    private String username;
    private String password;
    private String tel;
    private Boolean status;
    private String avatar;

    public User(int user_id, String account, String username, String password, String tel, Boolean status, String avatar) {
        this.user_id = user_id;
        this.account = account;
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.status = status;
        this.avatar = avatar;
    }

    public int getUser_id() {
        return user_id;
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

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
                "user_id=" + user_id +
                ", account='" + account + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                ", status=" + status +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}



