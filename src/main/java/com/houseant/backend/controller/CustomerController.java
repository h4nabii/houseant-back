package com.houseant.backend.controller;

import com.houseant.backend.entity.House;
import com.houseant.backend.entity.Reservation;
import com.houseant.backend.entity.User;
import com.houseant.backend.service.HouseService;
import com.houseant.backend.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final ReservationService reservationService;
    private final HouseService houseService;
    @Autowired
    public CustomerController(ReservationService reservationService, HouseService houseService){
        this.reservationService=reservationService;
        this.houseService=houseService;
    }
    //1.确定预约
    //  ReservationService -->   insert
    @PostMapping("/addReservation")
    public ResponseEntity<?>addReservation(
            @RequestBody Reservation reservation,
            @NonNull HttpServletRequest request){
        String msg;
        reservation.setAccount(((User)(request.getSession().getAttribute("user"))).getAccount());
        reservationService.create(reservation);
        msg = "addReservation Successfully";
        return ResponseEntity.ok(Map.of("message", msg));
    }
    //2.查看预约
    //  ReservationService  -->  findByAccount
    @PostMapping("/findByAccount")
    public ResponseEntity<?>findByAccount(
            @NonNull HttpServletRequest request){
        String msg;
        List<Reservation> res = reservationService.findByAccount(((User) (request.getSession().getAttribute("user"))).getAccount());
        msg = "findByAccount Successfully";
        return ResponseEntity.ok(Map.of(
                        "result",res,
                        "message",msg
                ));
    }
    //3.查看房源
    //  HouseService  -->  findByKey
    @PostMapping("/findByKey")
    public ResponseEntity<?>findByKey(
            @NonNull HttpServletRequest request,
            @NonNull @RequestBody Map<String, Object> params){
        String msg;
        houseService.findByKey(params);
        msg = "findByKey Successfully";
        return ResponseEntity.ok(Map.of("message",msg));
    }
}