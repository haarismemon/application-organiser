package main.java.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import main.java.controller.InternshipController;
import main.java.model.ApplicationStage;
import main.java.model.ParseApplications;
import main.java.model.Internship;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class represents the java.view to see all the stages of an Internship.
 */
public class EditInternshipView extends ScrollPane {

    private ParseApplications parseApplications;
    private Internship internship;

    private TextField companyNameTextField;
    private TextField roleTextField;
    private TextField lengthTextField;
    private TextField locationTextField;

    private Map<ApplicationStage, Map<String, TextField>> stageUpdatedInfomation;

    public EditInternshipView(ParseApplications parseApplications, Internship internship) {
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
        backBtn.setOnMouseClicked(new InternshipController(internship));

        Button saveBtn = new Button("Save");
        saveBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Internship newInternship = new Internship(companyNameTextField.getText(), roleTextField.getText());
                newInternship.setLength(lengthTextField.getText());
                newInternship.setLocation(locationTextField.getText());

                for(ApplicationStage stage : stageUpdatedInfomation.keySet()) {

                    String[] stageArray = {getString(stage, "stageName"), getString(stage, "isCompleted"),
                            getString(stage, "isWaiting"), getString(stage, "isSuccessful"), getString(stage, "startDate"),
                            getString(stage, "completedDate"), getString(stage, "replyDate") };

                    ApplicationStage newStage = parseApplications.makeStage(stageArray);

                    newInternship.addStage(newStage);
                }

                if(internship.getCompanyName().equals("") && internship.getRole().equals("")) {
                    parseApplications.updateInternshipCache(newInternship.getCompanyName(), newInternship.getRole(), newInternship);
                } else {
                    parseApplications.updateInternshipCache(internship.getCompanyName(), internship.getRole(), newInternship);
                }
                internship = newInternship;

                Scene scene = new Scene(new InternshipView(internship), 710, 400);
                Stage stage = new Stage();
                stage.setTitle("Internship Application Organiser");
                stage.setScene(scene);
                stage.show();

                ((Node) event.getSource()).getScene().getWindow().hide();
            }
        });

        Button addBtn = new Button("Add Stage");
        Button deleteBtn = new Button("Delete");
        HBox hBox = new HBox(backBtn, saveBtn, addBtn, deleteBtn);
        vBox.getChildren().add(hBox);

        GridPane companyEditGP = new GridPane();
        companyEditGP.add(new Label("Company Name: "), 0,0);
        companyEditGP.add(companyNameTextField = new TextField(internship.getCompany().getName()), 1,0);
        companyEditGP.add(new Label("Role: "), 0,1);
        companyEditGP.add(roleTextField = new TextField(internship.getRole()), 1,1);
        companyEditGP.add(new Label("Length: "), 0,2);
        companyEditGP.add(lengthTextField = new TextField(internship.getLength()), 1,2);
        companyEditGP.add(new Label("Location: "), 0,3);
        companyEditGP.add(locationTextField = new TextField(internship.getLocation()), 1,3);
        vBox.getChildren().addAll(companyEditGP, new Separator());

        for(ApplicationStage stage : internship.getApplicationStages()) {
            addStageToVBox(stage, vBox);
        }

        addBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addStageToVBox(new ApplicationStage(""), vBox);
            }
        });

        deleteBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                parseApplications.deleteInternship(internship);

                Scene scene = new Scene(new ApplicationsView(), 710, 400);
                Stage stage = new Stage();
                stage.setTitle("Internship Application Organiser");
                stage.setScene(scene);
                stage.show();

                ((Node) event.getSource()).getScene().getWindow().hide();
            }
        });

        root.setCenter(vBox);

        setContent(root);
        setFitToHeight(true);
        setFitToWidth(true);
    }

    private String getString(ApplicationStage stage, String stageWord) {
        Map<String, TextField> textfieldMap = stageUpdatedInfomation.get(stage);
        return textfieldMap.get(stageWord).getText();
    }

    private void addStageToVBox(ApplicationStage stage, VBox vBox) {
        Map<String, TextField> textfieldMap = new LinkedHashMap<>();
        GridPane gp = new GridPane();
        Pane separatorPane = new Pane();

        TextField stageNameTextField = new TextField(stage.getStageName());
        textfieldMap.put("stageName", stageNameTextField);
        TextField isCompletedTextField = new TextField("" + stage.isCompleted());
        textfieldMap.put("isCompleted", isCompletedTextField);
        TextField isWaitingTextField = new TextField("" + stage.isWaitingForResponse());
        textfieldMap.put("isWaiting", isWaitingTextField);
        TextField isSuccessfulTextField = new TextField("" + stage.isSuccessful());
        textfieldMap.put("isSuccessful", isSuccessfulTextField);
        TextField startDateTextField = new TextField("" + ParseApplications.formatDate(stage.getDateOfStart()));
        textfieldMap.put("startDate", startDateTextField);
        TextField completedDateTextField = new TextField("" + ParseApplications.formatDate(stage.getDateOfCompletion()));
        textfieldMap.put("completedDate", completedDateTextField);
        TextField replyDateTextField = new TextField("" + ParseApplications.formatDate(stage.getDateOfReply()));
        textfieldMap.put("replyDate", replyDateTextField);

        int i  = 0;
        for (String stageWord : textfieldMap.keySet()) {
            gp.add(textfieldMap.get(stageWord), 1, i);
            ++i;
        }

        stageUpdatedInfomation.put(stage, textfieldMap);

        gp.add(new Label("Stage Name: "), 0, 0);
        gp.add(new Label("Completed?: "), 0, 1);
        gp.add(new Label("Waiting for Response?: "), 0, 2);
        gp.add(new Label("Successful?: "), 0, 3);
        gp.add(new Label("Date of start: "), 0, 4);
        gp.add(new Label("Date of completion: "), 0, 5);
        gp.add(new Label("Date of reply: "), 0, 6);

        Button removeBtn = new Button("Remove");
        gp.add(removeBtn, 2, 0);

        separatorPane.getChildren().add(new Separator());

        removeBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                internship.removeStage(stage);
                gp.getChildren().clear();
                separatorPane.getChildren().clear();
            }
        });

        vBox.getChildren().addAll(gp, separatorPane);
    }

}
