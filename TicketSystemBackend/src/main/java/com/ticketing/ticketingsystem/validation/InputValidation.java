package com.ticketing.ticketingsystem.validation;

import com.ticketing.ticketingsystem.config.Configuration;

public class InputValidation {

    public static void validateConfiguration(Configuration configuration) {
        if (configuration.getTotalTickets() <= 0) {
            throw new IllegalArgumentException("Total tickets must be a positive integer!");
        }

        if (configuration.getTicketReleaseRate() <= 0) {
            throw new IllegalArgumentException("Ticket release rate must be a positive integer!");
        }

        if (configuration.getTicketReleaseRate() > configuration.getTotalTickets()) {
            throw new IllegalArgumentException("The ticket release rate cannot exceed the total available tickets!");
        }

        if (configuration.getCustomerRetrievalRate() <= 0) {
            throw new IllegalArgumentException("Customer retrieval rate must be a positive integer!");
        }

        if (configuration.getCustomerRetrievalRate() > configuration.getTicketReleaseRate()) {
            throw new IllegalArgumentException("The customer retrieval rate cannot exceed the ticket release rate!");
        }

        if (configuration.getMaxTicketCapacity() <= 0) {
            throw new IllegalArgumentException("Maximum ticket capacity must be a positive integer!");
        }

        if (configuration.getMaxTicketCapacity() > configuration.getTotalTickets()
                || configuration.getMaxTicketCapacity() < configuration.getTicketReleaseRate()) {
            throw new IllegalArgumentException(
                    "The maximum ticket capacity cannot exceed the total tickets and must be greater than or equal to the ticket release rate!");
        }
    }
}
