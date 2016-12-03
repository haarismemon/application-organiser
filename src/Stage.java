/**
 * This class represents a particular Stage in the application process of an Internship.
 */
public class Stage {

    private String stageName;
    private boolean isCompleted;
    private boolean isWaitingForResponse;
    private boolean isSuccessful;

    public Stage(String stageName) {
        this.stageName = stageName;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setWaitingForResponse(boolean waitingForResponse) {
        isWaitingForResponse = waitingForResponse;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
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
