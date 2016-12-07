package main.java;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import main.java.view.ApplicationsView;

/**
 * Created by Haaris on 06/12/2016.
 */
public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Internship Application Organiser");

        Scene scene = new Scene(new ApplicationsView(), 710, 400);
        primaryStage.setMinWidth(550);
        primaryStage.setMinHeight(450);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
