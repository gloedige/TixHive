package de.iav.frontend.model;

import java.time.LocalDateTime;

public record Ticket(
        String id,
        String subject,
        String priority,
        String status,
        String text,
        String creatorId,
        LocalDateTime creationDate
) {

}
