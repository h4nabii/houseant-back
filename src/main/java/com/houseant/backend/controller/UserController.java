package com.houseant.backend.controller;

import com.houseant.backend.entity.User;
import com.houseant.backend.service.TokenService;
import com.houseant.backend.service.UserService;
import com.houseant.backend.service.impl.EncryptService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserService userService;
    private  final TokenService tokenService;

    @Autowired
    public UserController(UserService userService ,TokenService tokenService) {
        this.userService = userService;
        this.tokenService=tokenService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody Map<String, Object> params,
            HttpServletResponse response,
            HttpServletRequest request) {

        String cookies;
        if (request.getCookies() != null) {
            cookies = Arrays.stream(request.getCookies())
                    .map(cookie -> cookie.getName() + "=" + cookie.getValue())
                    .collect(Collectors.joining(","));
            logger.info(cookies);

            logger.info("get cookies");
        }

        logger.info("Entering login model");

        var responseMsg = new HashMap<String, Object>();

        String accountGet = (String) params.get("account");
        String passwordGet = (String) params.get("password");

        User user = userService.findByAccount(accountGet);
        if (user == null) {
            logger.warn("User not found");

            // Response data
            responseMsg.put("login", false);
            responseMsg.put("message", "User not found");

        } else if (user.getPassword().equals(passwordGet)) {
            // Create a cookie

            Cookie cookie = tokenService.createToken(user);
            cookie.setMaxAge(7 * 24 * 60 * 60); // 7 days
            cookie.setPath("/");
            logger.info(cookie.getValue());
            response.addCookie(cookie);

            // Create a session
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            logger.info("Login succeed");

            // Response data
            responseMsg.put("login", true);
            responseMsg.put("message", "Login succeed");

        } else {
            logger.warn(user);
            logger.warn("wrong password");

            // Response data
            responseMsg.put("login", false);
            responseMsg.put("message", "Wrong password");
        }

        if (request.getCookies() != null) {
            cookies = Arrays.stream(request.getCookies())
                    .map(cookie -> cookie.getName() + "=" + cookie.getValue())
                    .collect(Collectors.joining(","));
            logger.info(cookies);
        }

        return ResponseEntity.ok().body(responseMsg);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletResponse response,
            HttpServletRequest request) {
        Logger logger = LogManager.getLogger(UserController.class);
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        Cookie cookie = new Cookie("user", null); // overwrite with a null value
        cookie.setMaxAge(0); // 0 means the cookie will be deleted immediately
        response.addCookie(cookie);

        logger.info("Logout succeed");
        return ResponseEntity.ok().body("Logout success");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User newUser) {
        Logger logger = LogManager.getLogger(UserController.class);
        // Check if account is already used
        User existingUser = userService.findByAccount(newUser.getAccount());
        if (existingUser != null) {
            logger.warn("Account already exists");
            return ResponseEntity.badRequest().body("Account already exists");
        }

        // TODO: You may want to encrypt the password before saving it to the database
        userService.create(newUser);
        logger.info("User registered successfully");
        return ResponseEntity.ok().body("User registered successfully");
    }

    @GetMapping("/cookies")
    public String getCookies(HttpServletRequest request) {
        String cookies = null;
        var cookiesArr = request.getCookies();
        if (cookiesArr != null) {
            cookies = Arrays.stream(cookiesArr)
                    .map(cookie -> cookie.getName() + "=" + cookie.getValue())
                    .collect(Collectors.joining(","));
            logger.info("Cookies: " + cookies);
        } else {
            cookies = "No cookies";
        }
        return "Cookies: " + cookies;
    }

    @GetMapping("/autologin")
    public ResponseEntity<?> autologin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            // If the user is logged in, return a successful response
            return ResponseEntity.ok().body(Map.of(
                    "login", true,
                    "message", "Auto login successful"
            ));
        } else {
            // If the user is not logged in, return a failed response
            return ResponseEntity.ok().body(Map.of(
                    "login", false,
                    "message", "Auto login failed"
            ));
        }
    }
}
