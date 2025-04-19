package src;

import java.util.List;

public class ApplicationController {
    private List<Application> allApplications;

    public ApplicationController(List<Application> apps) {
        this.allApplications = apps;
    }

    public void apply(Applicant applicant, Project project, FlatType type) {
        if (applicant.getApplication() != null) {
            System.out.println("You have already applied for a BTO.");
            return;
        }

        if (!applicant.canApply(type)) {
            System.out.println("You are not eligible to apply for this flat type.");
            return;
        }

        Application app = new Application(applicant, project, type);
        applicant.setApplication(app);
        allApplications.add(app);
        System.out.println("Application submitted. Status: Pending");
    }

public void withdraw(Applicant applicant) {
    Application app = applicant.getApplication();
    if (app == null) {
        System.out.println("No application to withdraw.");
        return;
    }

    app.setWithdrawal(true);
    System.out.println("Application withdrawn successfully.");
}



    public void viewStatus(Applicant applicant) {
        if (applicant.getApplication() == null) {
            System.out.println("No application found.");
            return;
        }
        System.out.println(applicant.getApplication());
    }
}


