package de.iav.backend.dto;

import de.iav.backend.model.TicketPriority;
import de.iav.backend.model.TicketStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TicketRequestDTO {
    private String subject;
    private TicketPriority priority;
    private TicketStatus status;
    private String text;
    private String creatorId;
}