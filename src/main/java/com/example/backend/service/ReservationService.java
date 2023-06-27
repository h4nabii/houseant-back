package com.example.backend.service;

import com.example.backend.entity.Reservation;
import com.example.backend.entity.User;

import java.util.List;
import java.util.Map;

public interface ReservationService {
    List<Reservation> findAll();
    List<Reservation> findByAccount(String account);
    void insert(Reservation reservation);
    void delete(Integer res_id);
    void update(Reservation reservation);
}
