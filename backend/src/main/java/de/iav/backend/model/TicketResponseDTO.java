package de.iav.backend.model;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TicketResponseDTO {
    private String ticketId;
    private String subject;
    private String priority;
    private String status;
    private String text;
    private LocalDateTime creationDate;

    public TicketResponseDTO(String id, String subject, String priority, String status, String text, LocalDateTime localDateTime) {
    }
    
}
