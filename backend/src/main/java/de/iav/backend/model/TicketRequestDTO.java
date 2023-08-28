package de.iav.backend.model;

import lombok.Getter;

@Getter
public class TicketRequestDTO{
        private String subject;
        private String priority;
        private String status;
        private String text;

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setText(String text) {
        this.text = text;
    }
}
