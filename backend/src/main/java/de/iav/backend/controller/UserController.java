package de.iav.backend.controller;

import de.iav.backend.model.Ticket;
import de.iav.backend.repository.TicketRepository;
import de.iav.backend.security.AppUser;
import de.iav.backend.security.AppUserRepository;
import de.iav.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/tixhive/users")
public class UserController {
    private final UserService userService;
    private final AppUserRepository appUserRepository;
    private final TicketRepository ticketRepository;

    public UserController(UserService userService, AppUserRepository appUserRepository, TicketRepository ticketRepository) {
        this.userService = userService;
        this.appUserRepository = appUserRepository;
        this.ticketRepository = ticketRepository;
    }

    @GetMapping("/{email}")
    public AppUser findUserById(@PathVariable String email) {
        return userService.findUserByEmail(email);
    }

    @GetMapping("/{email}/tickets")
    public List<Ticket> findTicketsByUser(@PathVariable String email) {
        AppUser appUser = findUserById(email);
        if (appUser.tickets() == null) {
            return new ArrayList<>();
        } else {
            return appUser.tickets();
        }
    }

    @PutMapping("/{email}/{ticketId}")
    public AppUser addTicketToAppUser(@PathVariable String email, @PathVariable String ticketId) {
        AppUser appUser = findUserById(email);
        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow();
        List<Ticket> newTicketList = appUser.tickets();
        if (newTicketList == null) {
            newTicketList = new ArrayList<>();
        }
        newTicketList.add(ticket);

        return appUserRepository.save(new AppUser(
                appUser.id(),
                appUser.username(),
                appUser.email(),
                appUser.password(),
                appUser.role(),
                newTicketList
        ));
    }
}
