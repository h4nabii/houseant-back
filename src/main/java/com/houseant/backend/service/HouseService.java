package com.houseant.backend.service;

import com.houseant.backend.entity.House;

import java.util.List;
import java.util.Map;

public interface HouseService {
    List<House> findAll();

    List<House> findByKey(Map<String, Object> params);

    void insert(House house);

    void delete(Integer house_id);

    void update(House house);


}
