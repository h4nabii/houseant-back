package com.houseant.backend.controller;

import com.houseant.backend.annotations.NoLogin;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HomePageController {

    @NoLogin
    @GetMapping("/")
    public ResponseEntity<?> home() {
        // Your processing code goes here...
        var responseMsg = new HashMap<String, Object>();
        responseMsg.put("message", "Welcome to the home page!");
        return ResponseEntity.ok().body(responseMsg);
    }
}