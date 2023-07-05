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
import org.springframework.http.HttpMethod;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AutoLoginInterceptor implements HandlerInterceptor {

    private static final Logger logger = LogManager.getLogger("AutoLoginInterceptor");
    private final TokenService tokenService;
    private final UserService userService;

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
                if (tokenService.validateCookie(cookie)) {
                    String account = tokenService.getAccountFromCookie(cookie);

                    User user = userService.findByAccount(account);
                    request.getSession().setAttribute("user", user);
                    logger.info("auto login success");
                    return true;
                }
            }
        }
        logger.info(handler.getClass());
        // Check if the request handler is a method
        if (handler instanceof HandlerMethod handlerMethod) {
            // Check if the method has the SkipLoginCheck annotation
            NoLogin ifSkipLoginCheck = handlerMethod.getMethodAnnotation(NoLogin.class);
            if (ifSkipLoginCheck != null) {
                // Skip this request
                return true;
            }
        } else if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            // This is a CORS preflight request. Handle it accordingly.
            return true; // if you want to ignore/skip it
        }


        response.setStatus(401);
        logger.error("user need to login");
        return false;
    }
}
