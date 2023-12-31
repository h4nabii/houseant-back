package com.houseant.backend.service;

import com.houseant.backend.entity.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> findAll();

    List<Reservation> findByAccount(String account);

    Reservation findById(int id);

    void create(Reservation reservation);

    void insert(Reservation reservation);

    void delete(Integer res_id);

    void update(Reservation reservation);
}
