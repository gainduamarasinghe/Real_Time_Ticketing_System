package com.ticketing.ticketingsystem.model;

import com.ticketing.ticketingsystem.ticketpool.TicketPool;

/**
 * Represents a customer in the ticketing system who retrieves tickets.
 * The customer runs in a separate thread and periodically attempts to purchase tickets
 * from the ticket pool based on the configured retrieval rate.
 */
public class Customer implements Runnable {

    private TicketPool ticketPool;
    private int customerRetrievalRate;
    private int quantity;

    /**
     * Constructs a Customer instance with the specified parameters.
     *
     * @param ticketPool           the TicketPool from which tickets will be retrieved.
     * @param customerRetrievalRate the rate (in seconds) at which the customer retrieves tickets.
     * @param quantity             the total number of tickets the customer wants to retrieve.
     */
    public Customer(TicketPool ticketPool, int customerRetrievalRate, int quantity) {
        this.ticketPool = ticketPool;
        this.customerRetrievalRate = customerRetrievalRate;
        this.quantity = quantity;
    }

    /**
     * Runs the ticket retrieval logic for the customer in a separate thread.
     * The customer continues to buy tickets until interrupted or until the ticket pool
     * indicates the stop condition.
     */
    @Override
    public void run() {
        int customerId = ticketPool.getCustomerIdForThread(); // Get the customer ID for the current thread
        while (!Thread.currentThread().isInterrupted() && !ticketPool.shouldStop()) {
            ticketPool.buyTicket(); // Attempt to buy a ticket
            try {
                // Wait for the configured retrieval rate before attempting to retrieve the next ticket
                Thread.sleep(customerRetrievalRate * 1000);
            } catch (InterruptedException e) {
                System.out.println("Customer-" + customerId + " interrupted while retrieving tickets.");
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Customer-" + customerId + " stopped.");
    }
}
