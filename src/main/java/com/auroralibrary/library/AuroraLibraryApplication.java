package com.auroralibrary.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableRetry
@EnableAsync
@EnableSpringDataWebSupport
@SpringBootApplication
public class AuroraLibraryApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuroraLibraryApplication.class, args);
    }
} 