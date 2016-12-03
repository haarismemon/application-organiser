package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class represents the model.Internship that a user has applied to.
 */
public class Internship {

    private Company company;
    private String role;
    private Date dateApplied;
    private String length;
    private String location;
    private List<Stage> applicationStages;

    public Internship(String companyName, String role) {
        company = new Company(companyName);
        this.role = role;
        applicationStages = new ArrayList<>();
    }

    public Stage getCurrentStage() {
        for(Stage stage : applicationStages) {
            if(stage.isWaitingForResponse()) return stage;
        }

        return null;
    }

    public Stage setApplied(String dateApplied) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try{
            this.dateApplied = dateFormat.parse(dateApplied);
        } catch(ParseException e) {
            System.out.println("The date supplied is incorrect, date is set to null");
        }

        Stage onlineApplication = new Stage("Online Application");
        //if the user has applied, then online application is completed
        onlineApplication.setCompleted(true, dateApplied);
        //if the user has applied, and not specified the isSuccessful then they are waiting for a response.
        onlineApplication.setWaitingForResponse(true);
        addStage(onlineApplication);

        return onlineApplication;
    }

    public void setApplied(String dateApplied, boolean isSuccessful) {
        Stage onlineApplication = setApplied(dateApplied);
        onlineApplication.setSuccessful(isSuccessful);
    }

    public Company getCompany() {
        return company;
    }

    public void addStage(Stage stage) {
        applicationStages.add(stage);
    }

    public Date getDateApplied() {
        return dateApplied;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Stage> getApplicationStages() {
        return applicationStages;
    }

}
