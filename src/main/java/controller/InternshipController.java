package main.java.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.model.Internship;
import main.java.view.InternshipView;

/**
 * This class represents the Event Handler Mouse event to go to the Internship View.
 */
public class InternshipController implements EventHandler {

    private Internship internship;

    public InternshipController(Internship internship) {
        this.internship = internship;
    }

    @Override
    public void handle(Event event) {
        Scene scene = new Scene(new InternshipView(internship), 710, 400);
        Stage stage = new Stage();
        stage.setTitle("Internship Application Organiser");
        stage.setScene(scene);
        stage.show();

        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
