package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ApplicationStage;
import model.Applications;
import model.Internship;

/**
 * This class represents the Applications window that allows user to view all the Internships applied to
 */
public class ApplicationsView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Internship Application Organiser");

        VBox vBox = new VBox();

        Applications applications = new Applications();

        Internship accentureTech = new Internship("Accenture", "Technology Industrial Placement");
        accentureTech.setLength("12 Months");
        accentureTech.setLocation("London");

        ApplicationStage firstStage = new model.ApplicationStage("Online Application");
        firstStage.setCompleted(true);
        firstStage.setSuccessful(true);
        accentureTech.addStage(firstStage);

        ApplicationStage secondStage = new ApplicationStage("Online Test");
        secondStage.setCompleted(true);
        secondStage.setSuccessful(true);
        accentureTech.addStage(secondStage);

        ApplicationStage thirdStage = new ApplicationStage("Online Interview");
        thirdStage.setCompleted(true);
        thirdStage.setWaitingForResponse(true);
        accentureTech.addStage(thirdStage);

        applications.addInternship(accentureTech);

        Internship barclaysSpring = new Internship("Barclays", "Technology Spring Insight");
        applications.addInternship(barclaysSpring);


        for(Internship internship : applications.getApplications()) {
            vBox.getChildren().add(new Label(internship.print()));
        }

        // A new WelcomeView scene which is the Main Menu
        Scene scene = new Scene(vBox, 600, 300);
        primaryStage.setMinWidth(500);
        primaryStage.setMinHeight(350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
