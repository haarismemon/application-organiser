package model;

import model.Internship;

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

    public void removeInternship(Internship removeIntership) {
        int index = applications.indexOf(removeIntership);
        if(index != -1) {
            applications.remove(applications.indexOf(removeIntership));
        } else {
            System.out.println(removeIntership + " not in Applications list");
        }
    }

    public List<Internship> getApplications() {
        return applications;
    }

}
