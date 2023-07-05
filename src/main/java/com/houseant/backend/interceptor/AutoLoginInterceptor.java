package com.houseant.backend.interceptor;

import com.houseant.backend.annotations.NoLogin;
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
import org.springframework.web.method.HandlerMethod;
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
        // Check if the request handler is a method
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // Check if the method has the SkipLoginCheck annotation
            NoLogin ifSkipLoginCheck = handlerMethod.getMethodAnnotation(NoLogin.class);
            if (ifSkipLoginCheck != null) {
                // Skip this request
                return true;
            }
        }

        logger.info("Enter LoginInterceptor Modules");

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (tokenService.validateCookie(cookie)) {
                    String account = tokenService.getAccountFromCookie(cookie);

                    User user = userService.findByAccount(account);
                    request.getSession().setAttribute("user", user);
                    logger.info("auto login success");
                    return true;
                }
            }
        }

        response.setStatus(401);
        logger.error("user need to login");
        return false;
    }
}
