package com.ticketing.ticketingsystem.ticketpool;

import com.ticketing.ticketingsystem.model.Ticket;
import com.ticketing.ticketingsystem.utils.Logger;
import com.ticketing.ticketingsystem.websocket.ActivityWebSocketHandler;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Manages the pool of tickets in the ticketing system. Handles ticket addition and purchase, and ensures thread safety.
 */
public class TicketPool {
    private final int maximumTicketCapacity;
    private final int totalTicketsToSell;
    private final ActivityWebSocketHandler webSocketController; // Injected WebSocketController
    private Queue<Ticket> ticketsQueue;
    private int ticketCounter = 1;
    private int totalTicketsReleased = 0;
    private int totalTicketsSold = 0;

    // Separate ID maps and counters for vendors and customers
    private final ConcurrentHashMap<Thread, Integer> vendorIdMap = new ConcurrentHashMap<>();
    private final AtomicInteger vendorIdCounter = new AtomicInteger(1);

    private final ConcurrentHashMap<Thread, Integer> customerIdMap = new ConcurrentHashMap<>();
    private final AtomicInteger customerIdCounter = new AtomicInteger(1);

    /**
     * Constructs a TicketPool instance with the specified maximum capacity, ticket count, and WebSocket handler.
     *
     * @param maximumTicketCapacity the maximum number of tickets the pool can hold.
     * @param totalTicketsToSell    the total number of tickets available for sale.
     * @param webSocketController   the WebSocket handler for broadcasting updates.
     */
    public TicketPool(int maximumTicketCapacity, int totalTicketsToSell, ActivityWebSocketHandler webSocketController) {
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.totalTicketsToSell = totalTicketsToSell;
        this.ticketsQueue = new LinkedList<>();
        this.webSocketController = webSocketController; // Set WebSocketController
    }

    /**
     * Gets or assigns a unique ID for the current vendor thread.
     *
     * @return the vendor ID for the current thread.
     */
    private int getVendorId() {
        return vendorIdMap.computeIfAbsent(Thread.currentThread(), t -> vendorIdCounter.getAndIncrement());
    }

    /**
     * Gets or assigns a unique ID for the current customer thread.
     *
     * @return the customer ID for the current thread.
     */
    private int getCustomerId() {
        return customerIdMap.computeIfAbsent(Thread.currentThread(), t -> customerIdCounter.getAndIncrement());
    }

    /**
     * Adds a ticket to the pool. Waits if the pool is full or all tickets have been released.
     */
    public synchronized void addTicket() {
        while (ticketsQueue.size() >= maximumTicketCapacity || totalTicketsReleased >= totalTicketsToSell) {
            if (totalTicketsReleased >= totalTicketsToSell) {
                return; // No more tickets to release
            }
            try {
                System.out.println("Waiting to add a ticket...");
                webSocketController.broadcastMessage("Waiting to add a ticket...");
                wait(); // Wait for space in the queue or more tickets to be sold
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupt flag
                System.out.println("Ticket addition interrupted.");
                webSocketController.broadcastMessage("Ticket addition interrupted.");
                return; // Exit if interrupted
            }
        }
        Ticket ticket = new Ticket(ticketCounter++, "Simple Event", new BigDecimal("1000"), "Maharagama");
        ticketsQueue.add(ticket);
        totalTicketsReleased++;
        notifyAll(); // Notify waiting customers and vendors

        int vendorId = getVendorId();
        String logMessage = "Ticket added by Vendor-" + vendorId + " - current size is " + ticketsQueue.size();
        System.out.println(logMessage);
        Logger.log(logMessage);

        // Send log to frontend via WebSocket
        webSocketController.broadcastMessage(logMessage);
    }

    /**
     * Allows a customer to purchase a ticket. Waits if no tickets are available.
     *
     * @return the purchased Ticket, or null if no ticket is available.
     */
    public synchronized Ticket buyTicket() {
        while (ticketsQueue.isEmpty() && totalTicketsSold < totalTicketsToSell) {
            try {
                System.out.println("Customer waiting to buy...");
                webSocketController.broadcastMessage("Customer waiting to buy...");
                wait(); // Wait for a ticket to be available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore the interrupt flag
                System.out.println("Ticket purchase interrupted.");
                webSocketController.broadcastMessage("Ticket purchase interrupted.");
                return null; // Exit if interrupted
            }
        }
        if (!ticketsQueue.isEmpty()) {
            Ticket ticket = ticketsQueue.poll();
            totalTicketsSold++;
            notifyAll(); // Notify vendor threads

            int customerId = getCustomerId();
            String logMessage = "Ticket bought by Customer-" + customerId + " - current size is " + ticketsQueue.size() + " - " + ticket;
            System.out.println(logMessage);
            Logger.log(logMessage);
            webSocketController.broadcastMessage(logMessage);

            // Send ticket counter update to frontend
            String counterUpdate = totalTicketsSold + "/" + totalTicketsToSell;
            webSocketController.broadcastMessage("Sold Ticket count: " + counterUpdate);

            return ticket;
        }
        return null; // No ticket available, or stop condition met
    }

    /**
     * Checks if the simulation should stop. This happens when all tickets are released and sold.
     *
     * @return true if the simulation should stop, false otherwise.
     */
    public synchronized boolean shouldStop() {
        return allTicketsReleased() && allTicketsSold();
    }

    /**
     * Checks if all tickets have been released.
     *
     * @return true if all tickets are released, false otherwise.
     */
    public synchronized boolean allTicketsReleased() {
        return totalTicketsReleased >= totalTicketsToSell;
    }

    /**
     * Checks if all tickets have been sold.
     *
     * @return true if all tickets are sold, false otherwise.
     */
    public synchronized boolean allTicketsSold() {
        return totalTicketsSold >= totalTicketsToSell;
    }

    /**
     * Gets the vendor ID for the current thread.
     *
     * @return the vendor ID.
     */
    public int getVendorIdForThread() {
        return getVendorId(); // Call the private method to get the vendor ID
    }

    /**
     * Gets the customer ID for the current thread.
     *
     * @return the customer ID.
     */
    public int getCustomerIdForThread() {
        return getCustomerId(); // Call the private method to get the customer ID
    }
}
