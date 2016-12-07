package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ApplicationStage;
import model.Internship;
import model.ParseApplications;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class represents the view to see all the stages of an Internship.
 */
public class AddStageView extends ScrollPane {

    private ParseApplications parseApplications;
    private Internship internship;

    private TextField companyNameTextField;
    private TextField roleTextField;
    private TextField lengthTextField;
    private TextField locationTextField;

    private Map<ApplicationStage, Map<String, TextField>> stageUpdatedInfomation;
    private TextField stageNameTextField;
    private TextField isCompletedTextField;
    private TextField isWaitingTextField;
    private TextField isSuccessfulTextField;
    private TextField startDateTextField;
    private TextField completedDateTextField;
    private TextField replyDateTextField;

    public AddStageView(ParseApplications parseApplications, Internship internship) {
        this.internship = internship;
        this.parseApplications = parseApplications;
        stageUpdatedInfomation = new LinkedHashMap<>();

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
                goBackToInternshipView(event);
            }
        });

        Button saveBtn = new Button("Save");
        saveBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ApplicationStage newStage = new ApplicationStage(stageNameTextField.getText());
                String is_completed = isCompletedTextField.getText();
                String is_waiting = isWaitingTextField.getText();
                String is_successful = isSuccessfulTextField.getText();
                String date_of_start = startDateTextField.getText();
                String date_of_completed = completedDateTextField.getText();
                String date_of_reply = replyDateTextField.getText();

                if(is_completed.equals("true")) {
                    if(!date_of_completed.equals("null")) newStage.setCompleted(true, date_of_completed);
                    else newStage.setCompleted(true);
                }
                else if(is_completed.equals("false")) newStage.setCompleted(false);

                if(is_waiting.equals("true")) {
//                            System.out.println("is waiting");
                    newStage.setWaitingForResponse(true);
                }
                else if(is_waiting.equals("false")) newStage.setWaitingForResponse(false);

                if(is_successful.equals("true")) {
                    if(!date_of_reply.equals("null")) newStage.setSuccessful(true, date_of_reply);
                    else newStage.setSuccessful(true);
                }
                else if(is_successful.equals("false")) newStage.setSuccessful(false, date_of_reply);
//                        else if(is_successful.equals("null")) newStage.setSuccessful(null);

                if(!date_of_start.equals("null")) newStage.setStartDate(date_of_start);

                internship.addStage(newStage);

                parseApplications.updateInternshipCache(internship);

                goBackToInternshipView(event);
            }
        });

        vBox.getChildren().addAll(backBtn, saveBtn);

        GridPane gp = new GridPane();

        gp.add(new Label("Stage Name: "), 0, 0);
        gp.add(new Label("Completed?: "), 0, 1);
        gp.add(new Label("Waiting for Response?: "), 0, 2);
        gp.add(new Label("Successful?: "), 0, 3);
        gp.add(new Label("Date of start: "), 0, 4);
        gp.add(new Label("Date of completion: "), 0, 5);
        gp.add(new Label("Date of reply: "), 0, 6);

        stageNameTextField = new TextField();
        isCompletedTextField = new TextField();
        isWaitingTextField = new TextField();
        isSuccessfulTextField = new TextField();
        startDateTextField = new TextField();
        completedDateTextField = new TextField();
        replyDateTextField = new TextField();

        gp.add(stageNameTextField, 1, 0);
        gp.add(isCompletedTextField, 1, 1);
        gp.add(isWaitingTextField, 1, 2);
        gp.add(isSuccessfulTextField, 1, 3);
        gp.add(startDateTextField, 1, 4);
        gp.add(completedDateTextField, 1, 5);
        gp.add(replyDateTextField, 1, 6);

        vBox.getChildren().addAll(gp, new Separator());

        root.setCenter(vBox);

        setContent(root);
        setFitToHeight(true);
        setFitToWidth(true);
    }

    private String getString(ApplicationStage stage, String stageWord) {
        Map<String, TextField> textfieldMap = stageUpdatedInfomation.get(stage);
        return textfieldMap.get(stageWord).getText();
    }

    private void goBackToInternshipView(MouseEvent event) {
        Scene scene = new Scene(new InternshipView(internship), 710, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        ((Node) event.getSource()).getScene().getWindow().hide();
    }

}
