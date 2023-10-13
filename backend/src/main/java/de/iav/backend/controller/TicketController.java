package de.iav.backend.controller;

import de.iav.backend.dto.TicketRequestDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tixhive/tickets")
public class TicketController {
    private final TicketService ticketService;
    @PostMapping
    public Ticket addTicket(@RequestBody TicketRequestDTO ticketInfo){
        return ticketService.addTicket(ticketInfo);
    }

    @GetMapping
    public List<Ticket> listAllTicket() {
        return ticketService.listAllTicket();
    }

    @PutMapping("/{id}")
    public Ticket updateTicketById(@PathVariable String id, @RequestBody TicketRequestDTO ticketToUpdate) {
        return ticketService.updateTicketById(id, ticketToUpdate);
    }

    @PutMapping("/status/{id}")
    public Ticket updateTicketStatusById(@PathVariable String id, @RequestBody TicketRequestDTO ticketToUpdate) {
        return ticketService.updateTicketStatusById(id, ticketToUpdate);
    }

    @DeleteMapping("/{id}")
    public void deleteTicketById(@PathVariable String id) {
        ticketService.deleteTicketById(id);
    }
}
