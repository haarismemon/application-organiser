package main.java.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.List;
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
    private TextField deadlineDateTextField;
    private TextField linkTextField;
    private TextArea descriptionTextField;

    private Map<ApplicationStage, Map<String, Object>> stageUpdatedInfomation;

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

        Button addBtn = new Button("Add Stage");
        Button deleteBtn = new Button("Delete");
        HBox hBox = new HBox(backBtn, saveBtn, addBtn, deleteBtn);
        hBox.setSpacing(15);
        vBox.getChildren().add(hBox);

        GridPane companyEditGP = new GridPane();
        companyEditGP.setHgap(50);

        companyEditGP.add(new Label("Company Name: "), 0,0);
        companyEditGP.add(companyNameTextField = new TextField(internship.getCompany().getName()), 1,0);
        companyNameTextField.setPrefWidth(300);
        companyEditGP.add(new Label("Role: "), 0,1);
        companyEditGP.add(roleTextField = new TextField(internship.getRole()), 1,1);
        companyEditGP.add(new Label("Length: "), 0,2);
        companyEditGP.add(lengthTextField = new TextField(internship.getLength()), 1,2);
        companyEditGP.add(new Label("Location: "), 0,3);
        companyEditGP.add(locationTextField = new TextField(internship.getLocation()), 1,3);
        companyEditGP.add(new Label("Due Date: "), 0,4);
        companyEditGP.add(deadlineDateTextField = new TextField(ParseApplications.formatDate(internship.getDeadlineDate())), 1,4);
        companyEditGP.add(new Label("Link: "), 0,5);
        companyEditGP.add(linkTextField = new TextField(ParseApplications.formatDate(internship.getDeadlineDate())), 1,5);
        companyEditGP.add(new Label("Description: "), 0,6);
        companyEditGP.add(descriptionTextField = new TextArea(ParseApplications.formatDate(internship.getDeadlineDate())), 1,6);
        descriptionTextField.setPrefSize(500, 50);
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

        saveBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Internship newInternship = new Internship(companyNameTextField.getText(), roleTextField.getText());
                newInternship.setLength(lengthTextField.getText());
                newInternship.setLocation(locationTextField.getText());
                newInternship.setDeadlineDate(deadlineDateTextField.getText());
                newInternship.setLocation(linkTextField.getText());
                newInternship.setLocation(descriptionTextField.getText());

                for(ApplicationStage stage : stageUpdatedInfomation.keySet()) {

                    String start_date = getString(stage, "startDate");
                    if(start_date == "") start_date = "null";
                    String completed_date = getString(stage, "completedDate");
                    if(completed_date == "") completed_date = "null";
                    String reply_date = getString(stage, "replyDate");
                    if(reply_date == "") reply_date = "null";

                    String[] stageArray = {getString(stage, "stageName"), getString(stage, "isCompleted"),
                            getString(stage, "isWaiting"), getString(stage, "isSuccessful"), start_date,
                            completed_date, reply_date };

//                    for(String s : stageArray) System.out.println(s);

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
        Map<String, Object> textfieldAndRadioMap = stageUpdatedInfomation.get(stage);
        Object inputFieldObject = textfieldAndRadioMap.get(stageWord);
        if (inputFieldObject instanceof TextField){
            return ((TextField) inputFieldObject).getText();
        } else if(inputFieldObject instanceof ToggleGroup){
            return "" + (Boolean)((ToggleGroup) inputFieldObject).getSelectedToggle().getUserData();
        }
        return null;
    }

    private void addStageToVBox(ApplicationStage stage, VBox vBox) {
        Map<String, Object> textfieldAndRadioMap = new LinkedHashMap<>();
        GridPane gp = new GridPane();
        gp.setHgap(15);
        gp.setVgap(10);
        Pane separatorPane = new Pane();

        TextField stageNameTextField = new TextField(stage.getStageName());
        textfieldAndRadioMap.put("stageName", stageNameTextField);
        gp.add(stageNameTextField, 1, 0);


        ToggleGroup completedGroup = new ToggleGroup();
        RadioButton yesCompletedRB = new RadioButton("Yes");
        yesCompletedRB.setToggleGroup(completedGroup);
        yesCompletedRB.setUserData(true);
        RadioButton noCompletedRB = new RadioButton("No");
        noCompletedRB.setToggleGroup(completedGroup);
        noCompletedRB.setUserData(false);
        HBox completedHBox = new HBox();
        completedHBox.setSpacing(20);
        completedHBox.getChildren().addAll(yesCompletedRB, noCompletedRB);
        gp.add(completedHBox, 1, 1);
        textfieldAndRadioMap.put("isCompleted", completedGroup);

        ToggleGroup waitingGroup = new ToggleGroup();
        RadioButton yesWaitingRB = new RadioButton("Yes");
        yesWaitingRB.setUserData(true);
        yesWaitingRB.setToggleGroup(waitingGroup);
        RadioButton noWaitingRB = new RadioButton("No");
        noWaitingRB.setUserData(false);
        noWaitingRB.setToggleGroup(waitingGroup);
        HBox waitingHBox = new HBox();
        waitingHBox.setSpacing(20);
        waitingHBox.getChildren().addAll(yesWaitingRB, noWaitingRB);
        gp.add(waitingHBox, 1, 2);
        textfieldAndRadioMap.put("isWaiting", waitingGroup);

        ToggleGroup successfulGroup = new ToggleGroup();
        RadioButton yesSuccessfulRB = new RadioButton("Yes");
        yesSuccessfulRB.setUserData(true);
        yesSuccessfulRB.setToggleGroup(successfulGroup);
        RadioButton noSuccessfulRB = new RadioButton("No");
        noSuccessfulRB.setUserData(false);
        noSuccessfulRB.setToggleGroup(successfulGroup);
        RadioButton nullSuccessfulRB = new RadioButton("No Idea");
        nullSuccessfulRB.setUserData(null);
        nullSuccessfulRB.setToggleGroup(successfulGroup);
        HBox successfulHBox = new HBox();
        successfulHBox.setSpacing(20);
        successfulHBox.getChildren().addAll(yesSuccessfulRB, noSuccessfulRB, nullSuccessfulRB);
        gp.add(successfulHBox, 1, 3);
        textfieldAndRadioMap.put("isSuccessful", successfulGroup);

        TextField startDateTextField = new TextField();
        startDateTextField.setPromptText("dd/mm/yyyy");
        if(stage.getDateOfStart() != null) startDateTextField.setText("" + ParseApplications.formatDate(stage.getDateOfStart()));
        textfieldAndRadioMap.put("startDate", startDateTextField);
        gp.add(startDateTextField, 1, 4);
        TextField completedDateTextField = new TextField();
        completedDateTextField.setPromptText("dd/mm/yyyy");
        if(stage.getDateOfCompletion() != null) completedDateTextField.setText("" + ParseApplications.formatDate(stage.getDateOfCompletion()));
        textfieldAndRadioMap.put("completedDate", completedDateTextField);
        gp.add(completedDateTextField, 1, 5);
        TextField replyDateTextField = new TextField();
        replyDateTextField.setPromptText("dd/mm/yyyy");
        if(stage.getDateOfReply() != null) replyDateTextField.setText("" + ParseApplications.formatDate(stage.getDateOfReply()));
        textfieldAndRadioMap.put("replyDate", replyDateTextField);
        gp.add(replyDateTextField, 1, 6);

//        int i  = 0;
//        for (String stageWord : textfieldMap.keySet()) {
//            gp.add(textfieldMap.get(stageWord), 1, i);
//            ++i;
//        }

        stageUpdatedInfomation.put(stage, textfieldAndRadioMap);

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
                stageUpdatedInfomation.remove(stage);
                gp.getChildren().clear();
                separatorPane.getChildren().clear();
            }
        });

        completedGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(completedGroup.getSelectedToggle() != null && successfulGroup.getSelectedToggle() != null) {
                    if((boolean) completedGroup.getSelectedToggle().getUserData()) {
                        completedDateTextField.setDisable(false);
                        if(successfulGroup.getSelectedToggle().getUserData() == null) {
                            replyDateTextField.setDisable(true);
                        } else {
                            replyDateTextField.setDisable(false);
                        }
                    } else {
                        completedDateTextField.setDisable(true);
                        replyDateTextField.setDisable(true);
                        waitingGroup.selectToggle(switchToggles(false, waitingGroup));
                        successfulGroup.selectToggle(switchToggles(null, successfulGroup));
                        completedGroup.selectToggle(switchToggles(false, completedGroup));

                    }
                }
            }
        });

        waitingGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(waitingGroup.getSelectedToggle() != null && completedGroup.getSelectedToggle() != null) {
                    if((boolean) completedGroup.getSelectedToggle().getUserData()) {
                        if((boolean) waitingGroup.getSelectedToggle().getUserData()) {
                            replyDateTextField.setDisable(true);
                        } else {
                            replyDateTextField.setDisable(false);
                        }
                        completedDateTextField.setDisable(false);
                    } else {
                        completedDateTextField.setDisable(true);
                        replyDateTextField.setDisable(true);
                    }
                }
            }
        });

        successfulGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(successfulGroup.getSelectedToggle() != null && completedGroup
                        .getSelectedToggle() != null) {
                    if((successfulGroup.getSelectedToggle().getUserData()) == null && ((boolean) completedGroup.getSelectedToggle().getUserData())){
//                        completedGroup.selectToggle(switchToggles(false, completedGroup));
                        waitingGroup.selectToggle(switchToggles(true, waitingGroup));
                    } else {
                        completedGroup.selectToggle(switchToggles(true, completedGroup));
                        waitingGroup.selectToggle(switchToggles(false, waitingGroup));
                    }
                }
            }
        });

        if(stage.isSuccessful() == null) nullSuccessfulRB.setSelected(true);
        else if(stage.isSuccessful()) yesSuccessfulRB.setSelected(true);
        else noSuccessfulRB.setSelected(true);

        if(stage.isWaitingForResponse()) yesWaitingRB.setSelected(true);
        else noWaitingRB.setSelected(true);

        if(stage.isCompleted()) yesCompletedRB.setSelected(true);
        else noCompletedRB.setSelected(true);

        vBox.getChildren().addAll(gp, separatorPane);
    }

    private Toggle switchToggles(Boolean booleanUserData, ToggleGroup toggleGroup) {
        for(Toggle toggle : toggleGroup.getToggles()) {
            if((toggle.getUserData()) == booleanUserData) {
                return toggle;
            }
        }
        return null;
    }

}
