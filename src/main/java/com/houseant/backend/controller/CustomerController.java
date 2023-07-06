package com.houseant.backend.controller;

import com.houseant.backend.entity.Reservation;
import com.houseant.backend.entity.User;
import com.houseant.backend.service.HouseService;
import com.houseant.backend.service.ReservationService;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger logger = LogManager.getLogger(CustomerController.class);

    private final ReservationService reservationService;
    private final HouseService houseService;

    public CustomerController(ReservationService reservationService, HouseService houseService) {
        this.reservationService = reservationService;
        this.houseService = houseService;
    }

    // 1.确定预约
    // ReservationService --> insert
    @PostMapping("/addReservation")
    public ResponseEntity<Map<String, Object>> addReservation(
            @RequestBody Reservation reservation,
            @NonNull HttpServletRequest request) {
        String msg;
        reservation.setAccount(((User) (request.getSession().getAttribute("user"))).getAccount());
        reservationService.insert(reservation);
        msg = "addReservation Successfully";
        return ResponseEntity.ok().body(Map.of("message", msg));
    }

    // 2.查看预约
    // ReservationService --> findByAccount
    @GetMapping("/findByAccount")
    public ResponseEntity<Map<String, Object>> findByAccount(
            @NonNull HttpServletRequest request) {
        String msg;
        List<Reservation> res = reservationService
                .findByAccount(((User) (request.getSession().getAttribute("user"))).getAccount());
        msg = "findByAccount Successfully";
        return ResponseEntity.ok().body(Map.of(
                "result", res,
                "message", msg));
    }

    // 3.查看房源
    // HouseService --> findByKey
    @PostMapping("/findByKey")
    public ResponseEntity<Map<String, Object>> findByKey(
            @NonNull HttpServletRequest request,
            @NonNull @RequestBody Map<String, Object> params) {
        String msg;
        houseService.findByKey(params);
        msg = "findByKey Successfully";
        return ResponseEntity.ok().body(Map.of("message", msg));
    }

    @DeleteMapping("deleteReservation")
    public ResponseEntity<Map<String, Object>> deleteReservation(@RequestParam int id,
                                                                 @NonNull HttpServletRequest request) {
        String msg;
        Reservation res = (reservationService.findById(id));
        if (res.getAccount().equals(((User) (request.getSession().getAttribute("user"))).getAccount())) {
            reservationService.delete(id);
            msg = "Delete successfully";
            return ResponseEntity.ok().body(Map.of("message", msg));
        } else {
            msg = "Records not found, check your account or insert a new reservation";
            return ResponseEntity.ok().body(Map.of("message", msg));
        }

    }

    @PostMapping("/updateReservationInfo")
    public ResponseEntity<String> updateHouseInfo(@RequestBody Reservation reservation,
                                                  @NonNull HttpServletRequest request) {
        Reservation newReservation = reservationService.findById(reservation.getRes_id());
        if (reservation.getContent() != null) newReservation.setContent(reservation.getContent());
        if (reservation.getType() != null) newReservation.setType(reservation.getType());
        reservationService.update(newReservation);
        return ResponseEntity.ok().body("successfully update a reservation info");
    }
}