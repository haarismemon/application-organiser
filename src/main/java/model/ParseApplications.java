package main.java.model;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class parses the Application txt file, and also caches it.
 */
public class ParseApplications {

    private Applications applications;
    private String applicationFileName;
    private String copyFileName;

    public ParseApplications() {
        applications = new Applications();
        applicationFileName = "applications.txt";
        copyFileName = "copy.txt";
        parse();
    }

    public Applications parse() {
        File applicationsFile = new File(applicationFileName);

        try (BufferedReader reader = new BufferedReader(new FileReader(applicationsFile))) {
            String internshipLine = null;

            while((internshipLine = reader.readLine()) != null) {
                Internship internship = parseInternship(internshipLine);
                applications.addInternship(internship);
            }
            reader.close();

        } catch(IOException e) {
            try {
                PrintWriter writer = new PrintWriter(applicationFileName);
                writer.close();
            } catch (FileNotFoundException e1) {
                System.out.println("File not found.");
//                e1.printStackTrace();
            }
        }

        return applications;
    }

    public void updateInternshipCache(String companyName, String role, Internship internship) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(applicationFileName));

            String[] applicationFileNameArray = applicationFileName.split("applications.txt");
            PrintWriter writer = new PrintWriter(new FileWriter(copyFileName));

            String line = null;
            String internshipLine = makeInternshipLine(internship);

            boolean foundInternship = false;

            while ((line = reader.readLine()) != null) {
                if(line.contains(companyName + "," + role)) {
                    line = internshipLine;
                    foundInternship = true;
                }

                writer.println(line);
            }

            if(!foundInternship) {
                writer.println(internshipLine);
            }

            writer.close();
            reader.close();

            File applicationsFile = new File(applicationFileName);
            applicationsFile.delete();
            File copyFile = new File(copyFileName);
            copyFile.renameTo(applicationsFile);
        } catch(IOException e) {
            System.out.println("File not found when updating Internship.");
        }


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

                ApplicationStage stage = makeStage(internshipStage);

                internship.addStage(stage);
            }
        }

        return internship;
    }

    private String makeInternshipLine(Internship internship) {
        String s = internship.getCompanyName()+ ","+internship.getRole()+","+internship.getLength()+","+internship.getLocation();

        for(ApplicationStage stage : internship.getApplicationStages()) {
            s += "\\" + stage.getStageName()+","+stage.isCompleted()+","+stage.isWaitingForResponse()+","+stage.isSuccessful()+","
                    + formatDate(stage.getDateOfStart()) + "," + formatDate(stage.getDateOfCompletion()) + "," + formatDate(stage.getDateOfReply());
        }

        return s;
    }

    public ApplicationStage makeStage(String[] stageArray) {
        String stage_name = stageArray[0];
        String is_completed = stageArray[1];
        String is_waiting_for_response = stageArray[2];
        String is_successful = stageArray[3];
        String date_of_start = stageArray[4];
        String date_of_completed = stageArray[5];
        String date_of_reply = stageArray[6];

        ApplicationStage stage = new ApplicationStage(stage_name);

        if(is_completed.equals("true")) {
            if(!date_of_completed.equals("null")) stage.setCompleted(true, date_of_completed);
            else stage.setCompleted(true);
        }
        else if(is_completed.equals("false")) stage.setCompleted(false);


        if(is_waiting_for_response.equals("true")) {
            stage.setWaitingForResponse(true);
        }
        else if(is_waiting_for_response.equals("false")) stage.setWaitingForResponse(false);


        if(is_successful.equals("true")) {
            if(!date_of_reply.equals("null")) stage.setSuccessful(true, date_of_reply);
            else stage.setSuccessful(true);
        }
        else if(is_successful.equals("false")) stage.setSuccessful(false, date_of_reply);

        if(!date_of_start.equals("null")) stage.setStartDate(date_of_start);

        return stage;
    }

    public void deleteInternship(Internship internship) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(applicationFileName));

            PrintWriter writer = new PrintWriter(new FileWriter(copyFileName));

            String line = null;

            while ((line = reader.readLine()) != null) {
                if(line.contains(internship.getCompanyName() + "," + internship.getRole())) {
                    continue;
                } else {
                    writer.println(line);
                }
            }

            writer.close();
            reader.close();

            File applicationsFile = new File(applicationFileName);
            applicationsFile.delete();
            File copyFile = new File(copyFileName);
            copyFile.renameTo(applicationsFile);
        } catch(IOException e) {
            System.out.println("File not found when deleting Internship.");
        }

    }

}
