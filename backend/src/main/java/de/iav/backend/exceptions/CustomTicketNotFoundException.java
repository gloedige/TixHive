package de.iav.backend.exceptions;

public class CustomTicketNotFoundException extends RuntimeException {

    public CustomTicketNotFoundException(String ticketId) {
        super("Element with " + ticketId + " not found!");
    }
}
