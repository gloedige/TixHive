package de.iav.frontend.model;

public record TicketWithoutId (
        String subject,
        TicketPriority priority,
        TicketStatus status,
        String text,
        String creatorId
){

}
