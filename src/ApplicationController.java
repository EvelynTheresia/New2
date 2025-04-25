package src;

import java.util.List;

/**
 * Controller class that handles application-related logic such as applying for flats,
 * withdrawing applications, viewing status, and deleting applications.
 */
public class ApplicationController {
    private List<Application> allApplications;

    /**
     * Constructs the controller with a list of existing applications.
     *
     * @param apps the list of all applications
     */
    public ApplicationController(List<Application> apps) {
        this.allApplications = apps;
    }

    /**
     * Handles application submission for an applicant.
     *
     * @param applicant the applicant submitting the application
     * @param project the project the applicant is applying to
     * @param type the type of flat the applicant is applying for
     */
    public void apply(Applicant applicant, Project project, FlatType type) {
        if (applicant.getApplication() != null) {
            System.out.println("You have already applied for a BTO.");
            return;
        }

        if (!applicant.canApply(type)) {
            System.out.println("You are not eligible to apply for this flat type.");
            return;
        }

        if(project.getAvailableUnits(type)<=0){
            System.out.println("No available units");
            return;
        }

        Application app = new Application(applicant, project, type);
        applicant.setApplication(app);
        allApplications.add(app);
        System.out.println("Application submitted. Status: Pending");
    }

    /**
     * Allows the applicant to request withdrawal of an existing application.
     *
     * @param applicant the applicant requesting withdrawal
     */
    public void requestToWithdraw(Applicant applicant) {
        Application app = applicant.getApplication();
        if (app == null) {
            System.out.println("No application to withdraw");
        } else if (app.getStatus().toString().equalsIgnoreCase("Pending") && !app.isWithdrawalRequested()) {
            deleteApplicantApplication(applicant);
            System.out.println("Removed your application.");
        } else {
            app.requestWithdrawal();
            System.out.println("Withdrawal is requested");
        }
    }

    /**
     * Displays the current status of the applicant’s application.
     *
     * @param applicant the applicant viewing their application
     */
    public void viewStatus(Applicant applicant) {
        if (applicant.getApplication() == null) {
            System.out.println("No application found.");
            return;
        }
        System.out.println(applicant.getApplication());
    }

    /**
     * Deletes an applicant’s application from both the applicant record and the global application list.
     *
     * @param applicant the applicant whose application is to be deleted
     */
    public void deleteApplicantApplication(Applicant applicant) {
        Application app = applicant.getApplication();
        if (app == null) return;

        // Step 1: Remove from applicant
        applicant.setApplication(null);

        // Step 2: Remove from master list
        allApplications.remove(app);

        System.out.println("Application deleted for " + applicant.getNric());
    }
}
