package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class parses the Application txt file, and also caches it.
 */
public class ParseApplications {

    public static Applications parse() {
        Applications applications = new Applications();

        File applicationsFile = new File("applications.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(applicationsFile))) {

            String internshipLine = null;

            while((internshipLine = reader.readLine()) != null) {
                String[] internshipStages = internshipLine.split("\\\\");

                String[] internshipDetails = internshipStages[0].split(",");

                String companyName = internshipDetails[0];
                String role = internshipDetails[1];
                String length = internshipDetails[2];
                String location = internshipDetails[3];

                Internship internship = new Internship(companyName, role);

                if(!length.equals("null")) internship.setLength(length);
                if(!location.equals("null")) internship.setLocation(location);

                if(internshipStages.length > 1) {
                    for(int i = 1; i < internshipStages.length; ++i) {
                        String[] internshipStage = internshipStages[i].split(",");

                        String stage_name = internshipStage[0];
                        String is_completed = internshipStage[1];
                        String is_waiting_for_response = internshipStage[2];
                        String is_successful = internshipStage[3];
                        String date_of_start = internshipStage[4];
                        String date_of_completed = internshipStage[5];
                        String date_of_reply = internshipStage[6];

                        ApplicationStage stage = new ApplicationStage(stage_name);

                        if(is_completed.equals("yes")) {
                            if(!date_of_completed.equals("null")) stage.setCompleted(true, date_of_completed);
                            else stage.setCompleted(true);
                        }
                        else if(is_completed.equals("no")) stage.setCompleted(false);

                        if(is_waiting_for_response.equals("yes")) {
//                            System.out.println("is waiting");
                            stage.setWaitingForResponse(true);
                        }
                        else if(is_waiting_for_response.equals("no")) stage.setWaitingForResponse(false);

//                        System.out.println(stage.isWaitingForResponse());

                        if(is_successful.equals("yes")) {
                            if(!date_of_reply.equals("null")) stage.setSuccessful(true, date_of_reply);
                            else stage.setSuccessful(true);
                        }
                        else if(is_successful.equals("no")) stage.setSuccessful(false);
//                        else if(is_successful.equals("null")) stage.setSuccessful(null);

                        if(!date_of_start.equals("null")) stage.setStartDate(date_of_start);

                        internship.addStage(stage);
                    }
                }

//                System.out.println(companyName +" " +role + " "+ length + " " + location);

                applications.addInternship(internship);

            }
            reader.close();

        } catch(IOException e) {
            System.out.println("File not found.");
        }


        return applications;
    }

    public static void main(String[] args) {
        for(Internship i : ParseApplications.parse().getApplications()) {
            System.out.println(i.getCurrentStage());
        }
    }

}
