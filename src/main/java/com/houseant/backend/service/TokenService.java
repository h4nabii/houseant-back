package com.houseant.backend.service;

import com.houseant.backend.entity.User;
import jakarta.servlet.http.Cookie;

public interface TokenService {
    public boolean validateToken(String token);
    public Cookie createToken(User user);

    String getAccountFromToken(String token);
    String getPasswdFromToken(String token);
}
