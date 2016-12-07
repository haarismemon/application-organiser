package main.java.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.java.controller.ApplicationController;
import main.java.controller.EditInternshipController;
import main.java.controller.StageController;
import main.java.model.ApplicationStage;
import main.java.model.ParseApplications;
import main.java.model.Internship;


/**
 * This class represents the java.view to see all the stages of an Internship.
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
        backBtn.setOnMouseClicked(new ApplicationController());

        Button editBtn = new Button("Edit Internship");
        editBtn.setOnMouseClicked(new EditInternshipController(parseApplications, internship));

        HBox hBox = new HBox(backBtn, editBtn);
        hBox.setSpacing(15);
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

            bp.setOnMouseClicked(new StageController(internship, stage));
            vBox.getChildren().add(new Separator());
        }

        root.setCenter(vBox);

        setContent(root);
        setFitToHeight(true);
        setFitToWidth(true);
    }

}
