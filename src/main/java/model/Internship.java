package main.java.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the Internship that a user has applied to.
 */
public class Internship {

    private Company company;
    private String role;
    private String length;
    private String location;
    private List<ApplicationStage> applicationStages;

    public Internship(String companyName, String role) {
        company = new Company(companyName);
        this.role = role;
        applicationStages = new ArrayList<>();
    }

    public String printCurrentStage() {
        ApplicationStage currentStage = getCurrentStage();
        if (currentStage != null) return currentStage.toString();
        else return "Not yet Applied";
    }

    public ApplicationStage getCurrentStage() {
        if(applicationStages.size() > 0) return applicationStages.get(applicationStages.size() - 1);
        return null;
    }

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

    public boolean removeStage(ApplicationStage stage) {
        return applicationStages.remove(stage);
    }

    public void setLength(String length) {
        this.length = length;
    }

    public void setLocation(String location) {
        this.location = location;
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
