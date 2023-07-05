package com.houseant.backend.service.impl;

import com.houseant.backend.entity.User;
import com.houseant.backend.service.TokenService;
import com.houseant.backend.service.UserService;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    private final UserService userService;
    private final EncryptService encryptService;

    private static final String cookieName = "user";
    private static final String splitChar = "%";

    @Autowired
    public TokenServiceImpl(UserService userService, EncryptService encryptService) {
        this.userService = userService;
        this.encryptService = encryptService;
    }

    @Override
    public boolean validateCookie(Cookie cookie) {
        if (cookie.getName().equals(cookieName)) {
            String[] parts = cookie.getValue().split(splitChar);

            if (parts.length == 2) {
                String account = parts[0];
                String encryptedPassword = parts[1];

                User user;
                if ((user = userService.findByAccount(account)) != null) {
                    return encryptedPassword.equals(encryptService.encrypt(user.getPassword()));
                }
            }
        }
        return false;
    }

    @Override
    public Cookie createTokenCookie(User user) {
        return new Cookie(
                cookieName,
                user.getAccount() + splitChar + encryptService.encrypt(user.getPassword())
        );
    }

    @Override
    public String getAccountFromCookie(Cookie cookie) {
        if (cookie.getName().equals(cookieName))
            return cookie.getValue().split(splitChar)[0];
        return null;
    }
}
