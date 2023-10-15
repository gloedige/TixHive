package de.iav.frontend.controller;

import de.iav.frontend.model.*;
import de.iav.frontend.service.ChoiceBoxService;
import de.iav.frontend.service.SceneSwitchService;
import de.iav.frontend.service.TicketService;
import de.iav.frontend.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddTicketController implements Initializable {

    private final TicketService ticketService = TicketService.getInstance();

    @FXML
    private TextField subjectOfNewTicket;
    @FXML
    private ChoiceBox<String> priorityOfNewTicket = new ChoiceBox<>();
    @FXML
    private TextField contentOfNewTicket;

    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    private final UserService userService = UserService.getInstance();
    private final ChoiceBoxService choiceBoxService = new ChoiceBoxService();
    private AppUser appUser;

    public void customInitialize(AppUser appUser) {
        this.appUser = appUser;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priorityOfNewTicket.getItems().addAll(TicketPriority.LOW.toString(), TicketPriority.MEDIUM.toString(), TicketPriority.HIGH.toString());
    }
    @FXML
    public void addTicketButton(ActionEvent event) throws IOException {
        TicketPriority selectedPriority = choiceBoxService.stringToTicketPriority(priorityOfNewTicket.getValue());

        TicketWithoutId newTicket = new TicketWithoutId(
                    subjectOfNewTicket.getText(),
                    selectedPriority,
                    TicketStatus.OPEN,
                    contentOfNewTicket.getText(),
                    "1");

        Ticket addedTicket = ticketService.addTicket(newTicket);
        userService.addTicketToAppUser(appUser.email(), addedTicket.id());

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