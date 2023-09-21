package de.iav.frontend.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.iav.frontend.exception.CustomJsonProcessingException;
import de.iav.frontend.model.Ticket;
import de.iav.frontend.model.TicketWithoutId;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TicketService {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TICKET_BASE_URL = "http://localhost:8080/api/tixhive";
    private static final String HEADER_VAR = "application/json";

    private static TicketService instance;
    public TicketService(){
        objectMapper.registerModule(new JavaTimeModule());
    }
    public static synchronized TicketService getInstance() {
        if (instance == null) {
            instance = new TicketService();
        }
        return instance;
    }

    public void addTicket(TicketWithoutId newTicket) {
        try{
            String requestBody = objectMapper.writeValueAsString(newTicket);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(TICKET_BASE_URL + "/tickets"))
                    .header("Content-Type", HEADER_VAR)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(this::mapToTicket)
                    .join();
        }catch (JsonProcessingException e){
            throw new CustomJsonProcessingException("Could not add ticket!", e);
        }
    }
    private Ticket mapToTicket(String json){
        try{
            return objectMapper.readValue(json, Ticket.class);
        }catch (JsonProcessingException e){
            throw new CustomJsonProcessingException("Could not map to ticket!", e);
        }
    }
}
