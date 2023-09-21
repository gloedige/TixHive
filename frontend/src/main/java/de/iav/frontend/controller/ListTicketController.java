package de.iav.frontend.controller;

import de.iav.frontend.model.Ticket;
import de.iav.frontend.model.TicketPriority;
import de.iav.frontend.model.TicketStatus;
import de.iav.frontend.service.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ListTicketController {
    private Scene scene;
    private Parent root;
    private Stage stage;
    private final TicketService ticketService = TicketService.getInstance();

    @FXML
    private Button addTicketButton;
    @FXML
    private TableView<Ticket> table;
    @FXML
    private TableColumn<Ticket, String> subjectColumn;
    @FXML
    private TableColumn<Ticket, TicketPriority> priorityColumn;
    @FXML
    private TableColumn<Ticket, TicketStatus> statusColumn;
    @FXML
    private TableColumn<Ticket, LocalDateTime> creationDateColumn;

    public void initialize() {
        List<Ticket> allTicket = ticketService.listAllTickets();
        table.getItems().clear();
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        priorityColumn.setCellValueFactory(new PropertyValueFactory<>("priority"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

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
}
