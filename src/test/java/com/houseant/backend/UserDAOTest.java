package com.houseant.backend;

import com.houseant.backend.dao.UserDAO;
import com.houseant.backend.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootTest
@Transactional
@Rollback
public class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testFindAll() {
        Logger logger = Logger.getLogger("test");
        List<User> users = userDAO.findAll();
        logger.log(Level.INFO, users.toString());
        Assertions.assertNotNull(users);
    }

    @Test
    public void testInsertAndFindByAccout() {
        Logger logger = Logger.getLogger("test");
        User TestUser = new User(100, "test", "test", "test", "test", true, "test");
        userDAO.insert(TestUser);
        User QueryUser = userDAO.findByAccount("test");
        logger.log(Level.INFO, QueryUser.toString());
        Assertions.assertEquals(QueryUser.getAccount(), "test");
    }
/*
    @Test
    public void testDelete() {
        Logger logger = Logger.getLogger("test");
        User TestUser = new User(100, "test", "test", "test", "test", true, "test");
        userDAO.insert(TestUser);
        User QueryUser = userDAO.findByAccount("test");
        logger.info(QueryUser.toString());
        userDAO.delete(QueryUser.getUserId());
        QueryUser = userDAO.findByAccount("test");
        Assertions.assertEquals(null, QueryUser);
    }

    @Test
    public void testUpdate() {
        User TestUser = new User(100, "test", "test", "test", "test", true, "test");
        userDAO.insert(TestUser);
        User QueryUser = userDAO.findByAccount("test");
        QueryUser.setUsername("null");
        userDAO.update(QueryUser);
        QueryUser = userDAO.findByAccount("test");
        Assertions.assertEquals("null", QueryUser.getUsername());
    }
*/
}
