package de.iav.frontend.controller;

import de.iav.frontend.TixHiveApplication;
import de.iav.frontend.exception.CustomIOException;
import de.iav.frontend.security.AppUserRequest;
import de.iav.frontend.security.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    private static final String ROLE = "USER";
    @FXML
    public TextField usernameInput;
    @FXML
    public TextField emailInput;
    @FXML
    public PasswordField passwordInput;
    @FXML
    public Label errorLabel;

    @FXML
    protected void onRegisterClick() {
        register();
    }

    private final AuthService authService = AuthService.getInstance();

    @FXML
    protected void onClickBackButton(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(TixHiveApplication.class.getResource("/de/iav/frontend/fxml/login-scene.fxml"));
        root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new CustomIOException(e.toString());
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void register() {
        AppUserRequest appUserRequest = new AppUserRequest(
                usernameInput.getText(),
                emailInput.getText(),
                passwordInput.getText(),
                ROLE
        );

        if (authService.registerAppUser(appUserRequest)) {
            FXMLLoader fxmlLoader = new FXMLLoader(TixHiveApplication.class.getResource("/de/iav/frontend/fxml/login-scene.fxml"));
            root = null;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                throw new CustomIOException(e.toString());
            }
            scene = new Scene(root);
            stage = (Stage) usernameInput.getScene().getWindow();
            stage.setScene(scene);

        } else {
            errorLabel.setText(authService.errorMessage());
        }
    }
}
