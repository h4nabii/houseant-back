package com.houseant.backend;

import com.houseant.backend.controller.HomePageController;
import com.houseant.backend.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HomePageControllerTest {

    @InjectMocks
    private HomePageController homePageController;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setAccount("TestAccount");
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testHomeWithLoggedInUser() {
        when(session.getAttribute("user")).thenReturn(user);
        ResponseEntity<?> responseEntity = homePageController.home(request);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        assertTrue((Boolean) responseBody.get("auto-login"));
        assertEquals("TestAccount", responseBody.get("account"));
    }

    @Test
    void testHomeWithNoLoggedInUser() {
        when(session.getAttribute("user")).thenReturn(null);
        ResponseEntity<?> responseEntity = homePageController.home(request);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());

        Map<String, Object> responseBody = (Map<String, Object>) responseEntity.getBody();

        assertNull(responseBody.get("auto-login"));
        assertNull(responseBody.get("account"));
    }
}