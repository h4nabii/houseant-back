package com.example.backend.service.impl;

import com.example.backend.dao.ReservationDao;
import com.example.backend.entity.Reservation;
import com.example.backend.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReservationServiceImpl implements ReservationService {
    private final ReservationDao reservationDao;
    @Autowired
    public ReservationServiceImpl(ReservationDao reservationDao) {
        this.reservationDao = reservationDao;
    }
    @Override
    public List<Reservation> findAll() {
        return reservationDao.findAll();
    }

    public List<Reservation> findByAccount(String name){ return reservationDao.findByAccount(name);}
    @Override
    public void insert(Reservation reservation) {
        reservationDao.insert(reservation);
    }
    @Override
    public void delete(Integer resId) {
        reservationDao.delete(resId);
    }
    @Override
    public void update(Reservation reservation) {
        reservationDao.update(reservation);
    }
}
