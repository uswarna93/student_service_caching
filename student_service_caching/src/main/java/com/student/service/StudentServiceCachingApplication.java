package com.student.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StudentServiceCachingApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentServiceCachingApplication.class, args);
    }

}
