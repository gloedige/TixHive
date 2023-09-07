package de.iav.frontend.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TicketService {
    public static final String COOKIE = "Cookie";
    public static final String JSESSIONID = "JSESSIONID=";
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
}
