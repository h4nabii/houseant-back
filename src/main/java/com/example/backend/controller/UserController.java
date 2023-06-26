package com.example.backend.controller;

import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, Object> params, HttpServletResponse response,
                                   HttpServletRequest request) {
        Logger logger = LogManager.getLogger(UserController.class);
        logger.info("enter login model");
        String UserAccount=(String) params.get("account");
        String Passwd_get=(String) params.get("password");
        User user=userService.findByAccount(UserAccount);
        if(user==null){
            logger.warn("user not found");
            return ResponseEntity.badRequest().body("User not found");

        }
        else if(user.getPassword().equals(Passwd_get)) {
            // Create a cookie
            Cookie cookie = new Cookie("user", user.getUsername());
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            response.addCookie(cookie);

            // Create a session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            logger.info("login success");

            return ResponseEntity.ok().body("Login success");
        }
        else {
            logger.warn("wrong password");
            logger.warn(user);
            return ResponseEntity.badRequest().body("Wrong password");
        }

    }
}
