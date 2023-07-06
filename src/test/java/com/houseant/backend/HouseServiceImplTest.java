package com.houseant.backend;

import com.houseant.backend.dao.HouseInfoDAO;
import com.houseant.backend.entity.House;
import com.houseant.backend.service.impl.HouseServiceImpl;

import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class HouseServiceImplTest {

    @Mock
    private HouseInfoDAO houseInfoDAO;

    private HouseServiceImpl houseService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // 初始化 Mockito 注解
        houseService = new HouseServiceImpl(houseInfoDAO);
    }

    @Test
    public void testInsert() {

        House house = new House(0, "123", "zx", "111", 0, "111", "111", 0, "111", "111", "111", "111", false);
        doNothing().when(houseInfoDAO).insert(house); // 模拟 houseInfoDAO.insert(house) 方法不执行任何操作
        // 执行被测试的方法
        houseService.insert(house);
        // 验证模拟对象方法是否被调用
        verify(houseInfoDAO, times(1)).insert(house); // 验证 houseInfoDAO.insert(house) 方法被调用一次

    }

    @Test
    public void testFindAll() {
        List<House> expectedHouses = Collections
                .singletonList(new House(0, "zc", "zxc", "111", 0, "111", "111", 0, "111", "111", "111", "111", false));
        when(houseInfoDAO.findAll()).thenReturn(expectedHouses);
        List<House> actualHouses = houseService.findAll();
        assertEquals(expectedHouses, actualHouses);
        verify(houseInfoDAO, times(1)).findAll(); // 验证 houseInfoDAO.findAll() 方法被调用一次
    }

    @Test
    public void testFindByKey() {
        Map<String, Object> params = Collections.singletonMap("account", "zc");
        List<House> expectedHouses = Collections
                .singletonList(new House(0, "zc", "zxc", "111", 0, "111", "111", 0, "111", "111", "111", "111", false));
        when(houseInfoDAO.findByKey(params)).thenReturn(expectedHouses);

        List<House> actualHouses = houseService.findByKey(params);

        assertEquals(expectedHouses, actualHouses);
        verify(houseInfoDAO, times(1)).findByKey(params); 
        houseInfoDAO.findByKey(params);
    }

    // 添加其他测试方法...
}
