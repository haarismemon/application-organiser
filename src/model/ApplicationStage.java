package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class represents a particular model.ApplicationStage in the application process of an model.Internship.
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            System.out.println("The date supplied is incorrect, date is set to null");
        }

        return date;
    }

    public void setStartDate(Date dateOfStart) {
        this.dateOfStart = dateOfStart;
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

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
        //if the user specifies if stage is successful or not, then they are no longer waiting for a response
        isWaitingForResponse = false;
    }

    public void setSuccessful(boolean successful, String date) {
        setSuccessful(successful);
        this.dateOfReply = stringToDate(date);
    }

    @Override
    public String toString() {
        String s = stageName;

        if(isCompleted) {
            if(isWaitingForResponse) {
                s += " - Waiting for Response";
            } else {
                if(isSuccessful) {
                    s+= " - Successful!";
                } else {
                    s+= " - Failed";
                }
            }
        } else {
            s += " - Not Completed";
        }

        return s;
    }

}
