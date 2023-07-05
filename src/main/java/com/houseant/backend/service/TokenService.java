package com.houseant.backend.service;

import com.houseant.backend.entity.User;
import jakarta.servlet.http.Cookie;

public interface TokenService {
    boolean validateCookie(Cookie cookie);

    Cookie createTokenCookie(User user);

    Cookie createInvalidCookie();

    String getAccountFromCookie(Cookie cookie);
}
