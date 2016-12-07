package main.java.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.java.controller.EditInternshipController;
import main.java.controller.InternshipController;
import main.java.model.ApplicationStage;
import main.java.model.Applications;
import main.java.model.ParseApplications;
import main.java.model.Internship;

/**
 * This class represents the Applications window that allows user to java.view all the Internships applied to
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

        Label requireActionLabel = new Label("Requires Action");
        requireActionLabel.setAlignment(Pos.CENTER);
        requireActionLabel.setFont(new Font(new Label().getFont().getStyle(), 20));
        vBox.getChildren().add(requireActionLabel);

        //add internships that require action
        for(Internship internship : applications.getApplications()) {
            ApplicationStage currentStage = internship.getCurrentStage();
            if((currentStage != null) && (!currentStage.isCompleted())) {
                addInternshipsToVBox(internship, vBox);
            }
        }

        Label applyToLabel = new Label("Apply To");
        applyToLabel.setAlignment(Pos.CENTER);
        applyToLabel.setFont(new Font(new Label().getFont().getStyle(), 20));
        vBox.getChildren().add(applyToLabel);

        //add internships that need to apply to
        for(Internship internship : applications.getApplications()) {
            if(internship.getCurrentStage() == null) {
                addInternshipsToVBox(internship, vBox);
            }
        }

        Label allLabel = new Label("All Applications");
        allLabel.setAlignment(Pos.CENTER);
        allLabel.setFont(new Font(new Label().getFont().getStyle(), 20));

        Button addBtn = new Button("Add Internship");
        addBtn.setOnMouseClicked(new EditInternshipController(parseApplications, new Internship("", "")));

        BorderPane allBP = new BorderPane();
        allBP.setLeft(allLabel);
        allBP.setRight(addBtn);
        vBox.getChildren().add(allBP);

        for(Internship internship : applications.getApplications()) {
            addInternshipsToVBox(internship, vBox);
        }

        root.setCenter(vBox);

        setContent(root);
        setFitToHeight(true);
        setFitToWidth(true);

    }

    private void addInternshipsToVBox(Internship internship, VBox vBox) {
        BorderPane bp = new BorderPane();
        HBox hBox = new HBox();
        Label companyLabel = new Label(internship.getCompanyName());
        companyLabel.setMinWidth(150);
        hBox.getChildren().add(companyLabel);
        Label roleLabel = new Label(internship.getRole());
        hBox.getChildren().add(roleLabel);
        bp.setCenter(hBox);
        bp.setRight(new Label(internship.printCurrentStage()));

        bp.setOnMouseClicked(new InternshipController(internship));

        vBox.getChildren().add(bp);
        vBox.getChildren().add(new Separator());
    }

}
