import model.Internship;
import model.ApplicationStage;
import model.Applications;

/**
 * This class tests the classes for an model.Internship.
 */
public class Main {

    public static void main(String[] args) {
        Applications applications = new Applications();

        Internship accentureTech = new Internship("Accenture", "Technology Industrial Placement");
        accentureTech.setLength("12 Months");
        accentureTech.setLocation("London");
//        accentureTech.setApplied("16/11/16", true);

        ApplicationStage firstStage = new model.ApplicationStage("Online Application");
        firstStage.setCompleted(true);
        firstStage.setSuccessful(true);
        accentureTech.addStage(firstStage);

        ApplicationStage secondStage = new ApplicationStage("Online Test");
        secondStage.setCompleted(true);
        secondStage.setSuccessful(true);
        accentureTech.addStage(secondStage);

        ApplicationStage thirdStage = new ApplicationStage("Online Interview");
        thirdStage.setCompleted(true);
        thirdStage.setWaitingForResponse(true);
        accentureTech.addStage(thirdStage);

        applications.addInternship(accentureTech);

//        System.out.println(accenture.getDateApplied());
//        System.out.println(accentureTech.getApplicationStages());

//        System.out.println("Current ApplicationStage for " + accentureTech.getCompany() + " is " + accentureTech.getCurrentStage());


//        ApplicationsView.launch(ApplicationsView.class, args);

    }

}
