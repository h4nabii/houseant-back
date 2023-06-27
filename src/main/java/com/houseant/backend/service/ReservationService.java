package com.houseant.backend.service;

import com.houseant.backend.entity.Reservation;

import java.util.List;
import java.util.Map;

public interface ReservationService {
    List<Reservation> findAll();

    List<Reservation> findByKey(Map<String, Object> params);

    void insert(Reservation reservation);

    void delete(Integer res_id);

    void update(Reservation reservation);
}
