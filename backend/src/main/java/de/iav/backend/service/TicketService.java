package de.iav.backend.service;

import de.iav.backend.dto.TicketRequestDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        Ticket newticket = convertToEntity(ticketRequest);
        return ticketRepository.save(newticket);
    }
    public Ticket convertToEntity(TicketRequestDTO requestDTO) {
        LocalDateTime creationDate = dateTimeService.getCurrentDateTime();
        return new Ticket(
                idService.generateId(),
                requestDTO.getSubject(),
                requestDTO.getPriority(),
                requestDTO.getStatus(),
                requestDTO.getText(),
                requestDTO.getCreatorId(),
                creationDate);
    }
}