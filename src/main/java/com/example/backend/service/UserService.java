package com.example.backend.service;

import com.example.backend.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> findAll();
    List<User> findByKey(Map<String, Object> params);
    void insert(User user);
    void delete(Integer userId);
    void update(User user);
    User findByAccount(String account);
}
