package com.example.backend.DatabaseConnectionTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DatabaseConnectionTest {

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
        // 这个测试方法实际上什么都不做，它的目的只是为了确保 Spring 应用上下文能够成功启动
    }
}
