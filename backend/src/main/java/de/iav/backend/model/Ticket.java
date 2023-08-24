package de.iav.backend.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public record Ticket(
        @Id
        String ticketId,
        String subject,
        String priority,
        String status,
        String text,
        LocalDateTime creationDate,
        String user
) {
}
