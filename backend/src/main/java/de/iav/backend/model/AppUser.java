package de.iav.backend.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
public record AppUser(
    @MongoId
    String ticketAppUserId,
    String firstname,
    String lastname,
    @DBRef
    List<Ticket> tickets
) {
}
