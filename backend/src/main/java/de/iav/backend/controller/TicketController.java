package de.iav.backend.controller;

import de.iav.backend.model.Ticket;
import de.iav.backend.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tixhive")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<Ticket> listAllTicket(){
        return ticketService.listAllTicket();
    }
    @GetMapping("/id")
    public Optional<Ticket> getTicketById(@PathVariable String ticketId){
        return ticketService.getTicketById(ticketId);
    }
    @GetMapping("/priority")
    public List<Ticket> getTicketByPriority (@PathVariable String priority) {
        return ticketService.getTicketByPriority(priority);
    }
    @GetMapping("/status")
    public List<Ticket> getTicketByStatus (@PathVariable String status){
        return ticketService.getTicketByStatus(status);
    }
    @GetMapping("/creationDate")
    public List<Ticket> getTicketByCreationDate(@PathVariable LocalDateTime creationDate){
        return ticketService.getTicketByCreationDate(creationDate);
    }
    @PostMapping
    public Ticket addTicket(@RequestBody Ticket newTicket){
        return ticketService.addTicket(newTicket);
    }
    @PutMapping("/id")
    public Ticket updateTicketById(@PathVariable String ticketId, @RequestBody Ticket ticketToUpdate){
        return ticketService.updateTicketById(ticketId, ticketToUpdate);
    }
    @DeleteMapping("/id")
    public void deleteTicketById(@PathVariable String ticketId){
        ticketService.deleteTicketById(ticketId);
    }

}
