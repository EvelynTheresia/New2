package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HDBOfficer extends Applicant {
    private boolean isOfficer = false;
    private List<Project> handledProjects = new ArrayList<>();

    public HDBOfficer(String nric, int age, String maritalStatus) {
        super(nric, age, maritalStatus);
    }

    public boolean isOfficer() {
        return isOfficer;
    }

    public void assignHandledProject(Project p) {
        handledProjects.add(p);
    }

    public List<Project> getHandledProjects() {
        return handledProjects;
    }

    public void viewOfficerDashboard(Scanner sc, List<Application> allApplications, OfficerController officerCtrl, ProjectController projectCtrl) {
        while (true) {
            System.out.println("\nOfficer Dashboard");
            System.out.println("1. View Applications");
            System.out.println("2. Book Flats");
            System.out.println("3. Register to Handle Project");
            System.out.println("4. View My Registration");
            System.out.println("5. View Projects I Handle");
            System.out.println("6. Logout");
            System.out.print("Choose: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> viewApplications(allApplications);
                case 2 -> bookFlat(sc, allApplications);
                case 3 -> {
                    projectCtrl.viewAllProjects();
                    System.out.print("Enter project name to register: ");
                    Project p = projectCtrl.getProjectByName(sc.nextLine());
                    if (p != null) {
                        officerCtrl.register(this, p);
                    } else {
                        System.out.println("Project not found.");
                    }
                }
                case 4 -> officerCtrl.viewMyRegistration(this);
                case 5 -> {
                    if (handledProjects.isEmpty()) {
                        System.out.println("You are not handling any projects.");
                    } else {
                        System.out.println("Projects you handle:");
                        for (Project p : handledProjects) {
                            System.out.println("- " + p.getName());
                        }
                    }
                }
                
                case 6 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    public void viewApplications(List<Application> allApplications) {
        System.out.println("\n== All Applications ==");
        for (Application app : allApplications) {
            System.out.println(app);
        }
    }

    public void bookFlat(Scanner sc, List<Application> allApplications) {
        System.out.print("Enter applicant NRIC to book flat: ");
        String nric = sc.nextLine();

        Application foundApp = null;
        for (Application app : allApplications) {
            if (app.getApplicant().getNric().equalsIgnoreCase(nric) && app.getStatus() == Application.Status.SUCCESSFUL) {
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

        // Book the flat
        foundApp.setStatus(Application.Status.BOOKED);
        project.setFlatUnits(type, project.getAvailableUnits(type) - 1);
        System.out.println("Flat booked successfully!");
        generateReceipt(foundApp);
    }

    public void generateReceipt(Application app) {
        System.out.println("\n=== Flat Booking Receipt ===");
        Applicant a = app.getApplicant();
        System.out.println("Name (NRIC): " + a.getNric());
        System.out.println("Age: " + a.getAge());
        System.out.println("Marital Status: " + a.getMaritalStatus());
        System.out.println("Flat Type Booked: " + app.getFlatType());
        System.out.println("Project Name: " + app.getProject().getName());
        System.out.println("Location: " + app.getProject().getNeighborhood());
        System.out.println("===========================\n");
    }
}


