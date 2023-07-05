package com.houseant.backend.controller;

import com.houseant.backend.entity.House;
import com.houseant.backend.service.HouseService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owner")
public class HouseOwnerController {
    private static final Logger logger = LogManager.getLogger(HouseOwnerController.class);

    private final HouseService houseService;

    @Autowired
    public HouseOwnerController(HouseService houseService) {
        this.houseService = houseService;
    }

    @ResponseBody
    @PostMapping("/add-source")
    public ResponseEntity<?> addSource(
            @NonNull @RequestBody House house,
            @NonNull HttpServletRequest request
    ) {
        logger.info("New Source from " + request.getRemoteAddr());



        return null;
    }

    //1.添加房源
    //  HouseService -->  insert
    //2.修改房源
    //  HouseService -->  findByKey 筛选本人的房源
    //  HouseService -->  update
    //3.删除房源
    //  HouseService -->  findByKey 筛选本人的房源
    //  HouseService -->  dalete
    //4.查看我的房源
    //  HouseService -->  findByKey 筛选本人的房源
}
