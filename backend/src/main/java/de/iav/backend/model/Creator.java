package de.iav.backend.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;
public record Creator(
    @MongoId
    String ticketCreatorId,
    String firstname,
    String lastname,
    @DBRef
    List<Ticket> tickets
) {
}
