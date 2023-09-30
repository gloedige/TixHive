package de.iav.frontend.controller;

import de.iav.frontend.exception.CustomIOException;
import de.iav.frontend.security.AppUserRole;
import de.iav.frontend.security.AuthService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private Label errorLabel;
    @FXML
    private ChoiceBox<String> roleChoiceBox;

    private final AuthService authService = AuthService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roleChoiceBox.getItems().addAll(AppUserRole.USER.toString(), AppUserRole.ADMIN.toString(), AppUserRole.DEVELOPER.toString());
    }

    @FXML
    protected void onLoginClick() {
        login();
    }

    @FXML
    private void login() {
        String selectedValue = roleChoiceBox.getValue();
        AppUserRole selectedRole;
        if (selectedValue.equals(AppUserRole.USER.toString())) {
            selectedRole = AppUserRole.USER;
        } else if (selectedValue.equals(AppUserRole.ADMIN.toString())) {
            selectedRole = AppUserRole.ADMIN;
        } else selectedRole = AppUserRole.DEVELOPER;

        if (authService.login(usernameInput.getText(), passwordInput.getText(), selectedRole)) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/iav/frontend/fxml/listAllTickets-scene.fxml"));

            try {
                root = fxmlLoader.load();
            } catch (Exception e) {
                throw new CustomIOException(e.toString());

            }

            scene = new Scene(root);
            stage = (Stage) usernameInput.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Ticket List");
        } else {
            errorLabel.setText(authService.errorMessage());
        }
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
