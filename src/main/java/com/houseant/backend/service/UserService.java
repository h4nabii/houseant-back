package com.houseant.backend.service;

import com.houseant.backend.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> findAll();

    List<User> findByKey(Map<String, Object> params);

    void create(User user);

    void removeByID(Integer userId);

    void update(User user);

    User findByAccount(String account);

    String generateAccount();

    boolean isAccountAvailable(String Account);


}
