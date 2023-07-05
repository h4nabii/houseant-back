package com.houseant.backend.service.impl;

import com.houseant.backend.dao.HouseInfoDAO;
import com.houseant.backend.entity.House;
import com.houseant.backend.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HouseServiceImpl implements HouseService {
    private final HouseInfoDAO houseInfoDAO;

    @Autowired
    public HouseServiceImpl(HouseInfoDAO houseInfoDAO) {
        this.houseInfoDAO = houseInfoDAO;
    }

    @Override
    public List<House> findAll() {
        return houseInfoDAO.findAll();
    }

    @Override
    public List<House> findByKey(Map<String, Object> params) {
        return houseInfoDAO.findByKey(params);
    }

    @Override
    public void insert(House house) {
        houseInfoDAO.insert(house);

    }

    @Override
    public void delete(Integer house_id) {
        houseInfoDAO.delete(house_id);
    }

    @Override
    public void update(House house) {
        houseInfoDAO.update(house);
    }
}
