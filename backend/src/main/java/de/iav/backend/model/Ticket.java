package de.iav.backend.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record Ticket(
        @Id
        String id,
        String subject,
        TicketPriority priority,
        TicketStatus status,
        String text,
        String creatorId,
        LocalDateTime creationDate
) {
}
