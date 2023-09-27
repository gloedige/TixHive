package de.iav.frontend.model;

public record TicketToBeUpdated(
        String id,
        String subject,
        TicketPriority priority,
        TicketStatus status,
        String text
) {
}
