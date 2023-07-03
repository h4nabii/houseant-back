package com.houseant.backend.service;

import com.houseant.backend.entity.User;

public interface TokenService {
    public boolean validateToken(String token);
    public String createToken(User user);

    String getAccountFromToken(String token);
}
