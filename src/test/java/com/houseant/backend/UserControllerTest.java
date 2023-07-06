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

    @Test
    public void testRegisterSuccessed() {
        when(userService.isAccountAvailable("test_account")).thenReturn(true);
         Map<String, Object> params = new HashMap<>();
        params.put("account", "test_account");
        params.put("password", "test_passwordWrong");

        User user = new User();

        user.setAccount("test_account");
        user.setPassword("test_password");
        user.setTel("test_tel");
        user.setUsername("test_username");
        user.setStatus(false);

        ResponseEntity<Map<String, Object>> responseEntity = (ResponseEntity<Map<String, Object>>) userController.register(user);
        Map<String, Object> response = responseEntity.getBody();
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(true,response.get("success"));
    }

    @Test
    public void testRegisterFailed() {
        when(userService.isAccountAvailable("test_account")).thenReturn(false);
         Map<String, Object> params = new HashMap<>();
        params.put("account", "test_account");
        params.put("password", "test_passwordWrong");

        User user = new User();

        user.setAccount("test_account");
        user.setPassword("test_password");
        user.setTel("test_tel");
        user.setUsername("test_username");
        user.setStatus(false);

        ResponseEntity<Map<String, Object>> responseEntity = (ResponseEntity<Map<String, Object>>) userController.register(user);
        Map<String, Object> response = responseEntity.getBody();
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(false,response.get("success"));
    }

    @Test
    void testUpdateUserWithPassword() {
        User mockUser = new User();
        mockUser.setAccount("test_account");
        mockUser.setUsername("test_username");
        mockUser.setPassword("test_password");
        mockUser.setTel("test_tel");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.getSession().setAttribute("user", mockUser);

        User updateUser = new User();
        updateUser.setUsername("updated_username");
        updateUser.setPassword("updated_password");
        updateUser.setTel("updated_tel");

        ResponseEntity<?> responseEntity = userController.updateUser(updateUser, mockRequest);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        // 验证更新后的信息是否正确
        Map<String, Object> response = (Map<String, Object>) responseEntity.getBody();
        assertEquals("Successfully update userIfo and logout", response.get("message"));
    }

    @Test
    void testUpdateUser() {
        User mockUser = new User();
        mockUser.setAccount("test_account");
        mockUser.setUsername("test_username");
        mockUser.setTel("test_tel");

        MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.getSession().setAttribute("user", mockUser);

        User updateUser = new User();
        updateUser.setUsername("updated_username");
        updateUser.setTel("updated_tel");

        ResponseEntity<?> responseEntity = userController.updateUser(updateUser, mockRequest);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        // 验证更新后的信息是否正确
        Map<String, Object> response = (Map<String, Object>) responseEntity.getBody();
        assertEquals("Successfully update userIfo", response.get("message"));
    }

    @Test
    void testUserInfo() {
        User mockUser = new User();
        mockUser.setAccount("test_account");
        mockUser.setUsername("test_username");
        mockUser.setPassword("test_password");
        mockUser.setTel("test_tel");

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("user", mockUser);

        when(userService.findByAccount(mockUser.getAccount())).thenReturn(mockUser);

        ResponseEntity<?> responseEntity = userController.userInfo(request);
        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        Map<String, Object> body = (Map<String, Object>) responseEntity.getBody();
        assertNotNull(body);

        assertEquals("searchUser successfully", body.get("message"));
        assertEquals(mockUser, body.get("result"));
    }

}