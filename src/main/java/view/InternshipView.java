package main.java.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import main.java.controller.ApplicationController;
import main.java.controller.EditInternshipController;
import main.java.controller.StageController;
import main.java.model.ApplicationStage;
import main.java.model.ParseApplications;
import main.java.model.Internship;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


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

        Font companyFont = new Font("Regular", 18);

        GridPane companyInfoGP = new GridPane();
        companyInfoGP.setPadding(new Insets(10, 0, 0, 0));
        companyInfoGP.setHgap(50);

        Label companyNameTitle = new Label("Company Name: ");
        companyInfoGP.add(companyNameTitle, 0,0);
        Label companyNameLabel = new Label(internship.getCompany().getName());
        companyInfoGP.add(companyNameLabel, 1,0);
        Label roleTitle = new Label("Role: ");
        companyInfoGP.add(roleTitle, 0,1);
        Label roleLabel = new Label(internship.getRole());
        companyInfoGP.add(roleLabel, 1,1);
        Label lengthTitle = new Label("Length: ");
        companyInfoGP.add(lengthTitle, 0,2);
        Label lengthLabel = new Label(internship.getLength());
        companyInfoGP.add(lengthLabel, 1,2);
        Label locationTitle = new Label("Location: ");
        companyInfoGP.add(locationTitle, 0,3);
        Label locationLabel = new Label(internship.getLocation());
        companyInfoGP.add(locationLabel, 1,3);

        Label deadlineTitle = new Label("Deadline Date: ");
        companyInfoGP.add(deadlineTitle, 0,4);
        Label deadlineLabel = new Label(ParseApplications.formatDate(internship.getDeadlineDate()));
        companyInfoGP.add(deadlineLabel, 1,4);


        WebView browser = new WebView();
        WebEngine webEngine = browser.getEngine();
        Label linkTitle = new Label("Link: ");
        companyInfoGP.add(linkTitle, 0,5);
        Hyperlink linkLabel = new Hyperlink("Click here!");
        companyInfoGP.add(linkLabel, 1,5);
        linkLabel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    try {
                        desktop.browse(new URI(internship.getLink()));
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        Label descriptionTitle = new Label("Description: ");
        companyInfoGP.add(descriptionTitle, 0,6);
        String description = "-";
        if(internship.getDescription() != null) description = internship.getDescription().replace("*~+", ",");
        Label descriptionLabel = new Label(description);
        companyInfoGP.add(descriptionLabel, 1,6);
        vBox.getChildren().addAll(companyInfoGP, new Separator());

        companyNameTitle.setFont(companyFont);
        roleTitle.setFont(companyFont);
        lengthTitle.setFont(companyFont);
        locationTitle.setFont(companyFont);
        locationLabel.setFont(companyFont);
        companyNameLabel.setFont(companyFont);
        roleLabel.setFont(companyFont);
        lengthLabel.setFont(companyFont);
        deadlineTitle.setFont(companyFont);
        deadlineLabel.setFont(companyFont);
        linkTitle.setFont(companyFont);
        linkLabel.setFont(companyFont);
        descriptionTitle.setFont(companyFont);
        descriptionLabel.setFont(companyFont);

        for(ApplicationStage stage : internship.getApplicationStages()) {
            Font stageFont = new Font("Regular", 14);

            BorderPane bp = new BorderPane();
            Label stageNameLabel = new Label(stage.getStageName());
            Label statusLabel = new Label(stage.getCurrentStatus());
            stageNameLabel.setFont(stageFont);
            statusLabel.setFont(stageFont);
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
