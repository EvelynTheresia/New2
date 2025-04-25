package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents an HDB Officer, who can also act as an applicant.
 * Officers can handle projects, manage enquiries, book flats,
 * and generate booking receipts.
 */
public class HDBOfficer extends Applicant {
    private boolean isOfficer = false;
    private List<Project> handledProjects = new ArrayList<>();
    List<Applicant> allApplicants;

    /**
     * Constructs an HDBOfficer with given details.
     *
     * @param name Name of the officer
     * @param nric NRIC of the officer
     * @param age Age of the officer
     * @param maritalStatus Marital status of the officer
     */
    public HDBOfficer(String name, String nric, int age, String maritalStatus) {
        super(name, nric, age, maritalStatus);
    }

    /**
     * @return true if the user is an officer
     */
    public boolean isOfficer() {
        return isOfficer;
    }

    /**
     * Assigns a project to the officer.
     *
     * @param p the project to assign
     */
    public void assignHandledProject(Project p) {
        handledProjects.add(p);
    }

    /**
     * @return list of projects handled by the officer
     */
    public List<Project> getHandledProjects() {
        return handledProjects;
    }

    /**
     * Allows the officer to view and reply to enquiries
     * related to projects they are handling.
     *
     * @param sc Scanner for input
     * @param allApplicants list of all applicants and their enquiries
     */
    public void enquiryMenu(Scanner sc, List<Applicant> allApplicants) {
        List<Enquiry> relevantEnquiries = new ArrayList<>();

        for (Applicant applicant : allApplicants) {
            for (Enquiry enquiry : applicant.getEnquiries()) {
                if (handledProjects.contains(enquiry.getProject())) {
                    relevantEnquiries.add(enquiry);
                }
            }
        }

        if (relevantEnquiries.isEmpty()) {
            System.out.println("No enquiries for your projects.");
            return;
        }

        System.out.println("\n--- Enquiries for Projects You Handle ---");
        for (int i = 0; i < relevantEnquiries.size(); i++) {
            Enquiry e = relevantEnquiries.get(i);
            System.out.printf("%d. [%s] %s\n", i + 1, e.getProject().getName(), e.getContent());
            System.out.println("   Reply: " + (e.getReply().isEmpty() ? "(no reply yet)" : e.getReply()));
        }

        System.out.print("\nEnter enquiry number to reply (0 to cancel): ");
        int choice = Integer.parseInt(sc.nextLine());

        if (choice < 1 || choice > relevantEnquiries.size()) {
            System.out.println("Cancelled.");
            return;
        }

        Enquiry toReply = relevantEnquiries.get(choice - 1);
        System.out.print("Enter your reply: ");
        String reply = sc.nextLine();
        if (reply.isEmpty()) {
            System.out.println("Reply cannot be empty.");
        } else {
            toReply.setReply(reply);
            System.out.println("Reply saved.");
        }
    }

    /**
     * Displays all submitted applications in the system.
     *
     * @param allApplications list of all applications
     */
    public void viewApplications(List<Application> allApplications) {
        System.out.println("\n== All Applications ==");
        for (Application app : allApplications) {
            System.out.println(app);
        }
    }

    /**
     * Books a flat for a specified applicant if eligible.
     *
     * @param sc Scanner for input
     * @param allApplications list of all applications
     */
    public void bookFlat(Scanner sc, List<Application> allApplications) {
        System.out.print("Enter applicant NRIC to book flat: ");
        String nric = sc.nextLine();

        Application foundApp = null;
        for (Application app : allApplications) {
            if (app.getApplicant().getNric().equalsIgnoreCase(nric)
                    && app.getStatus() == Application.Status.SUCCESSFULAPPLY) {
                foundApp = app;
                break;
            }
        }

        if (foundApp == null) {
            System.out.println("No successful application found for that NRIC.");
            return;
        }

        FlatType type = foundApp.getFlatType();
        Project project = foundApp.getProject();

        if (project.getAvailableUnits(type) <= 0) {
            System.out.println("No more units available for " + type + ".");
            return;
        }

        foundApp.setStatus(Application.Status.BOOKED);
        project.setFlatUnits(type, project.getAvailableUnits(type) - 1);
        System.out.println("Flat booked successfully!");
    }

    /**
     * Generates a receipt for a given applicant if the officer is authorized.
     *
     * @param nric NRIC of the applicant
     * @param allApplicants list of all applicants
     */
    public void generateReceiptByNric(String nric, List<Applicant> allApplicants) {
        for (Applicant applicant : allApplicants) {
            if (applicant.getNric().equalsIgnoreCase(nric)) {
                Application app = applicant.getApplication();

                if (app == null) {
                    System.out.println("This applicant has no application.");
                    return;
                }

                if (!handledProjects.contains(app.getProject())) {
                    System.out.println("You are not authorized to generate a receipt for this project.");
                    return;
                }

                System.out.println("\n=== Flat Booking Receipt ===");
                System.out.println("Name (NRIC): " + applicant.getNric());
                System.out.println("Age: " + applicant.getAge());
                System.out.println("Marital Status: " + applicant.getMaritalStatus());
                System.out.println("Flat Type Booked: " + app.getFlatType());
                System.out.println("Project Name: " + app.getProject().getName());
                System.out.println("Location: " + app.getProject().getNeighborhood());
                System.out.println("===========================\n");
                return;
            }
        }

        System.out.println("No applicant found with NRIC: " + nric);
    }
}
