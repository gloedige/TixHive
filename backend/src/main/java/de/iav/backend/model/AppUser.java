package de.iav.backend.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
public record AppUser(
    @MongoId
    String appUserId,
    String email,
    String password,
    AppUserRole role,
    @DBRef
    List<Ticket> tickets
) {
}
