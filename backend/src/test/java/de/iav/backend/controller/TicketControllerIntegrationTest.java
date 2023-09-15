package de.iav.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.dto.TicketRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class TicketControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_URL = "/api/tixhive/ticket";

    @Test
    void testAddTicket_whenTicketExist_thenReturnStatusAndTicket() throws Exception {
        // Create a TicketRequestDTO to send in the request
        TicketRequestDTO ticketRequestDTO = new TicketRequestDTO();
        ticketRequestDTO.setSubject("Test Subject");
        ticketRequestDTO.setPriority("High");
        ticketRequestDTO.setStatus("Open");
        ticketRequestDTO.setText("Test Text");
        ticketRequestDTO.setCreatorId("Test CreatorId");

        // Convert the DTO to JSON
        String ticketRequestJson = objectMapper.writeValueAsString(ticketRequestDTO);

        // Perform a POST request to the addTicket endpoint
        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ticketRequestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subject").value("Test Subject")) // Check the response JSON
                .andExpect(MockMvcResultMatchers.jsonPath("$.priority").value("High"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Open"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text").value("Test Text"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.creatorId").value("Test CreatorId"))
                .andReturn();
    }

}