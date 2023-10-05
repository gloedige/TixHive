package de.iav.frontend.controller;

import de.iav.frontend.exception.CustomIOException;
import de.iav.frontend.security.AppUserRequest;
import de.iav.frontend.security.AppUserRole;
import de.iav.frontend.security.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Label errorLabel;

    private AppUserRequest appUserRequest;

    private final AuthService authService = AuthService.getInstance();

    @FXML
    protected void onLoginClick() {
        login();
    }

    @FXML
    private void login() {
        String username = usernameInput.getText();
        String password = passwordInput.getText();
        String role = appUserRequest.role();

        if (authService.login(username, password, role)) {
            String fxmlResource;
            String sceneTitle;

            if (AppUserRole.ADMIN.equals(role) || AppUserRole.USER.equals(role)) {
                fxmlResource = "/de/iav/frontend/fxml/listAllTickets-scene.fxml";
                sceneTitle = "Ticket List";
            } else {
                fxmlResource = "/de/iav/frontend/fxml/listAllTicketDev-scene.fxml";
                sceneTitle = "Ticket List - Developer";
            }

            navigateToScene(fxmlResource, sceneTitle);
        } else {
            errorLabel.setText(authService.errorMessage());
        }
    }

    private void navigateToScene(String fxmlResource, String sceneTitle) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlResource));

        try {
            root = fxmlLoader.load();
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
