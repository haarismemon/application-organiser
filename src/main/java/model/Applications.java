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

    public List<Internship>  search(String searchQuery) {
        List<Internship> internshipsFound = new ArrayList<>();
        for(Internship internship : applications) {
            String joinString = internship.getCompanyName()+" "+internship.getRole();
            for(int i = 0; i < internship.getApplicationStages().size(); ++i) {
                ApplicationStage stage = internship.getApplicationStages().get(i);
                if(i == internship.getApplicationStages().size() - 1) {
                    joinString += " " + stage.toString();
                } else {
                    joinString += " " + stage.getStageName();
                }
            }
            if(joinString.toLowerCase().contains(searchQuery.toLowerCase())) {
                internshipsFound.add(internship);
            }
        }
        return internshipsFound;
    }

    public List<Internship> getApplications() {
        return applications;
    }

}
