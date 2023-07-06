package com.houseant.backend.service.impl;

import com.houseant.backend.dao.UserDAO;
import com.houseant.backend.entity.User;
import com.houseant.backend.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public List<User> findByKey(Map<String, Object> params) {
        return userDAO.findByKey(params);
    }

    @Override
    public void create(User user) {
        userDAO.insert(user);
    }

    @Override
    public void removeByID(Integer userId) {
        userDAO.delete(userId);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public User findByAccount(String account) {
        return userDAO.findByAccount(account);
    }

    @Override
    public String generateAccount() {
        return String.valueOf(new Date().getTime());
    }

    @Override
    public boolean isAccountAvailable(String account) {
        return userDAO.findByAccount(account) == null;
    }
}
