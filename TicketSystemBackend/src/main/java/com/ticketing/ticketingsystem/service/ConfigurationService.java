package com.ticketing.ticketingsystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketing.ticketingsystem.config.Configuration;
import com.ticketing.ticketingsystem.model.Customer;
import com.ticketing.ticketingsystem.model.Vendor;
import com.ticketing.ticketingsystem.ticketpool.TicketPool;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ConfigurationService {

    private TicketPool ticketPool;
    private ExecutorService vendorExecutor;
    private ExecutorService customerExecutor;
    private Configuration currentConfig;
    private boolean isRunning = false;

    /**
     * Saves the provided configuration to a JSON file and initializes the TicketPool.
     */
    public void saveConfiguration(Configuration configuration) {
        this.currentConfig = configuration;

        // Initialize TicketPool with the maximum ticket capacity and total tickets to sell
        this.ticketPool = new TicketPool(
                configuration.getMaxTicketCapacity(),
                configuration.getTotalTickets()
        );

        // Save configuration to a JSON file
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File("configuration.json"), configuration);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save configuration", e);
        }
    }

    /**
     * Starts the ticketing simulation by initializing vendor and customer threads.
     */
    public void startSimulation() {
        if (isRunning) {
            throw new IllegalStateException("Simulation already running");
        }

        if (ticketPool == null || currentConfig == null) {
            throw new IllegalStateException("Configuration not initialized. Save configuration first.");
        }

        isRunning = true;

        // Start vendors
        vendorExecutor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            vendorExecutor.submit(() -> {
                while (!Thread.currentThread().isInterrupted() && !ticketPool.allTicketsReleased()) {
                    ticketPool.addTicket();
                }
            });
        }

        // Start customers
        customerExecutor = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 5; i++) {
            customerExecutor.submit(() -> {
                while (!Thread.currentThread().isInterrupted() && !ticketPool.allTicketsSold()) {
                    ticketPool.buyTicket();
                }
            });
        }
    }

    /**
     * Stops the simulation by shutting down all threads.
     */
    public void stopSimulation() {
        if (!isRunning) {
            throw new IllegalStateException("Simulation is not running");
        }

        // Shut down vendor and customer executors
        vendorExecutor.shutdownNow();
        customerExecutor.shutdownNow();
        isRunning = false;
    }
}
