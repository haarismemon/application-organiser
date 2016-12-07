package view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.ApplicationStage;
import model.Company;
import model.Internship;
import model.ParseApplications;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class represents the view to see all the stages of an Internship.
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
//                String s = companyNameTextField.getText()+ "," + roleTextField.getText() + "," + lengthTextField.getText() + "," + locationTextField.getText();

                Internship newInternship = new Internship(companyNameTextField.getText(), roleTextField.getText());
//                internship.getCompany().setName();
//                internship.setRole(roleTextField.getText());
                newInternship.setLength(lengthTextField.getText());
                newInternship.setLocation(locationTextField.getText());

                for(ApplicationStage stage : stageUpdatedInfomation.keySet()) {
                    ApplicationStage newStage = new ApplicationStage(getString(stage, "stageName"));
//                    s += "\\";
//                    Map<String, String> data = stageUpdatedInfomation.get(stage);
//                    s += data.get("stageName");
//                    for(String label : data.keySet()) {
//                        if(label.equals("companyName"))
//                    }
//                    Map<String, TextField> textfieldMap = stageUpdatedInfomation.get(stage);
//                    s += "\\" + stage.getStageName()+","+stage.isCompleted()+","+stage.isWaitingForResponse()+","+stage.isSuccessful()+","
//                            + formatDate(stage.getDateOfStart()) + "," + formatDate(stage.getDateOfCompletion()) + "," + formatDate(stage.getDateOfReply());

//                    String stage_name = getString(stage, "stageName");
                    String is_completed = getString(stage, "isCompleted");
                    String is_waiting = getString(stage, "isWaiting");
                    String is_successful = getString(stage, "isSuccessful");
                    String date_of_start = getString(stage, "startDate");
                    String date_of_completed = getString(stage, "completedDate");
                    String date_of_reply = getString(stage, "replyDate");

                    System.out.println("date_of_reply: " + date_of_reply);
                    
                    
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

//                        System.out.println(newStage.isWaitingForResponse());

                    if(is_successful.equals("true")) {
                        if(!date_of_reply.equals("null")) newStage.setSuccessful(true, date_of_reply);
                        else newStage.setSuccessful(true);
                    }
                    else if(is_successful.equals("false")) newStage.setSuccessful(false, date_of_reply);
//                        else if(is_successful.equals("null")) newStage.setSuccessful(null);

                    if(!date_of_start.equals("null")) newStage.setStartDate(date_of_start);
                    
//                    s += "\\" + getString(newStage, "newStageName")+","+getString(newStage, "isCompleted")+","+getString(newStage, "isWaiting")+","
//                            +getString(newStage, "isSuccessful")+","+ getString(newStage, "startDate")+","+ getString(newStage, "completedDate")+ ","
//                            +getString(newStage, "replyDate");

//                    System.out.println(internship.getCompanyName());
                    System.out.println(newStage.getDateOfReply());

                    newInternship.addStage(newStage);
                }

//                String s = newInternship.getCompanyName()+ ","+newInternship.getRole()+","+newInternship.getLength()+","+newInternship.getLocation();
//
//                for(ApplicationStage newStage : newInternship.getApplicationStages()) {
//                    s += "\\" + newStage.getStageName()+","+newStage.isCompleted()+","+newStage.isWaitingForResponse()+","+newStage.isSuccessful()+","
//                            + ParseApplications.formatDate(newStage.getDateOfStart()) + "," + ParseApplications.formatDate(newStage.getDateOfCompletion()) + ","
//                            + ParseApplications.formatDate(newStage.getDateOfReply());
//                }
//
//                System.out.println(s);
                parseApplications.updateInternshipCache(newInternship);

                goBackToInternshipView(event);
            }
        });

        Button addBtn = new Button("Add Stage");
        addBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Scene scene = new Scene(new AddStageView(parseApplications,  internship), 710, 400);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();

                ((Node) event.getSource()).getScene().getWindow().hide();
            }
        });

        HBox hBox = new HBox(backBtn, saveBtn, addBtn);
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
            Map<String, TextField> textfieldMap = new LinkedHashMap<>();
            GridPane gp = new GridPane();

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

            vBox.getChildren().addAll(gp, new Separator());
        }

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
