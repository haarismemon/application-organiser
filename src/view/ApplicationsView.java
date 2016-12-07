package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
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
public class ApplicationsView extends ScrollPane {

    public ApplicationsView() {
        setUp();
    }

    private void setUp() {

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
                    Scene scene = new Scene(new InternshipView(internship), 710, 400);
//                    System.out.println(internship.getApplicationStages());
//                    stage.setMinWidth(500);
//                    stage.setMinHeight(350);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();

                    ((Node) event.getSource()).getScene().getWindow().hide();
                }
            });

            vBox.getChildren().add(bp);
            vBox.getChildren().add(new Separator());

        }

        root.setCenter(vBox);

        setContent(root);
        setFitToHeight(true);
        setFitToWidth(true);

    }
}
