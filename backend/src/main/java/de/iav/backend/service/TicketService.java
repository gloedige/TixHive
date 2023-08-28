package de.iav.backend.service;

import de.iav.backend.model.TicketRequestDTO;
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

    public TicketService(TicketRepository ticketRepository, IdService idService) {
        this.ticketRepository = ticketRepository;
        this.idService = idService;
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
    public Ticket addTicket(TicketRequestDTO ticketRequest)
    {
        Ticket newticket = convertToEntity(ticketRequest);
        return ticketRepository.save(newticket);
    }
    public Ticket updateTicketById(String ticketId, Ticket ticketToUpdate){
        Optional<Ticket> existingTicket = ticketRepository.findById(ticketId);
        if(existingTicket.isPresent()){
            return ticketRepository.save(ticketToUpdate);
        }
        throw new NoSuchElementException("Element with "+ ticketId +" not found!");
    }
    public void deleteTicketById(String ticketId){
        ticketRepository.deleteById(ticketId);
    }


    private Ticket convertToEntity(TicketRequestDTO requestDTO) {
        LocalDateTime creationDate = LocalDateTime.now();
        return new Ticket(
                idService.generateId(),
                requestDTO.getSubject(),
                requestDTO.getPriority(),
                requestDTO.getStatus(),
                requestDTO.getText(),
                creationDate);
    }
}