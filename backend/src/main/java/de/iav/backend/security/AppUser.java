package de.iav.backend.security;

import de.iav.backend.model.Ticket;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "users")
public record AppUser(
        String id,
        @Indexed(unique = true)
        String username,
        @Indexed(unique = true)
        String email,
        String password,
        AppUserRole role,
        List<Ticket> tickets
) {
}
