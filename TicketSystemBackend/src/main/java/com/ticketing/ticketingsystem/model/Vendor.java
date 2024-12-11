package com.ticketing.ticketingsystem.model;

import com.ticketing.ticketingsystem.ticketpool.TicketPool;

/**
 * Represents a vendor in the ticketing system who is responsible for releasing tickets.
 * The vendor runs in a separate thread and periodically adds tickets to the ticket pool
 * based on the configured release rate.
 */
public class Vendor implements Runnable {

    private int totalTickets;
    private int ticketReleaseRate;
    private TicketPool ticketPool;

    /**
     * Constructs a Vendor instance with the specified parameters.
     *
     * @param totalTickets      the total number of tickets the vendor manages.
     * @param ticketReleaseRate the rate (in seconds) at which the vendor releases tickets.
     * @param ticketPool        the TicketPool to which tickets will be added.
     */
    public Vendor(int totalTickets, int ticketReleaseRate, TicketPool ticketPool) {
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
    }

    /**
     * Runs the ticket release logic for the vendor in a separate thread.
     * The vendor continues to add tickets until interrupted or until the ticket pool
     * indicates the stop condition.
     */
    @Override
    public void run() {
        int vendorId = ticketPool.getVendorIdForThread(); // Get the vendor ID for the current thread
        while (!Thread.currentThread().isInterrupted() && !ticketPool.shouldStop()) {
            ticketPool.addTicket(); // Add a ticket to the pool
            try {
                // Wait for the configured release rate before adding the next ticket
                Thread.sleep(ticketReleaseRate * 1000);
            } catch (InterruptedException e) {
                System.out.println("Vendor-" + vendorId + " interrupted while releasing tickets.");
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("Vendor-" + vendorId + " stopped.");
    }
}
