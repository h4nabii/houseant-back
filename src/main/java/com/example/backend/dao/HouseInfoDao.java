package com.example.backend.dao;

import com.example.backend.entity.House;
import org.apache.ibatis.annotations.Mapper;
import org.w3c.dom.ls.LSInput;

import java.util.List;
import java.util.Map;

@Mapper
public interface HouseInfoDao {
    List<House> findall();
    List<House> findbykey(Map<String, Object> params);
    void insert(House house);
    void delete(Integer id);
    void update(House house);

}
