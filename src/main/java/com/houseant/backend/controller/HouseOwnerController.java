package com.houseant.backend.controller;

import com.houseant.backend.entity.House;
import com.houseant.backend.entity.User;
import com.houseant.backend.service.HouseService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.ibatis.annotations.Delete;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/house")
public class HouseOwnerController {
    private static final Logger logger = LogManager.getLogger(HouseOwnerController.class);
    private HouseService houseService;
    @Autowired
    public HouseOwnerController(HouseService houseService){
        this.houseService=houseService;
    }

    //1.添加房源
    //  HouseService -->  insert
    @PostMapping("/addHouseInfo")
    public ResponseEntity<?> addHouseInfo(@RequestBody House newHouse, @NonNull HttpServletRequest request){
        newHouse.setAccount(((User)request.getSession().getAttribute("user")).getAccount());
        houseService.insert(newHouse);
        logger.info("successfully insert a house info");
        return ResponseEntity.ok().body("successfully insert a house info");

    }
    //2.修改房源
    //  HouseService -->  findByKey 筛选本人的房源
    //  HouseService -->  update
    @PostMapping("/updateHouseInfo")
    public ResponseEntity<?>updateHouseInfo (@RequestBody House newHouse, @NonNull HttpServletRequest request){
        newHouse.setAccount(((User)request.getSession().getAttribute("user")).getAccount());
        houseService.update(newHouse);
        logger.info("successfully update a house info");
        return ResponseEntity.ok().body("successfully update a house info");
    }
    //3.删除房源
    //  HouseService -->  findByKey 筛选本人的房源
    //  HouseService -->  dalete
    @DeleteMapping("deleteHouseInfo")
    public ResponseEntity<?>deleteHouseInfo(@RequestParam int id,@NonNull HttpServletRequest request){
        if(houseService.findByKey(Map.of(
                "account", ((User)request.getSession().getAttribute("user")).getAccount(),
                "id" ,id))==null){
            return ResponseEntity.ok().body(Map.of("message","There is no listing information or the user is incorrect"));

        }
        else {
            houseService.delete(id);
            return ResponseEntity.ok().body(Map.of("message","successfully delete a HouseInfo id:"+id));
        }


    }

    //4.查看我的房源
    //  HouseService -->  findByKey 筛选本人的房源
    @PostMapping("/search")
    public ResponseEntity<?> search( @NonNull @RequestBody Map<String, Object> params,@NonNull HttpServletRequest request) {
        logger.info("sdgfsgf123");
        String account_get=((User) request.getSession().getAttribute("user")).getAccount();
        params.put("account",account_get);
        List<House> houseList=houseService.findByKey(params);
        if(houseList==null) {
            return ResponseEntity.ok().body(Map.of(
                    "message","houseInfo does not exist"
            ));
        }
        else {
            return ResponseEntity.ok().body(Map.of(
                    "message",("find houseInfo:"+houseList.size()),
                    "houses",houseList
            ));
        }

    }

}
