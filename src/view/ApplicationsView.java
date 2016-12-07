package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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

        Label heading = new Label("Applications");
        heading.setAlignment(Pos.CENTER);
        heading.setFont(new Font(new Label().getFont().getStyle(), 30));
        vBox.getChildren().add(heading);

        Label requireActionLabel = new Label("Require Action");
        requireActionLabel.setAlignment(Pos.CENTER);
        requireActionLabel.setFont(new Font(new Label().getFont().getStyle(), 20));
        vBox.getChildren().add(requireActionLabel);

        //add internships that require action
        for(Internship internship : applications.getApplications()) {
            if(!internship.getCurrentStage().isCompleted()) {
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
        }

        Label allLabel = new Label("All Applications");
        allLabel.setAlignment(Pos.CENTER);
        allLabel.setFont(new Font(new Label().getFont().getStyle(), 20));
        vBox.getChildren().add(allLabel);

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
