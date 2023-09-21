package de.iav.backend.service;

import de.iav.backend.dto.TicketRequestDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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