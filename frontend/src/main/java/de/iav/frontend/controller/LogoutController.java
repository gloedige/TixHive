package de.iav.frontend.controller;

import de.iav.frontend.exception.CustomIOException;
import de.iav.frontend.security.AuthService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LogoutController {

    private final AuthService authService = AuthService.getInstance();

    @FXML
    private void switchToLoginScene(ActionEvent event) throws IOException {
        authService.logout();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/de/iav/frontend/fxml/login-scene.fxml"));
        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (Exception e) {
            throw new CustomIOException(e.toString());
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}
