package de.iav.frontend.service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.iav.frontend.exception.CustomJsonProcessingException;
import de.iav.frontend.exception.CustomStatusCodeException;
import de.iav.frontend.model.Ticket;
import de.iav.frontend.model.TicketToBeUpdated;
import de.iav.frontend.model.TicketWithoutId;
import de.iav.frontend.security.AuthService;
import javafx.application.Platform;
import javafx.scene.control.TableView;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class TicketService {
    public static final String COOKIE = "Cookie";
    public static final String JSESSIONID = "JSESSIONID=";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TICKET_BASE_URL = "http://localhost:8080/api/tixhive";
    private static final String HEADER_VAR = "application/json";
    private static final String CONTENT_TYP = "Content-Type";

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
            HttpRequest request = HttpRequest.newBuilder().header(COOKIE, JSESSIONID + AuthService.getInstance().sessionId())
                    .uri(URI.create(TICKET_BASE_URL + "/tickets"))
                    .header(CONTENT_TYP, HEADER_VAR)
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

    public List<Ticket> listAllTickets() {
        HttpRequest request = HttpRequest.newBuilder().header(COOKIE, JSESSIONID + AuthService.getInstance().sessionId())
                .uri(URI.create(TICKET_BASE_URL + "/tickets"))
                .GET()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToTicketList)
                .join();
    }

    public void updateTicketById(String id, TicketToBeUpdated ticketToBeUpdated) {
        try {
            String requestBody = objectMapper.writeValueAsString(ticketToBeUpdated);
            HttpRequest request = HttpRequest.newBuilder().header(COOKIE, JSESSIONID + AuthService.getInstance().sessionId())
                    .uri(URI.create(TICKET_BASE_URL + "/tickets/" + id))
                    .header(CONTENT_TYP, HEADER_VAR)
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(this::mapToTicket)
                    .join();
        } catch (JsonProcessingException e) {
            throw new CustomJsonProcessingException("Ticket does not exist!", e);
        }
    }

    public void updateTicketStatusById(String id, TicketToBeUpdated ticketToBeUpdated) {
        try {
            String requestBody = objectMapper.writeValueAsString(ticketToBeUpdated);
            HttpRequest request = HttpRequest.newBuilder().header(COOKIE, JSESSIONID + AuthService.getInstance().sessionId())
                    .uri(URI.create(TICKET_BASE_URL + "/tickets/status/" + id))
                    .header(CONTENT_TYP, HEADER_VAR)
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(this::mapToTicket)
                    .join();
        } catch (JsonProcessingException e) {
            throw new CustomJsonProcessingException("Ticket does not exist!", e);
        }
    }

    public void deleteTicketById(String idToDelete, TableView<Ticket> table) {
        HttpRequest request = HttpRequest.newBuilder().header(COOKIE, JSESSIONID + AuthService.getInstance().sessionId())
                .uri(URI.create(TICKET_BASE_URL + "/tickets/" + idToDelete))
                .DELETE()
                .build();
        httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    if (response.statusCode() == 200) {
                        Platform.runLater(() -> {
                            table.getItems().removeIf(ticket -> ticket.id().equals(idToDelete));
                            table.refresh();
                        });
                    } else {
                        throw new CustomStatusCodeException("Error while deleting food with ID: " + idToDelete);
                    }
                })
                .join();
    }
    private Ticket mapToTicket(String json){
        try{
            return objectMapper.readValue(json, Ticket.class);
        }catch (JsonProcessingException e){
            throw new CustomJsonProcessingException("Could not map to ticket!", e);
        }
    }

    private List<Ticket> mapToTicketList(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new CustomJsonProcessingException("Failed to open ticket list!", e);
        }
    }
}
