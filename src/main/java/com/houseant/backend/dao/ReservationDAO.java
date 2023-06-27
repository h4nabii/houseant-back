package com.houseant.backend.dao;

import com.houseant.backend.entity.Reservation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReservationDAO {
    List<Reservation> findAll();

    List<Reservation> findByKey(Map<String, Object> params);

    void insert(Reservation reservation);

    void delete(Integer res_id);

    void update(Reservation reservation);

}
