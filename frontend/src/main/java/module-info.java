module de.iav.frontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    opens de.iav.frontend.model;
    exports de.iav.frontend.model;
    opens de.iav.frontend.controller to javafx.fxml;
    exports de.iav.frontend.controller;
    opens de.iav.frontend.service to javafx.fxml;
    exports de.iav.frontend.service;
    opens de.iav.frontend to javafx.fxml;
    exports de.iav.frontend;
}