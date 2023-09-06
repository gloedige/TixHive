package de.iav.backend.security;

public record AppUserResponse(
        String id,
        String email,
        AppUserRole role
) {
}
