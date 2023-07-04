package com.houseant.backend.service.impl;

import com.houseant.backend.entity.User;
import com.houseant.backend.service.TokenService;
import com.houseant.backend.service.UserService;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    private String key;
    @Autowired
    private UserService userService;

    @Autowired
    private EncryptService encryptService;
    @Override
    public boolean validateToken(String token) {
        return true;
    }

    @Override
    public Cookie createToken(User user) {
        String passwd=user.getPassword();
        String account=user.getAccount();
        String encodePW=encryptService.encrypt(passwd);
        String cookieMsg=account+","+encodePW;
        Cookie newcookie=new Cookie("user",cookieMsg);
        return newcookie;

    }

    @Override
    public String getAccountFromToken(String token) {
        String[] tokenMsg= token.split(",");
        return  tokenMsg[0];

    }

    @Override
    public String getPasswdFromToken(String token) {
        String[] tokenMsg= token.split(",");
        return  tokenMsg[1];
    }
}
