package org.example.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Starts the separate Library Management REST API. */
@SpringBootApplication
public class LibraryManagementApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApiApplication.class, args);
    }
}
