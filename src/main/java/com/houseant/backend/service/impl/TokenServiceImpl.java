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
        String[] parts = token.split("%");
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
        String passwd=user.getPassword();
        String account=user.getAccount();
        String encodePW=encryptService.encrypt(passwd);
        String cookieMsg=account+"%"+encodePW;
        Cookie newcookie=new Cookie("user",cookieMsg);
        return newcookie;

    }

    @Override
    public String getAccountFromToken(String token) {
        String[] tokenMsg= token.split("%");
        return  tokenMsg[0];

    }

    @Override
    public String getPasswdFromToken(String token) {
        String[] tokenMsg= token.split("%");
        return  tokenMsg[1];
    }
}
