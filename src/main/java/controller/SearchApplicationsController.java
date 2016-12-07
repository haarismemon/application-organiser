package main.java.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.view.SearchApplicationsView;

/**
 * This class represents the Event Handler Mouse event to go to the Internship View.
 */
public class SearchApplicationsController implements EventHandler {

    @Override
    public void handle(Event event) {
        Scene scene = new Scene(new SearchApplicationsView(), 710, 400);
        Stage stage = new Stage();
        stage.setTitle("Internship Application Organiser");
        stage.setScene(scene);
        stage.show();

        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
