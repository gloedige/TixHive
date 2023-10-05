package de.iav.frontend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitchService {
    private Parent root;
    private Stage stage;
    private Scene scene;

    private static SceneSwitchService instance;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public SceneSwitchService() {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static synchronized SceneSwitchService getInstance() {
        if (instance == null) {
            instance = new SceneSwitchService();
        }
        return instance;
    }

    public void switchToTicketListScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/iav/frontend/fxml/listAllTickets-scene.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ticket List");
        stage.show();
    }

    public void switchToTicketListDeveloperScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/iav/frontend/fxml/listAllTicketDev-scene.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ticket List");
        stage.show();
    }

    public void switchToLogoutScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/iav/frontend/fxml/logout-scene.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
}
