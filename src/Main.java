/**
 * This class tests the classes for an Internship.
 */
public class Main {

    public static void main(String[] args) {
        Internship accentureTech = new Internship("Accenture", "Technology Industrial Placement");
        accentureTech.setLength("12 Months");
        accentureTech.setLocation("London");
        accentureTech.setApplied("16/11/16", true);

//        Stage firstStage = new Stage("Online Application");
//        firstStage.setCompleted(true);
//        firstStage.setSuccessful(true);
//        accentureTech.addStage(firstStage);

        Stage secondStage = new Stage("Online Test");
        secondStage.setCompleted(true);
        secondStage.setSuccessful(true);
        accentureTech.addStage(secondStage);

        Stage thirdStage = new Stage("Online Interview");
        thirdStage.setCompleted(true);
        thirdStage.setWaitingForResponse(true);
        accentureTech.addStage(thirdStage);

//        System.out.println(accenture.getDateApplied());
        System.out.println(accentureTech.getApplicationStages());

        System.out.println("Current Stage for " + accentureTech.getCompany() + " is " + accentureTech.getCurrentStage());
    }

}
