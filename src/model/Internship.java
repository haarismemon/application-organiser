package model;

import java.text.DateFormat;
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
//    private Date dateApplied;
    private String length;
    private String location;
    private List<ApplicationStage> applicationStages;

    public Internship(String companyName, String role) {
        company = new Company(companyName);
        this.role = role;
        applicationStages = new ArrayList<>();
    }

    public String printCurrentStage() {
//        String s = company.getName() + ": " + role + " - Current Stage: ";

        ApplicationStage currentStage = getCurrentStage();
        if (currentStage != null) return currentStage.toString();
        else return "Not yet Applied";

//        s+= " - Applied On: ";
//        if(dateApplied != null) {
//            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
//            s += df.format(dateApplied);
//        }
//        else s+= "Not yet";

//        return s;
    }

    public ApplicationStage getCurrentStage() {
        if(applicationStages.size() > 0) return applicationStages.get(applicationStages.size() - 1);
        return null;
    }

//    public ApplicationStage setApplied(String dateApplied) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//        try{
//            this.dateApplied = dateFormat.parse(dateApplied);
//        } catch(ParseException e) {
//            System.out.println("The date supplied is incorrect, date is set to null");
//        }
//
//        ApplicationStage onlineApplication = new ApplicationStage("Online Application");
//        //if the user has applied, then online application is completed
//        onlineApplication.setCompleted(true, dateApplied);

//        //if the user has applied, and not specified the isSuccessful then they are waiting for a response.
//        onlineApplication.setWaitingForResponse(true);
//        addStage(onlineApplication);
//
//        return onlineApplication;
//    }
//
//    public void setApplied(String dateApplied, boolean isSuccessful) {
//        ApplicationStage onlineApplication = setApplied(dateApplied);
//        onlineApplication.setSuccessful(isSuccessful);
//    }

    public Company getCompany() {
        return company;
    }

    public String getCompanyName() {
        return company.getName();
    }

    public String getRole() {
        return role;
    }

    public void addStage(ApplicationStage stage) {
        applicationStages.add(stage);
    }

//    public Date getDateApplied() {
//        return dateApplied;
//    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<ApplicationStage> getApplicationStages() {
        return applicationStages;
    }

    public String getLength() {
        return length;
    }

    public String getLocation() {
        return location;
    }
}
