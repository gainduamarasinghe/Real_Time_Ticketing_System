package com.ticketing.ticketingsystem.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String name;
    private int customerRetrievalRate;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Ticket> purchasedTickets;

    // Default constructor
    public Customer() {}

    // Parameterized constructor
    public Customer(String name, int customerRetrievalRate) {
        this.name = name;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    // Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }

    public List<Ticket> getPurchasedTickets() {
        return purchasedTickets;
    }

    public void setPurchasedTickets(List<Ticket> purchasedTickets) {
        this.purchasedTickets = purchasedTickets;
    }
}
