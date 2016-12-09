package main.java.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.java.controller.InternshipController;
import main.java.model.ApplicationStage;
import main.java.model.ParseApplications;
import main.java.model.Internship;

/**
 * This class represents the java.view to see all the stages of an Internship.
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
        backBtn.setOnMouseClicked(new InternshipController(internship));

        vBox.getChildren().add(backBtn);

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10, 0, 0, 0));
        gp.setHgap(30);
        gp.setVgap(15);

        Font stageFont = new Font("Regular", 18);

        Label stageTitle = new Label("Stage Name: ");
        gp.add(stageTitle, 0, 0);
        Label completedTitle = new Label("Completed?: ");
        gp.add(completedTitle, 0, 1);
        Label waitingTitle = new Label("Waiting for Response?: ");
        gp.add(waitingTitle, 0, 2);
        Label successfulTitle = new Label("Successful?: ");
        gp.add(successfulTitle, 0, 3);
        Label startDateTitle = new Label("Date of start: ");
        gp.add(startDateTitle, 0, 4);
        Label completionDateTitle = new Label("Date of completion: ");
        gp.add(completionDateTitle, 0, 5);
        Label replyDateTitle = new Label("Date of reply: ");
        gp.add(replyDateTitle, 0, 6);

        Label stageLabel = new Label(stage.getStageName());
        gp.add(stageLabel, 1, 0);
        Label completedLabel;
        if(stage.isCompleted()) completedLabel = new Label("Yes");
        else completedLabel = new Label("No");
        gp.add(completedLabel, 1, 1);

        Label waitingLabel;
        if(stage.isWaitingForResponse()) waitingLabel = new Label("Yes");
        else waitingLabel = new Label("No");
        gp.add(waitingLabel, 1, 2);

        Label successfulLabel;
        if(stage.isSuccessful() == null) successfulLabel = new Label("-");
        else if (stage.isSuccessful()) successfulLabel = new Label("Yes");
        else successfulLabel = new Label("No");
        gp.add(successfulLabel, 1, 3);

        Label startDateLabel;
        if(stage.getDateOfStart() == null) startDateLabel = new Label("-");
        else startDateLabel = new Label(ParseApplications.formatDate(stage.getDateOfStart()));
        gp.add(startDateLabel, 1, 4);

        Label completionDateLabel;
        if(stage.getDateOfCompletion() == null) completionDateLabel = new Label("-");
        else completionDateLabel = new Label(ParseApplications.formatDate(stage.getDateOfCompletion()));
        gp.add(completionDateLabel, 1, 5);

        Label replyDateLabel;
        if(stage.getDateOfReply() == null) replyDateLabel = new Label("-");
        else replyDateLabel = new Label(ParseApplications.formatDate(stage.getDateOfReply()));
        gp.add(replyDateLabel, 1, 6);

        stageTitle.setFont(stageFont);
        completedTitle.setFont(stageFont);
        waitingTitle.setFont(stageFont);
        successfulTitle.setFont(stageFont);
        startDateTitle.setFont(stageFont);
        completionDateTitle.setFont(stageFont);
        replyDateTitle.setFont(stageFont);
        stageLabel.setFont(stageFont);
        completedLabel.setFont(stageFont);
        waitingLabel.setFont(stageFont);
        successfulLabel.setFont(stageFont);
        startDateLabel.setFont(stageFont);
        completionDateLabel.setFont(stageFont);
        replyDateLabel.setFont(stageFont);
        
        vBox.getChildren().add(gp);


        root.setCenter(vBox);

        setContent(root);
        setFitToHeight(true);
        setFitToWidth(true);
    }

}
