package com.example.campuscopapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "edu.famu.cop3060.resources")
public class CampusCopApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CampusCopApiApplication.class, args);
    }
}
