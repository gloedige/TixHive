package de.iav.frontend.controller;

import de.iav.frontend.model.*;
import de.iav.frontend.service.ChoiceBoxService;
import de.iav.frontend.service.SceneSwitchService;
import de.iav.frontend.service.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateTicketController implements Initializable {
    private final TicketService ticketService = TicketService.getInstance();
    @FXML
    private TextField subjectOfTicketToBeUpdated;
    @FXML
    private ChoiceBox<String> priorityOfTicketToBeUpdated = new ChoiceBox<>();
    @FXML
    private TextField contentOfTicketToBeUpdated;
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    private final ChoiceBoxService choiceBoxService = new ChoiceBoxService();
    private String ticketId;
    private TicketStatus status;
    private AppUser appUser;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priorityOfTicketToBeUpdated.getItems().addAll(TicketPriority.LOW.toString(), TicketPriority.MEDIUM.toString(), TicketPriority.HIGH.toString());
    }

    public int getIndexOfPriorityChoiceBox(ChoiceBox<String> choiceBox, Ticket ticketToUpdate) {
        int selectedIndex = -1;
        for (int i = 0; i < choiceBox.getItems().size(); i++) {
            String element = choiceBox.getItems().get(i);
            if (element.equals(ticketToUpdate.priority().toString())) {
                selectedIndex = i;
                break;
            }
        }
        return selectedIndex;
    }
    @FXML
    public void iniData(Ticket selectedTicket, AppUser appUser) {
        this.appUser = appUser;
        this.ticketId = selectedTicket.id();
        subjectOfTicketToBeUpdated.setText(selectedTicket.subject());
        priorityOfTicketToBeUpdated.getSelectionModel().select(getIndexOfPriorityChoiceBox(priorityOfTicketToBeUpdated, selectedTicket));
        this.status = selectedTicket.status();
        contentOfTicketToBeUpdated.setText(selectedTicket.text());
    }

    @FXML
    public void updateTicketAndSwitchToTicketListScene(ActionEvent event) throws IOException {
        TicketPriority selectedPriority = choiceBoxService.stringToTicketPriority(priorityOfTicketToBeUpdated.getValue());

        TicketToBeUpdated ticketToBeUpdated = new TicketToBeUpdated(
                ticketId,
                subjectOfTicketToBeUpdated.getText(),
                selectedPriority,
                status,
                contentOfTicketToBeUpdated.getText()
        );
        ticketService.updateTicketById(ticketToBeUpdated.id(), ticketToBeUpdated);
        sceneSwitchService.switchToTicketListScene(event, appUser);
    }

    @FXML
    public void switchToTicketListScene(ActionEvent event) throws IOException {
        sceneSwitchService.switchToTicketListScene(event, appUser);
    }

    @FXML
    public void switchToLogoutScene(ActionEvent event) throws IOException {
        sceneSwitchService.switchToLogoutScene(event);
    }
}
