package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ApplicationStage;
import model.Applications;
import model.Internship;
import model.ParseApplications;

/**
 * This class represents the Applications window that allows user to view all the Internships applied to
 */
public class ApplicationsView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Internship Application Organiser");

        VBox vBox = new VBox();

        Applications applications = ParseApplications.parse();


        for(Internship internship : applications.getApplications()) {
            vBox.getChildren().add(new Label(internship.print()));
        }

        // A new WelcomeView scene which is the Main Menu
        Scene scene = new Scene(vBox, 700, 300);
        primaryStage.setMinWidth(550);
        primaryStage.setMinHeight(450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
