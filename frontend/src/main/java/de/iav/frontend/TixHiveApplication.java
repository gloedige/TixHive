package de.iav.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TixHiveApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TixHiveApplication.class.getResource("/de/iav/frontend/fxml/addTicket-scene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("new Ticket");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}