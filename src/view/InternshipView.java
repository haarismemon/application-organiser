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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ApplicationStage;
import model.Applications;
import model.Internship;
import model.ParseApplications;

/**
 * This class represents the view to see all the stages of an Internship.
 */
public class InternshipView extends BorderPane {

    private ParseApplications parseApplications;
    private Internship internship;

    public InternshipView(Internship internship) {
        parseApplications = new ParseApplications();
        this.internship = internship;

        setUp();
    }

    private void setUp() {
//        BorderPane root = new BorderPane();

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

        for(ApplicationStage stage : internship.getApplicationStages()) {

            BorderPane bp = new BorderPane();
            Label stageNameLabel = new Label(stage.getStageName());
            Label statusLabel = new Label(stage.getCurrentStatus());
            bp.setLeft(stageNameLabel);
            bp.setRight(statusLabel);

            vBox.getChildren().add(bp);
            vBox.getChildren().add(new Separator());

        }

        setCenter(vBox);

//        root.setCenter(vBox);

//        getChildren().add(root);
//        setFitToHeight(true);
//        setFitToWidth(true);
    }

}
