package de.iav.backend.exceptions;

public class CustomUserNotFoundException extends RuntimeException {
    public CustomUserNotFoundException(String ticketId) {
        super("No user found with ticketId: " + ticketId);
    }
}
