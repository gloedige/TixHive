package de.iav.backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.dto.TicketRequestDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.model.TicketPriority;
import de.iav.backend.model.TicketStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class TicketControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/tixhive/tickets";

    public static class TicketControllerJsonParser {
        private static final ObjectMapper objectMapper = new ObjectMapper();

        public static List<Ticket> parseTicketList(String json) throws IOException {
            return objectMapper.readValue(json, new TypeReference<List<Ticket>>() {
            });
        }
    }

    @Test
    void testAddTicket_whenTicketExist_thenReturnStatusAndTicket() throws Exception {
        TicketRequestDTO ticketRequestDTO = new TicketRequestDTO(
                "Test Subject",
                TicketPriority.HIGH,
                TicketStatus.OPEN,
                "Test Text",
                "Test CreatorId");

        String ticketRequestJson = objectMapper.writeValueAsString(ticketRequestDTO);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ticketRequestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subject").value("Test Subject")) // Check the response JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value(TicketPriority.HIGH.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(TicketStatus.OPEN.toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("Test Text"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creatorId").value("Test CreatorId"))
                .andReturn();
    }

    @Test
    void testListAllTicket_whenListNotEmpty_thenReturnListOfTickets() throws Exception {
        MvcResult result = mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        List<Ticket> tickets = TicketControllerJsonParser.parseTicketList(responseJson);
        Assertions.assertNotNull(tickets);
    }
}