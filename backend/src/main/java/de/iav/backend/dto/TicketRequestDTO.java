package de.iav.backend.dto;

import de.iav.backend.model.TicketPriority;
import de.iav.backend.model.TicketStatus;

public record TicketRequestDTO(
        String subject,
        TicketPriority priority,
        TicketStatus status,
        String text,
        String creatorId) {
}