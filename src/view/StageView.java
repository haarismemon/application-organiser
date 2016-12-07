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
import model.StagePanePair;

/**
 * This class represents the view to see all the stages of an Internship.
 */
public class StageView extends ScrollPane {

    private ApplicationStage stage;
    private Internship internship;

    public StageView(Internship internship, ApplicationStage stage) {
        this.stage = stage;
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
                Scene scene = new Scene(new InternshipView(internship), 710, 400);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                ((Node) event.getSource()).getScene().getWindow().hide();
            }
        });

        vBox.getChildren().add(backBtn);

        GridPane gp = new GridPane();
        gp.add(new Label("Stage Name: "), 0, 0);
        gp.add(new Label("Completed?: "), 0, 1);
        gp.add(new Label("Waiting for Response?: "), 0, 2);
        gp.add(new Label("Successful?: "), 0, 3);
        gp.add(new Label("Date of start: "), 0, 4);
        gp.add(new Label("Date of completion: "), 0, 5);
        gp.add(new Label("Date of reply: "), 0, 6);
        gp.add(new Label(stage.getStageName()), 1, 0);
        gp.add(new Label("" + stage.isCompleted()), 1, 1);
        gp.add(new Label("" + stage.isWaitingForResponse()), 1, 2);
        gp.add(new Label("" + stage.isSuccessful()), 1, 3);
        gp.add(new Label(ParseApplications.formatDate(stage.getDateOfStart())), 1, 4);
        gp.add(new Label(ParseApplications.formatDate(stage.getDateOfCompletion())), 1, 5);
        gp.add(new Label(ParseApplications.formatDate(stage.getDateOfReply())), 1, 6);

        vBox.getChildren().add(gp);


        root.setCenter(vBox);

        setContent(root);
        setFitToHeight(true);
        setFitToWidth(true);
    }

}
