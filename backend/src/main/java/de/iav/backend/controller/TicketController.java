package de.iav.backend.controller;

import de.iav.backend.dto.TicketRequestDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tixhive/ticket")
public class TicketController {
    private final TicketService ticketService;
    @PostMapping
    public Ticket addTicket(@RequestBody TicketRequestDTO ticketInfo){
        return ticketService.addTicket(ticketInfo);
    }
}
