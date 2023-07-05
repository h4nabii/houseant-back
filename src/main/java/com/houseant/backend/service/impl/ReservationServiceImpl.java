package com.houseant.backend.service.impl;

import com.houseant.backend.dao.ReservationDAO;
import com.houseant.backend.entity.Reservation;
import com.houseant.backend.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationDAO reservationDAO;

    @Autowired
    public ReservationServiceImpl(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    @Override
    public List<Reservation> findAll() {
        return reservationDAO.findAll();
    }

    @Override
    public List<Reservation> findByAccount(String account) {
        return reservationDAO.findByAccount(account);
    }

    @Override
    public Reservation findById(int id) {
        return reservationDAO.findById(id);
    }

    @Override
    public void create(Reservation reservation) {
        reservationDAO.insert(reservation);
    }

    @Override
    public void insert(Reservation reservation) {
        reservationDAO.insert(reservation);
    }

    @Override
    public void delete(Integer resId) {
        reservationDAO.delete(resId);
    }

    @Override
    public void update(Reservation reservation) {
        reservationDAO.update(reservation);
    }
}
