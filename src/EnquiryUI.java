package src;

import java.util.Scanner;

/**
 * UI handler class for managing applicant enquiries.
 * Allows users to submit, view, edit, and delete enquiries.
 */
public class EnquiryUI {
    private final ProjectController projectController;
    private final EnquiryController enquiryController;

    /**
     * Constructs the EnquiryUI with references to project and enquiry controllers.
     *
     * @param pc ProjectController to retrieve and validate project information
     * @param ec EnquiryController to manage enquiry logic
     */
    public EnquiryUI(ProjectController pc, EnquiryController ec) {
        this.projectController = pc;
        this.enquiryController = ec;
    }

    /**
     * Displays the enquiry menu for the given applicant and handles their input.
     *
     * @param applicant the applicant interacting with the system
     * @param sc        scanner for user input
     */
    public void show(Applicant applicant, Scanner sc) {
        while (true) {
            System.out.println("\n--- Enquiry Menu ---");
            System.out.println("1. Submit Enquiry");
            System.out.println("2. View My Enquiries");
            System.out.println("3. Edit Enquiry");
            System.out.println("4. Delete Enquiry");
            System.out.println("5. Back");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> {
                    projectController.viewProjectsForApplicant(applicant);
                    System.out.print("Enter project name: ");
                    String pname = sc.nextLine();
                    Project p = projectController.getProjectByName(pname);
                    if (p != null) {
                        System.out.print("Enter your enquiry: ");
                        String msg = sc.nextLine();
                        if (msg.isEmpty()) {
                            System.out.println("Enquiry cannot be empty.");
                        } else {
                            enquiryController.submitEnquiry(applicant, p, msg);
                        }
                    } else {
                        System.out.println("Project not found.");
                    }
                }
                case 2 -> enquiryController.viewMyEnquiries(applicant);
                case 3 -> {
                    enquiryController.viewMyEnquiries(applicant);
                    System.out.print("Enter Enquiry ID to edit: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter new content: ");
                    String newText = sc.nextLine();
                    if (newText.isEmpty()) {
                        System.out.println("Enquiry cannot be empty.");
                    } else {
                        enquiryController.editEnquiry(applicant, id, newText);
                    }
                }
                case 4 -> {
                    enquiryController.viewMyEnquiries(applicant);
                    System.out.print("Enter Enquiry ID to delete: ");
                    int id = Integer.parseInt(sc.nextLine());
                    enquiryController.deleteEnquiry(applicant, id);
                }
                case 5 -> {
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
