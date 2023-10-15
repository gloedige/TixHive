package de.iav.backend.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.dto.TicketRequestDTO;
import de.iav.backend.model.Ticket;
import de.iav.backend.model.TicketPriority;
import de.iav.backend.model.TicketStatus;
import de.iav.backend.security.AppUser;
import de.iav.backend.security.AppUserRequest;
import de.iav.backend.security.AppUserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(username = "user")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final String BASE_USER_URL = "/api/tixhive/users";
    private final String BASE_AUTH_URL = "/api/auth/register";
    private final String BASE_TICKET_URL = "/api/tixhive/tickets";

    public static class UserControllerJsonParser {
        private static final ObjectMapper objectMapper = new ObjectMapper();
        public static AppUser parseAppUser(String json) throws IOException {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        }
    }


    @Test
    void testFindUserByMail_whenMailExist_thenReturnUser() throws Exception {
        String UserEmail = "UserEmail";
        AppUserRequest userToFind = new AppUserRequest(
                "Username",
                UserEmail,
                "UserPassword",
                AppUserRole.USER);

        String userRequestJson = objectMapper.writeValueAsString(userToFind);

        mockMvc.perform(post(BASE_AUTH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andExpect(status().is(201))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        MvcResult mvcResult = mockMvc.perform(get(BASE_USER_URL + "/" + UserEmail))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseJson = mvcResult.getResponse().getContentAsString();
        AppUser foundUser = UserControllerJsonParser.parseAppUser(responseJson);

        Assertions.assertNotNull(foundUser);
    }

    @Test
    void testFindTicketsByUser_whenUserExist_listAllUserTickets() throws Exception {
        String UserEmail = "UserEmail";
        AppUserRequest userToFind = new AppUserRequest(
                "Username",
                UserEmail,
                "UserPassword",
                AppUserRole.USER);
        String userRequestJson = objectMapper.writeValueAsString(userToFind);
        mockMvc.perform(post(BASE_AUTH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andExpect(status().is(201))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        TicketRequestDTO ticketRequestDTO = new TicketRequestDTO(
                "Test Subject",
                TicketPriority.HIGH,
                TicketStatus.OPEN,
                "Test Text",
                "Test CreatorId");
        String ticketRequestJson = objectMapper.writeValueAsString(ticketRequestDTO);
        MvcResult result = mockMvc.perform(post(BASE_TICKET_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ticketRequestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseJson = result.getResponse().getContentAsString();
        Ticket addedTicket = objectMapper.readValue(responseJson, Ticket.class);
        String ticketId = addedTicket.id();


        mockMvc.perform(MockMvcRequestBuilders.put(BASE_USER_URL + "/" + UserEmail + "/" + ticketId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        MvcResult mvcResult = mockMvc.perform(get(BASE_USER_URL + "/" + UserEmail + "/tickets"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseJson2 = mvcResult.getResponse().getContentAsString();
        Ticket[] foundTickets = objectMapper.readValue(responseJson2, Ticket[].class);
        Assertions.assertNotNull(foundTickets);
    }

    @Test
    void testAddTicketToAppUser_whenAppUserAndTicketExist_thenAddTicketToUser() throws Exception {
        String UserEmail = "UserEmail";
        AppUserRequest userToFind = new AppUserRequest(
                "Username",
                UserEmail,
                "UserPassword",
                AppUserRole.USER);
        String userRequestJson = objectMapper.writeValueAsString(userToFind);
        mockMvc.perform(post(BASE_AUTH_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userRequestJson))
                .andExpect(status().is(201))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();


        TicketRequestDTO ticketRequestDTO = new TicketRequestDTO(
                "Test Subject",
                TicketPriority.HIGH,
                TicketStatus.OPEN,
                "Test Text",
                "Test CreatorId");
        String ticketRequestJson = objectMapper.writeValueAsString(ticketRequestDTO);
        MvcResult newTicketResult = mockMvc.perform(post(BASE_TICKET_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ticketRequestJson))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        String responseJson = newTicketResult.getResponse().getContentAsString();
        Ticket addedTicket = objectMapper.readValue(responseJson, Ticket.class);
        String ticketId = addedTicket.id();


        MvcResult updatedUserResult = mockMvc.perform(MockMvcRequestBuilders.put(BASE_USER_URL + "/" + UserEmail + "/" + ticketId))
                .andExpect(status().isOk())
                .andReturn();
        String responseJson2 = updatedUserResult.getResponse().getContentAsString();
        AppUser updatedUser = objectMapper.readValue(responseJson2, AppUser.class);
        Assertions.assertNotNull(updatedUser.tickets());
    }
}