package com.example.backend.dao;

import com.example.backend.entity.Reservation;
import com.example.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReservationDao {
    List<Reservation> findAll();
    List<Reservation> findByAccount(String name);
    void insert(Reservation reservation);
    void delete(Integer res_id);
    void update(Reservation reservation);

}
