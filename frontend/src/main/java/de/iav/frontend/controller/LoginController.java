package de.iav.frontend.controller;

import de.iav.frontend.exception.CustomIOException;
import de.iav.frontend.security.AppUserRole;
import de.iav.frontend.security.AuthService;
import de.iav.frontend.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    private Parent root;
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Label errorLabel;

    private final AuthService authService = AuthService.getInstance();
    private final UserService userService = UserService.getInstance();

    @FXML
    protected void onLoginClick() {
        login();
    }

    @FXML
    private void login() throws CustomIOException {
        String username = usernameInput.getText();
        String password = passwordInput.getText();

        if (authService.login(username, password)) {
            String fxmlResource;
            String sceneTitle;
            String role = userService.findUserByEmail(username).role().toString();

            if (AppUserRole.ADMIN.toString().equals(role) || AppUserRole.USER.toString().equals(role)) {
                fxmlResource = "/de/iav/frontend/fxml/listAllTickets-scene.fxml";
                sceneTitle = "Ticket List";
                navigateToScene(fxmlResource, sceneTitle, role);
            } else {
                fxmlResource = "/de/iav/frontend/fxml/listAllTicketDev-scene.fxml";
                sceneTitle = "Ticket List - Developer";
                navigateToScene(fxmlResource, sceneTitle, role);
            }

        } else {
            errorLabel.setText(authService.errorMessage());
        }
    }

    @FXML
    private void navigateToScene(String fxmlResource, String sceneTitle, String role) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlResource));

        try {
            root = loader.load();
            ListTicketController listTicketController = loader.getController();
            listTicketController.customInitialize(role);
        } catch (Exception e) {
            throw new CustomIOException(e.toString());
        }

        scene = new Scene(root);
        stage = (Stage) usernameInput.getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle(sceneTitle);
    }

    @FXML
    protected void onRegisterClick() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/iav/frontend/fxml/register-scene.fxml"));

        try {
            root = fxmlLoader.load();
        } catch (Exception e) {
            throw new CustomIOException(e.toString());
        }

        scene = new Scene(root);
        stage = (Stage) usernameInput.getScene().getWindow();
        stage.setScene(scene);
    }
}