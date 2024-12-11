package com.ticketing.ticketingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Ticketing System application.
 * This class contains the main method to launch the Spring Boot application.
 */
@SpringBootApplication
public class TicketingSystemApplication {

    /**
     * Main method for starting the Ticketing System application.
     *
     * @param args command-line arguments passed during application startup.
     */
    public static void main(String[] args) {
        SpringApplication.run(TicketingSystemApplication.class, args);
    }
}
