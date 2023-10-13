package de.iav.backend.service;

import de.iav.backend.dto.TicketRequestDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.repository.TicketRepository;
import de.iav.backend.security.AppUser;
import de.iav.backend.security.AppUserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final AppUserRepository appUserRepository;
    private final IdService idService;
    private final DateTimeService dateTimeService;
    private final UserService userService;
    private static final String EXCEPTION_MESSAGE_PART1 = "Element with ";
    private static final String EXCEPTION_MESSAGE_PART2 = " not found!";

    public TicketService(TicketRepository ticketRepository, AppUserRepository appUserRepository, IdService idService, DateTimeService dateTimeService, UserService userService) {
        this.userService = userService;
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
        throw new NoSuchElementException(EXCEPTION_MESSAGE_PART1 + ticketId + EXCEPTION_MESSAGE_PART2);
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
        throw new NoSuchElementException(EXCEPTION_MESSAGE_PART1 + ticketId + EXCEPTION_MESSAGE_PART2);
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

    public void deleteTicketById(String ticketId) {
        AppUser appUser = userService.findUserByTicketId(ticketId);

        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        Ticket ticketToDeleteFromUser;
        if (ticketOptional.isPresent()) {
            ticketToDeleteFromUser = ticketOptional.get();
        } else {
            throw new NoSuchElementException(EXCEPTION_MESSAGE_PART1 + ticketId + EXCEPTION_MESSAGE_PART2);
        }

        List<Ticket> mutableTicket = new ArrayList<>(appUser.tickets());
        mutableTicket.remove(ticketToDeleteFromUser);
        AppUser appUserTicketDelete = new AppUser(
                appUser.id(),
                appUser.username(),
                appUser.email(),
                appUser.password(),
                appUser.role(),
                mutableTicket
        );
        appUserRepository.save(appUserTicketDelete);
        ticketRepository.deleteById(ticketId);
    }
}