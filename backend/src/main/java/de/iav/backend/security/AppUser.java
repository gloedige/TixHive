package de.iav.backend.security;

import de.iav.backend.model.Ticket;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
public record AppUser(
    @MongoId
    String appUserId,
    String email,
    String password,
    @Indexed(unique = true)
    AppUserRole role,
    @DBRef
    List<Ticket> tickets
) {
}
