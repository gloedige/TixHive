package de.iav.backend.controller;

import de.iav.backend.model.TicketRequestDTO;
import de.iav.backend.model.TicketResponseDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.service.TicketService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tixhive")
public class TicketController {
    private final TicketService ticketService;
    private TicketResponseDTO responseDTO = null;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
        this.responseDTO = responseDTO;
    }

    @GetMapping
    public List<Ticket> listAllTicket(){
        return ticketService.listAllTicket();
    }
    @GetMapping("/{id}")
    public Optional<Ticket> getTicketById(@PathVariable String id){
        return ticketService.getTicketById(id);
    }
    @GetMapping("/priority/{priority}")
    public List<Ticket> getTicketByPriority (@PathVariable String priority) {
        return ticketService.getTicketByPriority(priority);
    }
    @GetMapping("/status/{status}")
    public List<Ticket> getTicketByStatus (@PathVariable String status){
        return ticketService.getTicketByStatus(status);
    }
    @GetMapping("/creationDate/{creationDate}")
    public List<Ticket> getTicketByCreationDate(@PathVariable LocalDateTime creationDate){
        return ticketService.getTicketByCreationDate(creationDate);
    }
    @PostMapping
    public Ticket addTicket(@RequestBody TicketRequestDTO ticketInfo){
        Ticket addedTicket = ticketService.addTicket(ticketInfo);

        return addedTicket;//convertToTicketResponseDTO(addedTicket);
    }
    @PutMapping("/{id}")
    public Ticket updateTicketById(@PathVariable String id, @RequestBody Ticket ticketToUpdate){
        return ticketService.updateTicketById(id, ticketToUpdate);
    }
    @DeleteMapping("/{id}")
    public void deleteTicketById(@PathVariable String id){
        ticketService.deleteTicketById(id);
    }


    private TicketResponseDTO convertToTicketResponseDTO(Ticket ticket) {

        // Set properties from the ticket entity to the response DTO
        // ...
        return new TicketResponseDTO(
                ticket.id(),
                ticket.subject(),
                ticket.priority(),
                ticket.status(),
                ticket.text(),
                ticket.creationDate()
        );
    }
}
