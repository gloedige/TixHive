package de.iav.backend.service;

import de.iav.backend.model.Ticket;
import de.iav.backend.security.AppUser;
import de.iav.backend.security.AppUserRepository;
import de.iav.backend.security.AppUserRole;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceTest {
    private final AppUserRepository appUserRepository = mock(AppUserRepository.class);
    private final UserService userService = new UserService(appUserRepository);

    @Test
    void findUserByEmail_whenUserExists_thenReturnUser() {
        //GIVEN
        String email = "SampleEmail";
        AppUser expectedUser = new AppUser(
                "SampleId",
                "SampleName",
                email,
                "SamplePassword",
                AppUserRole.USER,
                new ArrayList<>());

        //WHEN
        when(appUserRepository.findByEmail(email)).thenReturn(java.util.Optional.of(expectedUser));
        AppUser foundUser = userService.findUserByEmail(email);

        assertEquals(expectedUser, foundUser);
    }

    @Test
    void findUserByTicketId_whenTicketIdExists_thenReturnUser() {
        //GIVEN
        String ticketId = "SampleId";
        Ticket sampleTicket = new Ticket(
                ticketId,
                "SampleSubject",
                null,
                null,
                null,
                "SampleCreatorId",
                null);
        AppUser expectedUser = new AppUser(
                "SampleId",
                "SampleName",
                "SampleEmail",
                "SamplePassword",
                AppUserRole.USER,
                new ArrayList<>());
        expectedUser.tickets().add(sampleTicket);

        //WHEN
        when(appUserRepository.findAll()).thenReturn(new ArrayList<>() {{
            add(expectedUser);
        }});
        AppUser foundUser = userService.findUserByTicketId(ticketId);

        assertEquals(expectedUser, foundUser);
    }

}