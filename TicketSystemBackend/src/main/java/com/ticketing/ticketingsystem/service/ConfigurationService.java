package com.ticketing.ticketingsystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketing.ticketingsystem.config.Configuration;
import com.ticketing.ticketingsystem.model.Customer;
import com.ticketing.ticketingsystem.model.Vendor;
import com.ticketing.ticketingsystem.ticketpool.TicketPool;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class ConfigurationService {

    private final SimpMessagingTemplate messagingTemplate;
    private TicketPool ticketPool;
    private ThreadPoolExecutor vendorExecutor;
    private ThreadPoolExecutor customerExecutor;
    private Configuration currentConfig;
    private boolean isRunning = false;

    public ConfigurationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void saveConfiguration(Configuration configuration) {
        this.currentConfig = configuration;

        // Initialize TicketPool with maximum ticket capacity and total tickets
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

    public void startSimulation() {
        if (isRunning) {
            throw new IllegalStateException("Simulation already running");
        }

        if (ticketPool == null || currentConfig == null) {
            throw new IllegalStateException("Configuration not initialized. Save configuration first.");
        }

        isRunning = true;

        // Start vendors
        int numberOfVendors = 10;
        vendorExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfVendors);

        for (int i = 0; i < numberOfVendors; i++) {

            Vendor vendor = new Vendor(currentConfig.getTotalTickets() / numberOfVendors, currentConfig.getTicketReleaseRate(), ticketPool);
            vendorExecutor.submit(vendor); // Submit tasks to the thread pool
        }

        // Start customers
        int numberOfCustomers = 5;
        customerExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfCustomers);

        for (int i = 0; i < numberOfCustomers; i++) {
            Customer customer = new Customer(ticketPool, currentConfig.getCustomerRetrievalRate(), currentConfig.getTotalTickets() / numberOfCustomers);
            customerExecutor.submit(customer); // Submit tasks to the thread pool
        }

        // Monitor the system to shut down when all tickets are sold
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (ticketPool.shouldStop()) {
                    System.out.println("All tickets have been sold. Terminating the system...");
                    stopSimulation();
                    messagingTemplate.convertAndSend("/topic/simulationStatus", "All tickets sold. Simulation stopped.");
                    break;
                }
                try {
                    Thread.sleep(1000); // Check periodically
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }

    public void stopSimulation() {
        if (!isRunning) {
            System.out.println("Simulation is not running.");
            return;
        }

        try {
            if (vendorExecutor != null) {
                vendorExecutor.shutdownNow(); // Attempt to stop vendor threads
            }
            if (customerExecutor != null) {
                customerExecutor.shutdownNow(); // Attempt to stop customer threads
            }

            if (vendorExecutor != null) {
                vendorExecutor.awaitTermination(5, TimeUnit.SECONDS);
            }
            if (customerExecutor != null) {
                customerExecutor.awaitTermination(5, TimeUnit.SECONDS);
            }

            System.out.println("All threads have been stopped.");
            messagingTemplate.convertAndSend("/topic/simulationStatus", "Simulation manually stopped.");
        } catch (InterruptedException e) {
            System.err.println("Error stopping threads: " + e.getMessage());
        } finally {
            isRunning = false;
        }
    }
}
