package com.houseant.backend.service.impl;

import com.houseant.backend.entity.User;
import com.houseant.backend.service.TokenService;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {
    private String key;
    @Override
    public boolean validateToken(String token) {
        return true;
    }

    @Override
    public String createToken(User user) {
        return null;
    }

    @Override
    public String getAccountFromToken(String token) {
        return token;
    }
}
