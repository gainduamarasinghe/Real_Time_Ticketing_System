package com.ticketing.ticketingsystem.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketing.ticketingsystem.config.Configuration;
import com.ticketing.ticketingsystem.model.Customer;
import com.ticketing.ticketingsystem.model.Vendor;
import com.ticketing.ticketingsystem.ticketpool.TicketPool;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class ConfigurationService {

    private TicketPool ticketPool;
    private ThreadPoolExecutor vendorExecutor;
    private ThreadPoolExecutor customerExecutor;
    private Configuration currentConfig;
    private boolean isRunning = false;

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
            vendorExecutor.shutdownNow();
            customerExecutor.shutdownNow();

            if (!vendorExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Vendor threads did not terminate.");
            }

            if (!customerExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                System.err.println("Customer threads did not terminate.");
            }

            System.out.println("Simulation stopped successfully.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Simulation termination interrupted.");
        } finally {
            isRunning = false;
        }
    }


}
