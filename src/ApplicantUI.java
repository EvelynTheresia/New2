package src;

import java.util.Scanner;

/**
 * This class represents the User Interface for Applicants.
 * It handles navigation and interaction for applicants within the BTO system.
 */
public class ApplicantUI {
    private final ProjectController projectController;
    private final ApplicationController applicationController;
    private final EnquiryUI enquiryUI;

    /**
     * Constructs an ApplicantUI instance with associated controllers.
     *
     * @param pc the ProjectController used for project-related actions
     * @param ac the ApplicationController used for application-related actions
     * @param ec the EnquiryController used for enquiry-related actions
     */
    public ApplicantUI(ProjectController pc, ApplicationController ac, EnquiryController ec) {
        this.projectController = pc;
        this.applicationController = ac;
        this.enquiryUI = new EnquiryUI(pc, ec);
    }

    /**
     * Displays the applicant dashboard and handles user input.
     *
     * @param applicant the current logged-in applicant
     * @param sc        the Scanner for input
     */
    public void show(Applicant applicant, Scanner sc) {
        while (true) {
            System.out.println("\nWelcome, " + applicant.getNric());
            System.out.println("1. View Project List");
            System.out.println("2. Apply for BTO");
            System.out.println("3. View Application Status");
            System.out.println("4. Withdraw Application");
            System.out.println("5. Enquiry Menu");
            System.out.println("6. Change Password");
            System.out.println("7. Logout");
            System.out.print("Choose: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> projectController.viewProjectsForApplicant(applicant);
                case 2 -> applyForBTO(applicant, sc);
                case 3 -> applicationController.viewStatus(applicant);
                case 4 -> applicationController.requestToWithdraw(applicant);
                case 5 -> enquiryUI.show(applicant, sc);
                case 6 -> {
                    System.out.print("Enter new password: ");
                    applicant.changePassword(sc.nextLine());
                    System.out.println("Password changed.");
                }
                case 7 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Guides the applicant through the process of applying for a BTO project.
     *
     * @param applicant the applicant applying for a flat
     * @param sc        the Scanner for input
     */
    private void applyForBTO(Applicant applicant, Scanner sc) {
        if (applicant.getApplication() != null) {
            System.out.println("Cannot apply to more than one flat.");
            return;
        }

        projectController.viewProjectsForApplicant(applicant);
        System.out.print("Enter Project Name to apply: ");
        String pname = sc.nextLine();
        Project p = projectController.getProjectByName(pname);
        FlatType ft;

        if (p == null || !p.isVisible()) {
            System.out.println("Project not found.");
            return;
        } else {
            System.out.print("Enter flat type (2 or 3): ");
            String flatStr = sc.nextLine();
            ft = flatStr.equals("2") ? FlatType.TWO_ROOM : FlatType.THREE_ROOM;
        }

        if (p.getAvailableUnits(ft) > 0) {
            applicationController.apply(applicant, p, ft);
        } else {
            System.out.println("Unable to apply to unavailable FlatType.");
        }
    }
}
