package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.ApplicationStage;
import model.Internship;
import model.ParseApplications;


/**
 * This class represents the view to see all the stages of an Internship.
 */
public class InternshipView extends ScrollPane {

    private ParseApplications parseApplications;
    private Internship internship;

    public InternshipView(Internship internship) {
        parseApplications = new ParseApplications();
        this.internship = internship;

        setUp();
    }

    private void setUp() {
        BorderPane root = new BorderPane();

        VBox vBox = new VBox();
        vBox.setSpacing(7);
        vBox.setPadding(new Insets(10, 50, 10, 50));


        Button backBtn = new Button("Back");
        backBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Scene scene = new Scene(new ApplicationsView(), 710, 400);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                ((Node) event.getSource()).getScene().getWindow().hide();
            }
        });

        Button editBtn = new Button("Edit Internship");
        editBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Scene scene = new Scene(new EditInternshipView(parseApplications,  internship), 710, 400);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                ((Node) event.getSource()).getScene().getWindow().hide();
            }
        });

        HBox hBox = new HBox(backBtn, editBtn);
        vBox.getChildren().add(hBox);

        GridPane companyInfoGP = new GridPane();
        companyInfoGP.add(new Label("Company Name: "), 0,0);
        companyInfoGP.add(new Label(internship.getCompany().getName()), 1,0);
        companyInfoGP.add(new Label("Role: "), 0,1);
        companyInfoGP.add(new Label(internship.getRole()), 1,1);
        companyInfoGP.add(new Label("Length: "), 0,2);
        companyInfoGP.add(new Label(internship.getLength()), 1,2);
        companyInfoGP.add(new Label("Location: "), 0,3);
        companyInfoGP.add(new Label(internship.getLocation()), 1,3);
        vBox.getChildren().addAll(companyInfoGP, new Separator());


        for(ApplicationStage stage : internship.getApplicationStages()) {

            BorderPane bp = new BorderPane();
            Label stageNameLabel = new Label(stage.getStageName());
            Label statusLabel = new Label(stage.getCurrentStatus());
            bp.setLeft(stageNameLabel);
            bp.setRight(statusLabel);

            vBox.getChildren().add(bp);

            bp.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Scene scene = new Scene(new StageView(internship, stage), 710, 400);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();

                    ((Node) event.getSource()).getScene().getWindow().hide();
                }
            });
            vBox.getChildren().add(new Separator());
        }

        root.setCenter(vBox);

        setContent(root);
        setFitToHeight(true);
        setFitToWidth(true);
    }

}
