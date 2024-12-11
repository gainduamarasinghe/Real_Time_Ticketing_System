package com.ticketing.ticketingsystem.model;

import java.math.BigDecimal;

/**
 * Represents a ticket in the ticketing system.
 * Each ticket is associated with an event and contains information about its price, location, and ID.
 */
public class Ticket {
    private int ticketID;
    private String eventName;
    private BigDecimal ticketPrice;
    private String location;

    /**
     * Constructs a Ticket object with the specified details.
     *
     * @param ticketID    the unique identifier for the ticket.
     * @param eventName   the name of the event associated with the ticket.
     * @param ticketPrice the price of the ticket.
     * @param location    the location of the event.
     */
    public Ticket(int ticketID, String eventName, BigDecimal ticketPrice, String location) {
        this.ticketID = ticketID;
        this.eventName = eventName;
        this.ticketPrice = ticketPrice;
        this.location = location;
    }

    /**
     * Gets the unique ID of the ticket.
     *
     * @return the ticket ID.
     */
    public int getTicketID() {
        return ticketID;
    }

    /**
     * Sets the unique ID of the ticket.
     *
     * @param ticketID the ticket ID.
     */
    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    /**
     * Gets the name of the event associated with the ticket.
     *
     * @return the event name.
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * Sets the name of the event associated with the ticket.
     *
     * @param eventName the event name.
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Gets the price of the ticket.
     *
     * @return the ticket price.
     */
    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    /**
     * Sets the price of the ticket.
     *
     * @param ticketPrice the ticket price.
     */
    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    /**
     * Gets the location of the event associated with the ticket.
     *
     * @return the event location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location of the event associated with the ticket.
     *
     * @param location the event location.
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Returns a string representation of the ticket, including its ID, event name, price, and location.
     *
     * @return a string representation of the ticket.
     */
    @Override
    public String toString() {
        return "Ticket is {" +
                "ticketID=" + ticketID +
                ", eventName='" + eventName + '\'' +
                ", ticketPrice=" + ticketPrice +
                ", location='" + location + '\'' +
                '}';
    }
}
