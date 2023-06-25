package com.example.backend;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Logger logger = Logger.getLogger("test");
		try {
			jdbcTemplate.queryForObject("SELECT 1", Integer.class);
			logger.log(Level.INFO, "Database connection test is successful.");
		} catch (Exception e) {
			logger.log(Level.INFO, "Database connection test failed.");
			e.printStackTrace();
		}
	}

}
