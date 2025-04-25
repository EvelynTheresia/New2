package src;

import java.util.List;
import java.util.Scanner;

/**
 * Service class responsible for managing applications, including
 * viewing, approving, and handling withdrawals.
 */
public class ApplicationManagementService {

    /**
     * Displays all applications in the system.
     *
     * @param allApplications list of all applications
     */
    public void viewApplications(List<Application> allApplications) {
        System.out.println("\\n== All Applications ==");
        for (Application app : allApplications) {
            System.out.println(app);
        }
    }

    /**
     * Allows a manager to approve or reject pending applications
     * that belong to projects they manage. Approving an application
     * reduces the flat unit count for the corresponding project.
     *
     * @param manager          the HDB manager approving the applications
     * @param sc               scanner for input
     * @param allApplications  list of all applications
     */
    public void approveApplications(HDBManager manager, Scanner sc, List<Application> allApplications) {
        boolean foundAny = false;

        for (Application app : allApplications) {
            if (app.getStatus() == Application.Status.PENDING &&
                app.getProject().getManagerInCharge() != null &&
                app.getProject().getManagerInCharge().getNric().equals(manager.getNric())) {

                foundAny = true;
                System.out.println(app);
                System.out.print("Approve this application? (y/n): ");
                String input = sc.nextLine();

                if (input.equalsIgnoreCase("y")) {
                    FlatType type = app.getFlatType();
                    Project proj = app.getProject();
                    proj.setFlatUnits(type, proj.getAvailableUnits(type) - 1);
                    app.setStatus(Application.Status.SUCCESSFULAPPLY);
                } else {
                    app.setStatus(Application.Status.UNSUCCESSFULAPPLY);
                }
            }
        }

        if (foundAny) {
            System.out.println("All pending applications reviewed.");
        } else {
            System.out.println("No pending applications for your projects.");
        }
    }

    /**
     * Handles withdrawal requests by applicants. If approved, the application
     * is removed, and flat units are returned to the respective project.
     *
     * @param sc               scanner for input
     * @param allApplicants    list of all applicants
     * @param allApplications  list of all applications
     */
    public void handleWithdrawals(Scanner sc, List<Applicant> allApplicants, List<Application> allApplications) {
        for (Applicant a : allApplicants) {
            Application app = a.getApplication();

            if (app != null && app.isWithdrawalRequested() &&
                (app.getStatus() == Application.Status.PENDING ||
                 app.getStatus() == Application.Status.SUCCESSFULAPPLY ||
                 app.getStatus() == Application.Status.BOOKED)) {

                System.out.println("Withdrawal request from: " + a.getNric());
                System.out.print("Approve withdrawal? (y/n): ");

                if (sc.nextLine().equalsIgnoreCase("y")) {
                    FlatType type = app.getFlatType();
                    Project proj = app.getProject();
                    proj.setFlatUnits(type, proj.getAvailableUnits(type) + 1);
                    a.setApplication(null);
                    allApplications.remove(app);
                    System.out.println("Application withdrawn.");
                } else {
                    System.out.println("Withdrawal denied.");
                }
            }
        }
    }
}
