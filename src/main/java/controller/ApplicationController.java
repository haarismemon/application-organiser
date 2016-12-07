package main.java.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.model.Internship;
import main.java.view.ApplicationsView;
import main.java.view.InternshipView;

/**
 * This class represents the Event Handler Mouse event to go to the Internship View.
 */
public class ApplicationController implements EventHandler {

    @Override
    public void handle(Event event) {
        Scene scene = new Scene(new ApplicationsView(), 710, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
