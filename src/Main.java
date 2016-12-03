/**
 * This class tests the classes for an Internship.
 */
public class Main {

    public static void main(String[] args) {
        Internship accenture = new Internship("Accenture", "Technology Industrial Placement", "16/11/16");
        accenture.setLength("12 Months");
        accenture.setLocation("London");

        Stage firstStage = new Stage("Online Application");
        firstStage.setCompleted(true);
        firstStage.setSuccessful(true);
        accenture.addStage(firstStage);

        Stage secondStage = new Stage("Online Test");
        secondStage.setCompleted(true);
        secondStage.setSuccessful(true);
        accenture.addStage(secondStage);

        Stage thirdStage = new Stage("Online Interview");
        thirdStage.setCompleted(true);
        thirdStage.setWaitingForResponse(true);
        accenture.addStage(thirdStage);

//        System.out.println(accenture.getDateApplied());
        System.out.println(accenture.getApplicationStages());
    }

}
