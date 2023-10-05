package de.iav.frontend.controller;

import de.iav.frontend.model.Ticket;
import de.iav.frontend.model.TicketPriority;
import de.iav.frontend.model.TicketStatus;
import de.iav.frontend.model.TicketToBeUpdated;
import de.iav.frontend.service.ChoiceBoxService;
import de.iav.frontend.service.SceneSwitchService;
import de.iav.frontend.service.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HandleTicketController implements Initializable {
    private final TicketService ticketService = TicketService.getInstance();
    @FXML
    private Label subjectOfTicketToBeHandled;
    @FXML
    private ChoiceBox<String> statusOfTicketToBeHandled = new ChoiceBox<>();
    @FXML
    private Label contentOfTicketToBeHandled;
    @FXML
    private Label priorityOfTicketToBeHandled;
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    private final ChoiceBoxService choiceBoxService = new ChoiceBoxService();
    private String ticketId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statusOfTicketToBeHandled.getItems().addAll(TicketStatus.OPEN.toString(), TicketStatus.IN_PROGRESS.toString(), TicketStatus.DONE.toString());
    }

    public int getIndexOfStatusChoiceBox(ChoiceBox<String> choiceBox, Ticket ticketToHandle) {
        int selectedIndex = -1;
        for (int i = 0; i < choiceBox.getItems().size(); i++) {
            String element = choiceBox.getItems().get(i);
            if (element.equals(ticketToHandle.priority().toString())) {
                selectedIndex = i;
                break;
            }
        }
        return selectedIndex;
    }

    @FXML
    public void setSelectedTicket(Ticket selectedTicket) {
        this.ticketId = selectedTicket.id();
        subjectOfTicketToBeHandled.setText(selectedTicket.subject());
        priorityOfTicketToBeHandled.setText(selectedTicket.priority().toString());
        statusOfTicketToBeHandled.getSelectionModel().select(getIndexOfStatusChoiceBox(statusOfTicketToBeHandled, selectedTicket));
        contentOfTicketToBeHandled.setText(selectedTicket.text());
    }

    @FXML
    public void handleTicketAndSwitchToTicketListDevScene(ActionEvent event) throws IOException {
        TicketStatus selectedStatus = choiceBoxService.stringToTicketStatus(statusOfTicketToBeHandled.getValue());
        TicketPriority selectedPriority = choiceBoxService.stringToTicketPriority(priorityOfTicketToBeHandled.getText());

        TicketToBeUpdated ticketToBeHandled = new TicketToBeUpdated(
                ticketId,
                subjectOfTicketToBeHandled.getText(),
                selectedPriority,
                selectedStatus,
                contentOfTicketToBeHandled.getText()
        );
        ticketService.updateTicketStatusById(ticketToBeHandled.id(), ticketToBeHandled);
        sceneSwitchService.switchToTicketListDeveloperScene(event);
    }

    @FXML
    public void switchToTicketListScene(ActionEvent event) throws IOException {
        sceneSwitchService.switchToTicketListScene(event);
    }

    @FXML
    public void switchToLogoutScene(ActionEvent event) throws IOException {
        sceneSwitchService.switchToLogoutScene(event);
    }
}