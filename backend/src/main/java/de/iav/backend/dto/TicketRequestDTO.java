package de.iav.backend.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class TicketRequestDTO {
    private String subject;
    private String priority;
    private String status;
    private String text;
    private String creatorId;
}