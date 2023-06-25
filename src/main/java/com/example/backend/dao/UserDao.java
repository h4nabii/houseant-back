package com.example.backend.dao;

import com.example.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao {
    List<User> findAll();
    List<User> findByKey(Map<String, Object> params);
    void insert(User user);
    void delete(Integer user_id);
    void update(User user);

}
