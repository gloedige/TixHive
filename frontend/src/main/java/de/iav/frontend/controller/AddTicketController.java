package de.iav.frontend.controller;

import de.iav.frontend.model.TicketWithoutId;
import de.iav.frontend.service.TicketService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
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
    private String ticketId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        priorityOfNewTicket.getItems().addAll("low", "medium", "high");
    }
    @FXML
    public void addTicketButton (ActionEvent event) throws IOException{

            TicketWithoutId newTicket = new TicketWithoutId(
                    subjectOfNewTicket.getText(),
                    priorityOfNewTicket.getValue(),
                    "open",
                    contentOfNewTicket.getText(),
                    "1");
            ticketService.addTicket(newTicket);

        //sceneSwitchService.saveNewTicketAndSwitchToTicketList(event);


    }



}
