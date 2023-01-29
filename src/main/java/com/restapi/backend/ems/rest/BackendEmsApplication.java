package com.restapi.backend.ems.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication()
public class BackendEmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendEmsApplication.class, args);
    }

}
