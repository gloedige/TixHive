package de.iav.frontend.controller;

import de.iav.frontend.model.AppUser;
import de.iav.frontend.model.Ticket;
import de.iav.frontend.model.TicketPriority;
import de.iav.frontend.model.TicketStatus;
import de.iav.frontend.security.AppUserRole;
import de.iav.frontend.service.TicketService;
import de.iav.frontend.service.UserService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ListTicketController {
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button handleTicketButton;
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

    private final TicketService ticketService = TicketService.getInstance();
    private final UserService userService = UserService.getInstance();
    private AppUser appUser;

    public void customInitialize(AppUser appUser) {
        this.appUser = appUser;

        if (AppUserRole.USER.toString().equals(appUser.role().toString())) {
            updateButton.setDisable(true);
            deleteButton.setDisable(true);

            table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    updateButton.setDisable(false);
                    deleteButton.setDisable(false);
                }
            });
            List<Ticket> allUserTickets = userService.listAllTicketsByUser(appUser.email());
            table.getItems().addAll(allUserTickets);
        }
        if (AppUserRole.ADMIN.toString().equals(appUser.role().toString())) {
            updateButton.setDisable(true);
            deleteButton.setDisable(true);

            table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    updateButton.setDisable(false);
                    deleteButton.setDisable(false);
                }
            });
            List<Ticket> allTicket = ticketService.listAllTickets();
            table.getItems().addAll(allTicket);
        }
        if (AppUserRole.DEVELOPER.toString().equals(appUser.role().toString())) {
            handleTicketButton.setDisable(true);

            table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    handleTicketButton.setDisable(false);
                }
            });
            List<Ticket> allTicket = ticketService.listAllTickets();
            table.getItems().addAll(allTicket);
        }
    }
    public void initialize() {

        table.getItems().clear();

        subjectColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().subject()));
        priorityColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().priority()));
        statusColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().status()));
        creationDateColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().creationDate()));

    }
    @FXML
    protected void switchToAddTicketScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/iav/frontend/fxml/addTicket-scene.fxml"));
        root = loader.load();
        AddTicketController addTicketController = loader.getController();
        addTicketController.customInitialize(appUser);
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
        updateTicketController.iniData(ticketToUpdate, appUser);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Update Ticket Page");
        stage.show();
    }

    @FXML
    public void switchToHandleTicketScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/iav/frontend/fxml/handleTicket-scene.fxml"));
        root = loader.load();

        Ticket ticketToHandle = table.getSelectionModel().getSelectedItem();
        HandleTicketController handleTicketController = loader.getController();
        handleTicketController.iniData(ticketToHandle, appUser);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Handle Ticket Page");
        stage.show();
    }

    @FXML
    protected void buttonToSwitchToConfirmDeleteTicketScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/iav/frontend/fxml/confirmDeleteTicket-scene.fxml"));
        root = loader.load();

        String ticketToDeleteId = table.getSelectionModel().getSelectedItem().id();
        TableView<Ticket> tableToChange = table;
        DeleteTicketController deleteTicketController = loader.getController();
        deleteTicketController.initData(ticketToDeleteId, tableToChange, appUser);
        deleteTicketController.setTicketToDeleteToLabel(table.getSelectionModel().getSelectedItem());

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Delete Ticket");
    }

    @FXML
    protected void switchToLogoutScene(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/de/iav/frontend/fxml/logout-scene.fxml"));
        root = loader.load();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
    }
}
