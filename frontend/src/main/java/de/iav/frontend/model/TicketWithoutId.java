package de.iav.frontend.model;

public record TicketWithoutId (
        String subject,
        String priority,
        String status,
        String text,
        String creatorId
){

}
