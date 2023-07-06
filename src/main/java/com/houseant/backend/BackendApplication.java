package com.houseant.backend;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;


    public BackendApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) {
        Logger logger = LogManager.getLogger("database connection test");
        try {
            jdbcTemplate.queryForObject("SELECT 1", Integer.class);
            logger.info("Database connection test is successful.");
        } catch (Exception e) {
            logger.error("Database connection test failed.", e);
        }
    }

}
