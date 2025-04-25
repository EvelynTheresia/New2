package src;

import java.util.List;
import java.util.Scanner;

/**
 * UI handler class for HDB Officers. This class provides the officer-specific menu and
 * delegates the officer's actions such as registering to handle projects, viewing applications,
 * responding to enquiries, and switching to the applicant dashboard.
 */
public class OfficerUI {
    private final HDBOfficer officer;

    /**
     * Constructs the Officer UI for a given officer.
     *
     * @param officer The logged-in HDBOfficer
     */
    public OfficerUI(HDBOfficer officer) {
        this.officer = officer;
    }

    /**
     * Displays the officer dashboard with various menu options.
     * Allows the officer to perform actions such as booking flats, registering for projects,
     * handling enquiries, and switching roles.
     *
     * @param sc               Scanner for input
     * @param allApplications List of all applications in the system
     * @param officerCtrl     Officer registration controller
     * @param projectCtrl     Project controller
     * @param applicationCtrl Application controller
     * @param enquiryCtrl     Enquiry controller
     * @param allApplicants   List of all applicants in the system
     */
    public void showDashboard(Scanner sc,
                              List<Application> allApplications,
                              OfficerController officerCtrl,
                              ProjectController projectCtrl,
                              ApplicationController applicationCtrl,
                              EnquiryController enquiryCtrl,
                              List<Applicant> allApplicants) {
        while (true) {
            System.out.println("\nOfficer Dashboard");
            System.out.println("1. View Applications");
            System.out.println("2. Book Flats");
            System.out.println("3. Register to Handle Project");
            System.out.println("4. View My Registration");
            System.out.println("5. View Projects I Handle");
            System.out.println("6. Enquiry Menu");
            System.out.println("7. Generate Receipt");
            System.out.println("8. Switch to Applicant Dashboard");
            System.out.println("9. Logout");
            System.out.print("Choose: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> officer.viewApplications(allApplications);
                case 2 -> officer.bookFlat(sc, allApplications);
                case 3 -> {
                    projectCtrl.viewAllProjects();
                    System.out.print("Enter project name to register: ");
                    Project p = projectCtrl.getProjectByName(sc.nextLine());
                    if (p != null) {
                        officerCtrl.register(officer, p);
                    } else {
                        System.out.println("Project not found.");
                    }
                }
                case 4 -> officerCtrl.viewMyRegistration(officer);
                case 5 -> {
                    List<Project> handled = officer.getHandledProjects();
                    if (handled.isEmpty()) {
                        System.out.println("You are not handling any projects.");
                    } else {
                        System.out.println("Projects you handle:");
                        handled.forEach(System.out::println);
                    }
                }
                case 6 -> officer.enquiryMenu(sc, allApplicants);
                case 7 -> {
                    System.out.print("Enter applicant NRIC: ");
                    String nric = sc.nextLine();
                    officer.generateReceiptByNric(nric, allApplicants);
                }
                case 8 -> {
                    ApplicantUI applicantUI = new ApplicantUI(projectCtrl, applicationCtrl, enquiryCtrl);
                    applicantUI.show(officer, sc); // reuse applicant dashboard
                }
                case 9 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
