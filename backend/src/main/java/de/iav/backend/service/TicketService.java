package de.iav.backend.service;

import de.iav.backend.dto.TicketRequestDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.repository.TicketRepository;
import de.iav.backend.security.AppUser;
import de.iav.backend.security.AppUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final AppUserRepository appUserRepository;
    private final IdService idService;
    private final DateTimeService dateTimeService;

    public TicketService(TicketRepository ticketRepository, AppUserRepository appUserRepository, IdService idService, DateTimeService dateTimeService) {
        this.ticketRepository = ticketRepository;
        this.appUserRepository = appUserRepository;
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

    public Ticket updateTicketStatusById(String ticketId, TicketRequestDTO ticketToUpdate) {
        Optional<Ticket> existingTicket = ticketRepository.findById(ticketId);
        if (existingTicket.isPresent()) {
            Ticket updatedTicket = new Ticket(
                    existingTicket.get().id(),
                    existingTicket.get().subject(),
                    existingTicket.get().priority(),
                    ticketToUpdate.status(),
                    existingTicket.get().text(),
                    existingTicket.get().creatorId(),
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

    public void deleteTicketById(String ticketId, String email) {

        Optional<AppUser> appUserOptional = appUserRepository.findByEmail(email);
        AppUser appUser;
        if (appUserOptional.isPresent()) {
            appUser = appUserOptional.get();
        } else {
            throw new NoSuchElementException("Element with " + email + " not found!");
        }
        Ticket ticketToDeleteFromUser = ticketRepository.findById(ticketId).orElseThrow(() -> new NoSuchElementException("Element with " + ticketId + " not found!"));
        appUser.tickets().remove(ticketToDeleteFromUser);
        ticketRepository.deleteById(ticketId);
    }
}