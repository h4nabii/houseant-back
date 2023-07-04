package com.houseant.backend.interceptor;

import com.houseant.backend.entity.User;
import com.houseant.backend.service.TokenService;
import com.houseant.backend.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AutoLoginInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    private final UserService userService;
    private static final Logger logger = LogManager.getLogger("AutoLoginInterceptor");

    @Autowired
    public AutoLoginInterceptor(TokenService tokenService, UserService userService) {
        this.tokenService = tokenService;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull Object handler) {

        logger.info("Enter LoginInterceptor Modules");

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user".equals(cookie.getName())) {

                    String token_account = tokenService.getAccountFromToken(cookie.getValue());
                    String token_passwd = tokenService.getPasswdFromToken(cookie.getValue());

                    // Validate the token
                    if (tokenService.validateToken(cookie.getValue())) {
                        // If the token is valid, get the user information from the token

                        User user = userService.findByAccount(token_account);
                        request.getSession().setAttribute("user", user);
                        logger.info("auto login success");
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
