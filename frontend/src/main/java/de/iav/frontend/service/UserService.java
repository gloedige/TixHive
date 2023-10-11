package de.iav.frontend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.iav.frontend.exception.CustomJsonProcessingException;
import de.iav.frontend.model.AppUser;
import de.iav.frontend.security.AuthService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class UserService {
    public static final String COOKIE = "Cookie";
    public static final String JSESSIONID = "JSESSIONID=";
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TICKET_BASE_URL = "http://localhost:8080/api/tixhive";

    private static UserService instance;

    public UserService() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static synchronized UserService getInstance() {
        if (instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    public AppUser findUserByEmail(String email) {

        HttpRequest request = HttpRequest.newBuilder().header(COOKIE, JSESSIONID + AuthService.getInstance().sessionId())
                .uri(URI.create(TICKET_BASE_URL + "/users/" + email))
                .GET()
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToUser)
                .join();
    }

    public AppUser addTicketToAppUser(String email, String ticketId) {
        HttpRequest request = HttpRequest.newBuilder().header(COOKIE, JSESSIONID + AuthService.getInstance().sessionId())
                .uri(URI.create(TICKET_BASE_URL + "/users/" + email + "/" + ticketId))
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToUser)
                .join();
    }

    private AppUser mapToUser(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new CustomJsonProcessingException("Failed to find user!", e);
        }
    }
}
