package de.iav.backend.service;

import de.iav.backend.dto.TicketRequestDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.repository.TicketRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    private final TicketRepository ticketRepository = mock(TicketRepository.class);
    private final IdService idService = mock(IdService.class);
    private final DateTimeService dateTimeService = mock(DateTimeService.class);
    private final TicketService ticketService = new TicketService(ticketRepository, idService, dateTimeService);

    @Test
    void addTicket_whenTicketWasAddedSuccessfully_thanReturnTicket() {
        //GIVEN
        TicketRequestDTO ticketRequestDTO = new TicketRequestDTO(
                "subject1",
                "priority1",
                "status1",
                "text1",
                "creatorId1"
        );
        Ticket expectedTicket = new Ticket(
                "SampleId",
                "subject1",
                "priority1",
                "status1",
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
}