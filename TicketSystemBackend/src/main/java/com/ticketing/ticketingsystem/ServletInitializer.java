package com.ticketing.ticketingsystem;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Servlet initializer for deploying the Spring Boot application in a traditional servlet container.
 * Configures the application to launch with the specified source class.
 */
public class ServletInitializer extends SpringBootServletInitializer {

    /**
     * Configures the application builder with the primary source class.
     *
     * @param application the SpringApplicationBuilder instance to configure.
     * @return the configured SpringApplicationBuilder.
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(TicketingSystemApplication.class);
    }
}
