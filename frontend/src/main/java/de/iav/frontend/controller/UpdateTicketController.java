package de.iav.frontend.controller;

import de.iav.frontend.model.Ticket;
import de.iav.frontend.service.SceneSwitchService;
import de.iav.frontend.service.TicketService;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class UpdateTicketController {
    private final TicketService ticketService = TicketService.getInstance();
    @FXML
    private TextField subjectOfTicketToBeUpdated;
    @FXML
    private final ChoiceBox<String> priorityOfNewTicket = new ChoiceBox<>();
    @FXML
    private TextField contentOfTicketToBeUpdated;
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    private String TicketId;

    public int getIndexOfPriority(ChoiceBox<String> choiceBox, Ticket ticketToUpdate) {
        // Find the index of the specified Food object in the ChoiceBox.
        int selectedIndex = -1; // Default value if the item is not found.
        for (int i = 0; i < choiceBox.getItems().size(); i++) {
            String element = choiceBox.getItems().get(i);
            if (element.equals(ticketToUpdate.priority())) {
                selectedIndex = i;
                break;
            }
        }
        return selectedIndex;
    }

    @FXML
    public void setSelectedTicket(Ticket selectedTicket) {
        this.TicketId = selectedTicket.id();
        subjectOfTicketToBeUpdated.setText(selectedTicket.subject());
        priorityOfNewTicket.getSelectionModel().select(getIndexOfPriority(priorityOfNewTicket, selectedTicket));
        contentOfTicketToBeUpdated.setText(selectedTicket.text());
    }
}
