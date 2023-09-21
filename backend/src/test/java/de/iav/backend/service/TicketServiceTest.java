package de.iav.backend.service;

import de.iav.backend.dto.TicketRequestDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.model.TicketPriority;
import de.iav.backend.model.TicketStatus;
import de.iav.backend.repository.TicketRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    private final TicketRepository ticketRepository = mock(TicketRepository.class);
    private final IdService idService = mock(IdService.class);
    private final DateTimeService dateTimeService = mock(DateTimeService.class);
    private final TicketService ticketService = new TicketService(ticketRepository, idService, dateTimeService);

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
}