package de.iav.backend.security;

public record AppUserRequest(
        String email,
        String password
) {
}
