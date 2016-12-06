package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

        BorderPane root = new BorderPane();

        VBox vBox = new VBox();
        vBox.setSpacing(7);
        vBox.setPadding(new Insets(10, 50, 10, 50));

        ParseApplications parseApplications = new ParseApplications();
        Applications applications = parseApplications.getApplications();


        for(Internship internship : applications.getApplications()) {

            BorderPane bp = new BorderPane();
            HBox hBox = new HBox();
            Label companyLabel = new Label(internship.getCompanyName());
            companyLabel.setMinWidth(150);
            hBox.getChildren().add(companyLabel);
            Label roleLabel = new Label(internship.getRole());
            hBox.getChildren().add(roleLabel);
            bp.setCenter(hBox);
            bp.setRight(new Label(internship.printCurrentStage()));

            bp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
//                    applications.getInternship(internship.getCompanyName(), internship.getRole());
                    System.out.println(internship);
                }
            });

            vBox.getChildren().add(bp);
            vBox.getChildren().add(new Separator());

        }

        root.setCenter(vBox);

        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);


        // A new WelcomeView scene which is the Main Menu
        Scene scene = new Scene(scrollPane, 710, 300);
        primaryStage.setMinWidth(550);
        primaryStage.setMinHeight(450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
