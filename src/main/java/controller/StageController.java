package main.java.controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.model.ApplicationStage;
import main.java.model.Internship;
import main.java.view.InternshipView;
import main.java.view.StageView;

/**
 * This class represents the Event Handler Mouse event to go to the Internship View.
 */
public class StageController implements EventHandler {

    private Internship internship;
    private ApplicationStage stage;

    public StageController(Internship internship, ApplicationStage stage) {
        this.internship = internship;
        this.stage = stage;
    }

    @Override
    public void handle(Event event) {
        Scene scene = new Scene(new StageView(internship, stage), 710, 400);
        Stage stage = new Stage();
        stage.setTitle("Internship Application Organiser");
        stage.setScene(scene);
        stage.show();

        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
