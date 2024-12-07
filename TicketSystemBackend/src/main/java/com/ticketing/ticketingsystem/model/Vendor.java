package com.ticketing.ticketingsystem.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vendorId;

    private String name;

    @OneToMany(mappedBy = "vendor", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    // Default constructor
    public Vendor() {}

    // Parameterized constructor
    public Vendor(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}

