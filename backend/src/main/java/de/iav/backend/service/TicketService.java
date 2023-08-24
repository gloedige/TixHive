package de.iav.backend.service;

import de.iav.backend.model.Ticket;
import de.iav.backend.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }
    public List<Ticket> listAllTicket(){
        return ticketRepository.findAll();
    }
    public Optional<Ticket> getTicketById (String ticketId){
        return ticketRepository.findById(ticketId);
    }
    public List<Ticket> getTicketByPriority (String priority){
        return ticketRepository.findAllByPriorityOrderByPriority(priority);
    }
    public List<Ticket> getTicketByStatus (String status){
        return ticketRepository.findAllByStatusOrderByStatus(status);
    }
    public List<Ticket> getTicketByCreationDate (LocalDateTime creationDate){
        return ticketRepository.findAllByCreationDateOrderByCreationDate(creationDate);
    }
}
