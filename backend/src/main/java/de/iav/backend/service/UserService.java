package de.iav.backend.service;

import de.iav.backend.exceptions.CustomUserNotFoundException;
import de.iav.backend.security.AppUser;
import de.iav.backend.security.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final AppUserRepository appUserRepository;

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
        throw new CustomUserNotFoundException(ticketId);
    }
}
