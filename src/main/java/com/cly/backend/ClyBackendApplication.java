package com.cly.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.oas.annotations.EnableOpenApi;

@SpringBootApplication
public class ClyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClyBackendApplication.class, args);
    }

}
