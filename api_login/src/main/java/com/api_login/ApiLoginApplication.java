package com.api_login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiLoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiLoginApplication.class, args);
    }
}
