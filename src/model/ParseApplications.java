package model;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class parses the Application txt file, and also caches it.
 */
public class ParseApplications {

    public Applications applications;

    public ParseApplications() {
        applications = new Applications();
        parse();
    }

    public Applications parse() {
//        Applications applications = new Applications();

        File applicationsFile = new File("applications.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(applicationsFile))) {

            String internshipLine = null;

            while((internshipLine = reader.readLine()) != null) {
                Internship internship = parseInternship(internshipLine);

//                System.out.println(companyName +" " +role + " "+ length + " " + location);

                applications.addInternship(internship);

            }
            reader.close();

        } catch(IOException e) {
            System.out.println("File not found.");
        }


        return applications;
    }

    public void updateInternshipCache(Internship internship) {
//        File applicationsFile = new File("applications.txt");
//        File copyFile = new File("copy.txt");


        try {
            BufferedReader reader = new BufferedReader(new FileReader("applications.txt"));
            PrintWriter writer = new PrintWriter(new FileWriter("copy.txt"));

            String line = null;

            while ((line = reader.readLine()) != null) {
                if(line.contains(internship.getCompanyName() + "," + internship.getRole())) {
                    String s = internship.getCompanyName()+ ","+internship.getRole()+","+internship.getLength()+","+internship.getLocation();

                    for(ApplicationStage stage : internship.getApplicationStages()) {
                        s += "\\" + stage.getStageName()+","+stage.isCompleted()+","+stage.isWaitingForResponse()+","+stage.isSuccessful()+","
                        + formatDate(stage.getDateOfStart()) + "," + formatDate(stage.getDateOfCompletion()) + "," + formatDate(stage.getDateOfReply());
                    }

                    line = s;
                }

                writer.println(line);
            }

            writer.close();
            reader.close();
        } catch(IOException e) {
            System.out.println("File not found.");
        }

        File applicationsFile = new File("applications.txt");
        applicationsFile.delete();
        File copyFile = new File("copy.txt");
        copyFile.renameTo(applicationsFile);

    }

    public static String formatDate(Date date) {
        if(date != null) {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            return df.format(date);
        } else return "null";
    }

    public Applications getApplications() {
        return applications;
    }

    public Internship parseInternship(String internshipLine) {
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

                if(is_completed.equals("true")) {
                    if(!date_of_completed.equals("null")) stage.setCompleted(true, date_of_completed);
                    else stage.setCompleted(true);
                }
                else if(is_completed.equals("false")) stage.setCompleted(false);

                if(is_waiting_for_response.equals("true")) {
//                            System.out.println("is waiting");
                    stage.setWaitingForResponse(true);
                }
                else if(is_waiting_for_response.equals("false")) stage.setWaitingForResponse(false);

//                        System.out.println(stage.isWaitingForResponse());

                if(is_successful.equals("true")) {
                    if(!date_of_reply.equals("null")) stage.setSuccessful(true, date_of_reply);
                    else stage.setSuccessful(true);
                }
                else if(is_successful.equals("false")) stage.setSuccessful(false, date_of_reply);
//                        else if(is_successful.equals("null")) stage.setSuccessful(null);

                if(!date_of_start.equals("null")) stage.setStartDate(date_of_start);

                internship.addStage(stage);
            }
        }

        return internship;
    }

    //    public static void main(String[] args) {
////        for(Internship i : ParseApplications.parse().getApplications()) {
////            System.out.println(i.getCurrentStage());
////        }
//        ParseApplications.parse();
//
//        Internship internship = applications.getApplications().get(22);
//
////        ApplicationStage rollsTest = new ApplicationStage("Online Test");
////        rollsTest.setStartDate("05/12/2016");
////
////        internship.addStage(rollsTest);
//
//        ParseApplications.updateInternshipCache(internship);
//    }

}
