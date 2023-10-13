package de.iav.backend.service;

import de.iav.backend.Exceptions.CustomUserNotFoundException;
import de.iav.backend.security.AppUser;
import de.iav.backend.security.AppUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final AppUserRepository appUserRepository;

    public UserService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public AppUser findUserByEmail(String email) {
        return appUserRepository.findByEmail(email).orElseThrow();
    }

    public AppUser findUserByTicketId(String ticketId) {
        Iterable<AppUser> allUsers = appUserRepository.findAll();
        for (AppUser user : allUsers) {
            if (user.tickets() == null) {
                continue;
            }
            if (user.tickets().stream().anyMatch(ticket -> ticket.id().equals(ticketId))) {
                return user;
            }
        }
        throw new CustomUserNotFoundException("No user found with ticketId: " + ticketId);
    }
}
