package main.java.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents a particular ApplicationStage in the application process of an Internship.
 */
public class ApplicationStage {

    private String stageName;
    private boolean isCompleted;
    private boolean isWaitingForResponse;
    private Boolean isSuccessful;
    private Date dateOfStart;
    private Date dateOfCompletion;
    private Date dateOfReply;

    public ApplicationStage(String stageName) {
        this.stageName = stageName;
    }

    private Date stringToDate(String dateString) {
        Date date = null;

        if(!dateString.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                date = dateFormat.parse(dateString);
            } catch (ParseException e) {
                System.out.println("The date supplied is incorrect, date is set to null");
            }
        }
        return date;
    }

    public String getCurrentStatus() {
        if(isCompleted) {
            if(isWaitingForResponse) {
                return "Waiting";
            } else {
                if(isSuccessful() != null) {
                    if(isSuccessful) {
                        return "Successful!";
                    } else {
                        return "Failed";
                    }
                } else return "Don't Know";
            }
        } else {
            return "Not Completed";
        }
    }

    public String getStageName() {
        return stageName;
    }

    public Date getDateOfStart() {
        return dateOfStart;
    }

    public Date getDateOfCompletion() {
        return dateOfCompletion;
    }

    public Date getDateOfReply() {
        return dateOfReply;
    }

    public void setStartDate(String dateOfStart) {
        this.dateOfStart = stringToDate(dateOfStart);
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setCompleted(boolean completed, String date) {
        setCompleted(completed);
        this.dateOfCompletion = stringToDate(date);
    }

    public boolean isWaitingForResponse() {
        return isWaitingForResponse;
    }

    public void setWaitingForResponse(boolean waitingForResponse) {
        isWaitingForResponse = waitingForResponse;
    }

    public Boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(Boolean successful) {
        isSuccessful = successful;
        //if the user specifies if stage is successful or not, then they are no longer waiting for a response
        isWaitingForResponse = false;
    }

    public void setSuccessful(Boolean successful, String date) {
        setSuccessful(successful);
        this.dateOfReply = stringToDate(date);
    }

    public String printStage() {
        return "\\"+stageName+","+isCompleted+","+isWaitingForResponse+","+isSuccessful+","+dateOfStart+","+dateOfCompletion+","+dateOfReply;
    }

    @Override
    public String toString() {
        String s = stageName;

        if(isCompleted) {
            if(isWaitingForResponse) {
                s += " - Waiting";
            } else {
                if(isSuccessful() != null) {
                    if(isSuccessful) {
                        s+= " - Successful!";
                    } else {
                        s+= " - Failed";
                    }
                } else s += " - Don't Know";
            }
        } else {
            s += " - Not Completed";
        }

        return s;
    }

    @Override
    public boolean equals(Object obj) {
        return stageName.equals(((ApplicationStage) obj).getStageName());
    }
}
