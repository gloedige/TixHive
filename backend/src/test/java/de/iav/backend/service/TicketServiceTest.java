package de.iav.backend.service;

import de.iav.backend.dto.TicketRequestDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.model.TicketPriority;
import de.iav.backend.model.TicketStatus;
import de.iav.backend.repository.TicketRepository;
import de.iav.backend.security.AppUser;
import de.iav.backend.security.AppUserRepository;
import de.iav.backend.security.AppUserRole;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    private final TicketRepository ticketRepository = mock(TicketRepository.class);
    private final AppUserRepository appUserRepository = mock(AppUserRepository.class);
    private final IdService idService = mock(IdService.class);
    private final DateTimeService dateTimeService = mock(DateTimeService.class);
    private final UserService userservice = mock(UserService.class);
    private final TicketService ticketService = new TicketService(ticketRepository, appUserRepository, idService, dateTimeService, userservice);

    @Test
    void addTicket_whenTicketWasAddedSuccessfully_thenReturnTicket() {
        //GIVEN
        TicketRequestDTO ticketRequestDTO = new TicketRequestDTO(
                "subject1",
                TicketPriority.MEDIUM,
                TicketStatus.OPEN,
                "text1",
                "creatorId1"
        );
        Ticket expectedTicket = new Ticket(
                "SampleId",
                "subject1",
                TicketPriority.MEDIUM,
                TicketStatus.OPEN,
                "text1",
                "creatorId1",
                LocalDateTime.of(2023, 9, 14, 16, 11, 11)
        );

        //WHEN
        when(ticketRepository.save(expectedTicket)).thenReturn(expectedTicket);
        when(idService.generateId()).thenReturn("SampleId");
        when(dateTimeService.getCurrentDateTime()).thenReturn(LocalDateTime.of(2023, 9, 14, 16, 11, 11));
        Ticket addedTicket = ticketService.addTicket(ticketRequestDTO);

        //THEN
        verify(idService).generateId();
        verify(ticketRepository).save(expectedTicket);
        verify(dateTimeService).getCurrentDateTime();
        assertEquals(expectedTicket, addedTicket);
    }

    @Test
    void listAllTicket_whenTicketListNotEmpty_thenReturnTicketList() {
        //GIVEN
        Ticket ticket1 = new Ticket(
                "SampleId1",
                "subject1",
                TicketPriority.MEDIUM,
                TicketStatus.OPEN,
                "text1",
                "creatorId1",
                LocalDateTime.of(2023, 9, 14, 16, 11, 11)
        );
        Ticket ticket2 = new Ticket(
                "SampleId2",
                "subject2",
                TicketPriority.MEDIUM,
                TicketStatus.OPEN,
                "text2",
                "creatorId2",
                LocalDateTime.of(2023, 9, 14, 16, 11, 11)
        );

        //WHEN
        when(ticketRepository.findAll()).thenReturn(List.of(ticket1, ticket2));
        List<Ticket> expectedTicketList = List.of(ticket1, ticket2);
        var addedTicketList = ticketService.listAllTicket();

        //THEN
        verify(ticketRepository).findAll();
        assertEquals(expectedTicketList, addedTicketList);
    }

    @Test
    void updateTicket_whenTicketWithIdExist_thenReturnUpdatedTicket() {
        //GIVEN
        TicketRequestDTO ticketWithChangesDTO = new TicketRequestDTO(
                "subject1",
                TicketPriority.MEDIUM,
                TicketStatus.OPEN,
                "text1",
                "creatorId1"
        );
        Ticket updatedTicket = new Ticket(
                "SampleId1",
                "subject1",
                TicketPriority.MEDIUM,
                TicketStatus.OPEN,
                "text1",
                "creatorId1",
                LocalDateTime.of(2023, 9, 14, 16, 11, 11)
        );
        //WHEN
        when(ticketRepository.findById("SampleId1")).thenReturn(java.util.Optional.of(updatedTicket));
        when(ticketRepository.save(updatedTicket)).thenReturn(updatedTicket);
        var newTicket = ticketService.updateTicketById("SampleId1", ticketWithChangesDTO);

        //THEN
        verify(ticketRepository).findById("SampleId1");
        assertEquals(updatedTicket, newTicket);
    }

    @Test
    void updateTicket_whenTicketWithIdNotExist_thenThrowNoSuchElementException() {
        //GIVEN
        TicketRequestDTO ticketWithChangesDTO = new TicketRequestDTO(
                "subject1",
                TicketPriority.MEDIUM,
                TicketStatus.OPEN,
                "text1",
                "creatorId1"
        );
        //WHEN
        when(ticketRepository.findById("SampleId1")).thenReturn(Optional.empty());

        //THEN
        assertThrows(NoSuchElementException.class, () -> {
            ticketService.updateTicketById("SampleId1", ticketWithChangesDTO);
        });
    }

    @Test
    void deleteTicket_whenTicketWithIdExist_thenDeleteTicket() {
        String email = "userEmail";
        String ticketId = "SampleId1";
        //GIVEN

        Ticket ticket1 = new Ticket(
                ticketId,
                "subject1",
                TicketPriority.MEDIUM,
                TicketStatus.OPEN,
                "text1",
                "creatorId1",
                LocalDateTime.of(2023, 9, 14, 16, 11, 11)
        );
        AppUser appUser = new AppUser(
                "userId1",
                "username1",
                email,
                "password1",
                AppUserRole.USER,
                List.of(ticket1)
        );
        //WHEN
        when(userservice.findUserByTicketId(ticketId)).thenReturn(appUser);
        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket1));
        when(ticketRepository.save(ticket1)).thenReturn(ticket1);
        ticketService.deleteTicketById(ticketId);

        //THEN
        verify(ticketRepository).deleteById(ticketId);
    }
}