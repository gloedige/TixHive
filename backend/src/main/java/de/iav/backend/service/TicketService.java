package de.iav.backend.service;

import de.iav.backend.dto.TicketRequestDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final IdService idService;
    private final DateTimeService dateTimeService;

    public TicketService(TicketRepository ticketRepository, IdService idService, DateTimeService dateTimeService) {
        this.ticketRepository = ticketRepository;
        this.idService = idService;
        this.dateTimeService = dateTimeService;
    }
    public Ticket addTicket(TicketRequestDTO ticketRequest)
    {
        Ticket newticket = createTicket(ticketRequest);
        return ticketRepository.save(newticket);
    }

    public List<Ticket> listAllTicket() {
        return ticketRepository.findAll();
    }

    public Ticket updateTicketById(String ticketId, TicketRequestDTO ticketToUpdate) {
        Optional<Ticket> existingTicket = ticketRepository.findById(ticketId);
        if (existingTicket.isPresent()) {
            Ticket updatedTicket = new Ticket(
                    existingTicket.get().id(),
                    ticketToUpdate.subject(),
                    ticketToUpdate.priority(),
                    ticketToUpdate.status(),
                    ticketToUpdate.text(),
                    ticketToUpdate.creatorId(),
                    existingTicket.get().creationDate()
            );
            return ticketRepository.save(updatedTicket);
        }
        throw new NoSuchElementException("Element with " + ticketId + " not found!");
    }

    public Ticket createTicket(TicketRequestDTO ticketRequest) {
        LocalDateTime creationDate = dateTimeService.getCurrentDateTime();
        return new Ticket(
                idService.generateId(),
                ticketRequest.subject(),
                ticketRequest.priority(),
                ticketRequest.status(),
                ticketRequest.text(),
                ticketRequest.creatorId(),
                creationDate);
    }
}