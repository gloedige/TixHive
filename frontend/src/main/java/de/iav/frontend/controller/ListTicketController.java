package de.iav.frontend.controller;

import de.iav.frontend.model.Ticket;
import de.iav.frontend.model.TicketPriority;
import de.iav.frontend.model.TicketStatus;
import de.iav.frontend.service.TicketService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ListTicketController {
    private final TicketService ticketService = TicketService.getInstance();
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private TableView<Ticket> table;
    @FXML
    private TableColumn<Ticket, String> subjectColumn = new TableColumn<>("Subject");
    @FXML
    private TableColumn<Ticket, TicketPriority> priorityColumn = new TableColumn<>("Priority");
    @FXML
    private TableColumn<Ticket, TicketStatus> statusColumn = new TableColumn<>("Status");
    @FXML
    private TableColumn<Ticket, LocalDateTime> creationDateColumn = new TableColumn<>("Creation date");

    public void initialize() {
        List<Ticket> allTicket = ticketService.listAllTickets();
        table.getItems().clear();
        subjectColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().subject()));
        priorityColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().priority()));
        statusColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().status()));
        creationDateColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().creationDate()));

        table.getItems().addAll(allTicket);
    }
    @FXML
    protected void switchToAddTicketScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/iav/frontend/fxml/addTicket-scene.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }

    @FXML
    public void switchToUpdateTicketScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/iav/frontend/fxml/updateTicket-scene.fxml"));
        root = loader.load();

        Ticket ticketToUpdate = table.getSelectionModel().getSelectedItem();
        UpdateTicketController updateTicketController = loader.getController();
        updateTicketController.setSelectedTicket(ticketToUpdate);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Update Ticket Page");
        stage.show();
    }
}
