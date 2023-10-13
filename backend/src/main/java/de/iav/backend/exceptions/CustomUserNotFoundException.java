package de.iav.backend.exceptions;

public class CustomUserNotFoundException extends RuntimeException {
    public CustomUserNotFoundException(String s) {
        super(s);
    }
}
