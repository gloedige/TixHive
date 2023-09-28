package de.iav.frontend.controller;

import de.iav.frontend.model.Ticket;
import de.iav.frontend.service.SceneSwitchService;
import de.iav.frontend.service.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

import java.io.IOException;

public class DeleteTicketController {
    private String idToDelete;
    private TableView<Ticket> table;

    @FXML
    private Label ticketToDeleteId;
    @FXML
    private Label ticketToDeleteSubject;
    private final TicketService ticketService = TicketService.getInstance();
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();

    @FXML
    public void deleteTicketAndSwitchToTicketListScene(ActionEvent event) throws IOException {
        ticketService.deleteTicketById(idToDelete, table);
        sceneSwitchService.switchToTicketListScene(event);
    }

    @FXML
    public void setTicketToDeleteToLabel(Ticket ticketToDeleteToLabel) {
        ticketToDeleteId.setText("ID: " + ticketToDeleteToLabel.id());
        ticketToDeleteSubject.setText("Subject: " + ticketToDeleteToLabel.subject());
    }

    public void initData(String idToDelete, TableView<Ticket> table) {
        this.idToDelete = idToDelete;
        this.table = table;
    }

    @FXML
    public void switchToTicketListScene(ActionEvent event) throws IOException {
        sceneSwitchService.switchToTicketListScene(event);
    }
}
