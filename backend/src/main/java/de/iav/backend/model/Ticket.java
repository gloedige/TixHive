package de.iav.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
@Document(collation = "tickets")
public record Ticket(
        @MongoId
        String ticketId,
        String subject,
        String priority,
        String status,
        String text,
        LocalDateTime creationDate
) {
}
