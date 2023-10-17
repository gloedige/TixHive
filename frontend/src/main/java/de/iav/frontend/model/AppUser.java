package de.iav.frontend.model;
import java.util.List;

public record AppUser(
        String id,
        String username,
        String email,
        String password,
        AppUserRole role,
        List<Ticket> tickets
) {
}
