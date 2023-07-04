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
    public boolean validateToken(String token) {
        String[] parts = token.split(splitChar);
        if (parts.length != 2) {
            return false;
        }

        String account = parts[0];
        String encryptedPassword = parts[1];

        User user = userService.findByAccount(account);
        if (user == null) {
            return false;
        }

        String actualEncryptedPassword = encryptService.encrypt(user.getPassword());

        return encryptedPassword.equals(actualEncryptedPassword);
    }


    @Override
    public Cookie createToken(User user) {
        return new Cookie(
                cookieName,
                user.getPassword() + splitChar + encryptService.encrypt(user.getAccount())
        );

    }

    @Override
    public String getAccountFromToken(String token) {
        return token.split(splitChar)[0];
    }

    @Override
    public String getPasswdFromToken(String token) {
        return token.split(splitChar)[1];
    }
}
