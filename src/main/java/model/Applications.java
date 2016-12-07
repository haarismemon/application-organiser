package main.java.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the all the Internships that the user has applied to
 */
public class Applications {

    private List<Internship> applications;

    public Applications() {
        applications = new ArrayList<>();
    }

    public void addInternship(Internship addIntership) {
        applications.add(addIntership);
    }

    public List<Internship> getApplications() {
        return applications;
    }

}
