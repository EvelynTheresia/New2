package src;

import java.util.List;
import java.util.Scanner;

/**
 * ManagerUI is responsible for displaying the dashboard and interaction flow
 * for HDBManager users. It handles manager-specific operations such as
 * project management, officer registration approvals, application review, and enquiries.
 */
public class ManagerUI {
    private final HDBManager manager;

    /**
     * Constructs the ManagerUI with the specified HDBManager.
     *
     * @param manager the logged-in HDBManager
     */
    public ManagerUI(HDBManager manager) {
        this.manager = manager;
    }

    /**
     * Displays the interactive dashboard for the manager and handles actions
     * based on user input.
     *
     * @param sc               Scanner for user input
     * @param allProjects      List of all projects in the system
     * @param officerController Controller handling officer registration
     * @param allApplications  List of all applications in the system
     * @param allApplicants    List of all applicants
     */
    public void showDashboard(
            Scanner sc,
            List<Project> allProjects,
            OfficerController officerController,
            List<Application> allApplications,
            List<Applicant> allApplicants) {

        while (true) {
            System.out.println("\n--- HDB Manager Dashboard ---");
            System.out.println("1. View All Projects");
            System.out.println("2. View My Projects");
            System.out.println("3. Create Project");
            System.out.println("4. Edit Project");
            System.out.println("5. Delete Project");
            System.out.println("6. Toggle Project Visibility");
            System.out.println("7. View Officer Registrations");
            System.out.println("8. Approve Officer Registrations");
            System.out.println("9. View BTO Applications");
            System.out.println("10. Approve BTO Applications");
            System.out.println("11. Handle Withdrawal Requests");
            System.out.println("12. View all Enquiries");
            System.out.println("13. View Enquiries of My Projects");
            System.out.println("14. Generate Booking Report");
            System.out.println("15. Logout");
            System.out.print("Choose: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> manager.viewAllProjects(allProjects);
                case 2 -> manager.viewMyProjects(allProjects);
                case 3 -> manager.getProjectService().createProject(sc, manager, allProjects);
                case 4 -> manager.editProject(sc, allProjects);
                case 5 -> manager.deleteProject(sc, allProjects, allApplications);
                case 6 -> manager.toggleProjectVisibility(sc, allProjects);
                case 7 -> manager.getRegistrationService().viewRegistrations(manager, officerController);
                case 8 -> manager.getRegistrationService().approveRegistrations(manager, officerController, sc);
                case 9 -> manager.getApplicationService().viewApplications(allApplications);
                case 10 -> manager.getApplicationService().approveApplications(manager, sc, allApplications);
                case 11 -> manager.getApplicationService().handleWithdrawals(sc, allApplicants, allApplications);
                case 12 -> manager.viewAllEnquiries(allApplicants);
                case 13 -> manager.viewEnquiriesOfMyProjects(allApplicants, allProjects, sc);
                case 14 -> manager.getReportService().generateBookingReport(sc, allApplications);
                case 15 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
