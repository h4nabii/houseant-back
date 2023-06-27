package com.houseant.backend.dao;

import com.houseant.backend.entity.House;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface HouseInfoDAO {
    List<House> findAll();

    List<House> findByKey(Map<String, Object> params);

    void insert(House house);

    void delete(Integer id);

    void update(House house);

}
