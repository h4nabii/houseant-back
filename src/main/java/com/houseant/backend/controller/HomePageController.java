package com.houseant.backend.controller;

import com.houseant.backend.annotations.NoLogin;
import com.houseant.backend.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HomePageController {

    @NoLogin
    @GetMapping("/")
    public ResponseEntity<?> home( @NonNull HttpServletRequest request) {
        // Your processing code goes here...
        var responseMsg = new HashMap<String, Object>();
        responseMsg.put("auto-login",true);
        var session=request.getSession();
        if(session!=null) {
            responseMsg.put("account", ((User)request.getSession().getAttribute("user")).getAccount());
        }
        return ResponseEntity.ok().body(responseMsg);
    }
}