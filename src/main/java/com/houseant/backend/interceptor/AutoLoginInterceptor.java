package com.houseant.backend.interceptor;

import com.houseant.backend.controller.UserController;
import com.houseant.backend.entity.User;
import com.houseant.backend.service.TokenService;
import com.houseant.backend.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class AutoLoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;
    private static final Logger logger = LogManager.getLogger("AutoLoginInterceptor");


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       logger.info("enter LoginInterceptor Modules");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user".equals(cookie.getName())) {

                    String token_account = tokenService.getAccountFromToken(cookie.getValue());
                    String token_passwd = tokenService.getPasswdFromToken(cookie.getValue());

                    // Validate the token
                    if (tokenService.validateToken(token_passwd)) {
                        // If the token is valid, get the user information from the token

                        String account = tokenService.getAccountFromToken(token_account);
                        User user = userService.findByAccount(account);
                        if (user != null) {
                            // Set the user in the session
                            request.getSession().setAttribute("user", user);
                            logger.info("auto login success");
                            return true;
                        }
                    }
                }
            }
        }
        return true;
    }
}
