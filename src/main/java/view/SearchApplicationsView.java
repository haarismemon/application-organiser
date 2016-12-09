package main.java.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import main.java.controller.ApplicationController;
import main.java.controller.InternshipController;
import main.java.model.Applications;
import main.java.model.Internship;
import main.java.model.ParseApplications;

import java.util.List;

/**
 * This class represents the Applications window that allows user to java.view all the Internships applied to
 */
public class SearchApplicationsView extends ScrollPane {

    public SearchApplicationsView() {
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
        TextField searchTF = new TextField();
        searchTF.setPromptText("search");
        searchTF.setMaxSize(250, 20);
        Button backBtn = new Button("Back");
        backBtn.setOnMouseClicked(new ApplicationController());

        BorderPane topBP = new BorderPane();
        topBP.setLeft(heading);

        BorderPane searchAndBackBP = new BorderPane();
        searchAndBackBP.setCenter(searchTF);
        searchAndBackBP.setRight(backBtn);
        topBP.setCenter(searchAndBackBP);
        vBox.getChildren().add(topBP);

        VBox searchResults = new VBox();
        searchResults.setSpacing(7);
        vBox.getChildren().add(searchResults);

        searchTF.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

//                if (event.getCode().equals(KeyCode.ENTER)) {
                    searchResults.getChildren().clear();

                    List<Internship> searchedIntership = applications.search(searchTF.getText());
                    for(Internship internship : searchedIntership) {
                        addInternshipsToVBox(internship, searchResults);
                    }
//                }
            }
        });

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
