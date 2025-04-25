package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Represents an HDB Manager who can manage housing projects,
 * approve officer registrations, manage applications, and respond to enquiries.
 */
public class HDBManager extends User {

    private final ProjectService projectService = new ProjectService();
    private final OfficerRegistrationService registrationService = new OfficerRegistrationService();
    private final ApplicationManagementService applicationService = new ApplicationManagementService();
    private final ReportService reportService = new ReportService();

    /**
     * Constructs a new HDBManager with the given details.
     */
    public HDBManager(String name, String nric, int age, String maritalStatus) {
        super(name, nric, age, maritalStatus);
    }

    /** @return the project service used by this manager. */
    public ProjectService getProjectService() {
        return projectService;
    }

    /** @return the officer registration service used by this manager. */
    public OfficerRegistrationService getRegistrationService() {
        return registrationService;
    }

    /** @return the application service used by this manager. */
    public ApplicationManagementService getApplicationService() {
        return applicationService;
    }

    /** @return the report service used by this manager. */
    public ReportService getReportService() {
        return reportService;
    }

    /**
     * Displays all available projects.
     */
    public void viewAllProjects(List<Project> allProjects) {
        System.out.println("\n== All Projects ==");
        for (Project p : allProjects) {
            System.out.println(p);
            System.out.println("------------------------");
        }
    }

    /**
     * Displays only the projects managed by this manager.
     */
    public void viewMyProjects(List<Project> allProjects) {
        System.out.println("\n== Projects You Manage ==");
        List<Project> myProjects = projectService.getManagerProjects(allProjects, this.getNric());
        if (myProjects.isEmpty()) {
            System.out.println("You have no projects.");
            return;
        }
        for (Project p : myProjects) {
            System.out.println(p);
            System.out.println("------------------------");
        }
    }

    /**
     * Allows the manager to edit flat unit counts of a project.
     */
    public void editProject(Scanner sc, List<Project> allProjects) {
        List<Project> myProjects = projectService.getManagerProjects(allProjects, this.getNric());
        if (myProjects.isEmpty()) {
            System.out.println("You have no projects to edit.");
            return;
        }

        int idx = selectProject(sc, myProjects);
        if (idx == -1) return;

        Project p = myProjects.get(idx);
        System.out.print("New 2-room unit count: ");
        p.setFlatUnits(FlatType.TWO_ROOM, Integer.parseInt(sc.nextLine()));
        System.out.print("New 3-room unit count: ");
        p.setFlatUnits(FlatType.THREE_ROOM, Integer.parseInt(sc.nextLine()));
        System.out.println("Project updated.");
    }

    /**
     * Deletes a project managed by this manager only if it has no pending applications.
     */
    public void deleteProject(Scanner sc, List<Project> allProjects, List<Application> apps) {
        List<Project> myProjects = projectService.getManagerProjects(allProjects, this.getNric());
        if (myProjects.isEmpty()) {
            System.out.println("You have no projects to delete.");
            return;
        }

        int idx = selectProject(sc, myProjects);
        if (idx == -1) return;
        Project p = myProjects.get(idx);
        for (Application app : apps) {
            if (app.getProject() == p && app.getAction().equalsIgnoreCase("Application")
                    && app.getStatus() == Application.Status.PENDING) {
                System.out.println("Unable to delete the Project, pending applications");
                return;
            }
        }

        allProjects.remove(p);
        System.out.println("Project deleted.");
    }

    /**
     * Allows the manager to toggle the visibility of a project.
     */
    public void toggleProjectVisibility(Scanner sc, List<Project> allProjects) {
        List<Project> myProjects = projectService.getManagerProjects(allProjects, this.getNric());
        if (myProjects.isEmpty()) {
            System.out.println("You have no projects to toggle.");
            return;
        }

        int idx = selectProject(sc, myProjects);
        if (idx == -1) return;

        Project p = myProjects.get(idx);
        projectService.toggleVisibility(p);
        System.out.println("Visibility toggled. Now: " + p.isVisible());
    }

    /**
     * Displays all enquiries submitted by applicants.
     */
    public void viewAllEnquiries(List<Applicant> applicants) {
        System.out.println("\n== All Enquiries ==");
        boolean found = false;

        for (Applicant a : applicants) {
            for (Enquiry e : a.getEnquiries()) {
                found = true;
                System.out.println("From: " + a.getNric());
                System.out.println("Project: " + e.getProject().getName());
                System.out.println("Enquiry: " + e.getContent());
                System.out.println("Reply: " + (e.getReply().isEmpty() ? "(no reply yet)" : e.getReply()));
                System.out.println("----------------------------------");
            }
        }

        if (!found) {
            System.out.println("No enquiries submitted yet.");
        }
    }

    /**
     * Displays and allows replying to enquiries related to projects managed by this manager.
     */
    public void viewEnquiriesOfMyProjects(List<Applicant> applicants, List<Project> allProjects, Scanner sc) {
        List<Project> myProjects = projectService.getManagerProjects(allProjects, this.getNric());
        List<Enquiry> relevantEnquiries = new ArrayList<>();
        List<Applicant> relatedApplicants = new ArrayList<>();

        for (Applicant a : applicants) {
            for (Enquiry e : a.getEnquiries()) {
                if (myProjects.contains(e.getProject())) {
                    relevantEnquiries.add(e);
                    relatedApplicants.add(a);
                }
            }
        }

        if (relevantEnquiries.isEmpty()) {
            System.out.println("No enquiries found for your projects.");
            return;
        }

        System.out.println("\n== Enquiries for Your Projects ==");
        for (int i = 0; i < relevantEnquiries.size(); i++) {
            Enquiry e = relevantEnquiries.get(i);
            Applicant a = relatedApplicants.get(i);
            System.out.printf("%d. From: %s | Project: %s\n", i + 1, a.getNric(), e.getProject().getName());
            System.out.println("   Enquiry: " + e.getContent());
            System.out.println("   Reply: " + (e.getReply().isEmpty() ? "(no reply yet)" : e.getReply()));
            System.out.println("------------------------");
        }

        System.out.print("Enter enquiry number to reply (0 to cancel): ");
        int choice = Integer.parseInt(sc.nextLine());

        if (choice <= 0 || choice > relevantEnquiries.size()) {
            System.out.println("Cancelled.");
            return;
        }

        Enquiry toReply = relevantEnquiries.get(choice - 1);
        System.out.print("Enter your reply: ");
        String reply = sc.nextLine();
        if (reply.isEmpty()) {
            System.out.println("Enquiry cannot be empty.");
        } else {
            toReply.setReply(reply);
            System.out.println("Reply saved.");
        }
    }

    /**
     * Helper method to let the manager select a project from a list.
     */
    private int selectProject(Scanner sc, List<Project> projects) {
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i).getName());
        }
        System.out.print("Choose project number: ");
        int idx = Integer.parseInt(sc.nextLine()) - 1;
        return (idx >= 0 && idx < projects.size()) ? idx : -1;
    }
}
