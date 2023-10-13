package de.iav.frontend.controller;

import de.iav.frontend.model.AppUser;
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
    private AppUser appUser;

    @FXML
    private Label ticketToDeleteSubject;
    @FXML
    private Label ticketToDeletePriority;
    @FXML
    private Label ticketToDeleteText;
    private final TicketService ticketService = TicketService.getInstance();
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();

    @FXML
    public void deleteTicketAndSwitchToTicketListScene(ActionEvent event) throws IOException {
        ticketService.deleteTicketById(idToDelete, appUser.email(), table);
        sceneSwitchService.switchToTicketListScene(event, appUser);
    }

    @FXML
    public void setTicketToDeleteToLabel(Ticket ticketToDeleteToLabel) {
        ticketToDeleteSubject.setText(ticketToDeleteToLabel.subject());
        ticketToDeletePriority.setText(ticketToDeleteToLabel.priority().toString());
        ticketToDeleteText.setText(ticketToDeleteToLabel.text());
    }

    public void initData(String idToDelete, TableView<Ticket> table, AppUser appUser) {
        this.idToDelete = idToDelete;
        this.table = table;
        this.appUser = appUser;
    }

    @FXML
    public void switchToTicketListScene(ActionEvent event) throws IOException {
        sceneSwitchService.switchToTicketListScene(event, appUser);
    }
}
