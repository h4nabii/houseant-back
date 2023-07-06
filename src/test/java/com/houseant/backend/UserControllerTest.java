package com.houseant.backend;

import com.houseant.backend.controller.UserController;
import com.houseant.backend.entity.User;
import com.houseant.backend.service.TokenService;
import com.houseant.backend.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    TokenService tokenService;

    @Mock
    Logger logger;

    HttpServletRequest request;
    HttpServletResponse response;

    @BeforeEach
    public void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    public void testLoginSucceed() {
        Map<String, Object> params = new HashMap<>();
        params.put("account", "test_account");
        params.put("password", "test_password");

        User user = new User();

        user.setAccount("test_account");
        user.setPassword("test_password");
        user.setTel("test_tel");
        user.setUsername("test_username");
        user.setStatus(false);

        when(userService.findByAccount(any())).thenReturn(user);
        Cookie mockCookie = new Cookie("test", "test");
        when(tokenService.createTokenCookie(any(User.class))).thenReturn(mockCookie);
        ResponseEntity<Map<String, Object>> responseEntity = (ResponseEntity<Map<String, Object>>) userController
                .login(params, response, request);

        Map<String, Object> response = responseEntity.getBody();

        assertEquals(response.get("login"), true);
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testLoginFailed() {
        Map<String, Object> params = new HashMap<>();
        params.put("account", "test_account");
        params.put("password", "test_passwordWrong");

        User user = new User();

        user.setAccount("test_account");
        user.setPassword("test_password");
        user.setTel("test_tel");
        user.setUsername("test_username");
        user.setStatus(false);

        when(userService.findByAccount(any())).thenReturn(user);
        ResponseEntity<Map<String, Object>> responseEntity = (ResponseEntity<Map<String, Object>>) userController
                .login(params, response, request);
        Map<String, Object> response = responseEntity.getBody();

        assertEquals(response.get("login"), false);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

    @Test
    public void testLogout() {
        Cookie mockCookie = new Cookie("test", "test");
        when(tokenService.createInvalidCookie()).thenReturn(mockCookie);
        ResponseEntity<?> responseEntity = userController.logout(response, request);
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        verify(tokenService, times(1)).createInvalidCookie();
    }
    
    // Similarly, you can add more test methods for logout, register, updateUser,
    // userInfo

}