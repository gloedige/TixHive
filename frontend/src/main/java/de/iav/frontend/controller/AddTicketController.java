package de.iav.frontend.controller;

import de.iav.frontend.model.TicketPriority;
import de.iav.frontend.model.TicketStatus;
import de.iav.frontend.model.TicketWithoutId;
import de.iav.frontend.service.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class AddTicketController implements Initializable {

    private final TicketService ticketService = TicketService.getInstance();
    @FXML
    private Button addTicketButton;
    @FXML
    private TextField subjectOfNewTicket;
    @FXML
    private ChoiceBox<String> priorityOfNewTicket = new ChoiceBox<>();
    @FXML
    private TextField contentOfNewTicket;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priorityOfNewTicket.getItems().addAll(TicketPriority.LOW.toString(), TicketPriority.MEDIUM.toString(), TicketPriority.HIGH.toString());
    }
    @FXML
    public void addTicketButton(ActionEvent event) {
        String selectedValue = priorityOfNewTicket.getValue();
        TicketPriority selectedPriority;
        if (selectedValue.equals(TicketPriority.LOW.toString())) {
            selectedPriority = TicketPriority.LOW;
        } else if (selectedValue.equals(TicketPriority.MEDIUM.toString())) {
            selectedPriority = TicketPriority.MEDIUM;
        } else selectedPriority = TicketPriority.HIGH;
            TicketWithoutId newTicket = new TicketWithoutId(
                    subjectOfNewTicket.getText(),
                    selectedPriority,
                    TicketStatus.OPEN,
                    contentOfNewTicket.getText(),
                    "1");
            ticketService.addTicket(newTicket);
    }
}