package src;

import java.util.*;
import java.time.LocalDate;

public class HDBManager extends User {

    public HDBManager(String nric, int age, String maritalStatus) {
        super(nric, age, maritalStatus);
    }

    public void viewManagerDashboard(
            Scanner sc,
            List<Project> allProjects,
            OfficerController officerController,
            List<Application> allApplications,
            List<Applicant> allApplicants
    ) {
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
            System.out.println("12. View Enquiries of My Projects");
            System.out.println("13. Generate Booking Report");
            System.out.println("14. Logout");
            System.out.print("Choose: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> viewAllProjects(allProjects);
                case 2 -> viewMyProjects(allProjects);
                case 3 -> createProject(sc, allProjects);
                case 4 -> editProject(sc, allProjects);
                case 5 -> deleteProject(sc, allProjects);
                case 6 -> toggleProjectVisibility(sc, allProjects);
                case 7 -> viewOfficerRegistrations(officerController);
                case 8 -> approveOfficerRegistrations(sc, officerController);
                case 9 -> viewApplications(allApplications);
                case 10 -> approveApplications(sc, allApplications);
                case 11 -> handleWithdrawals(sc, allApplicants);
                case 12 -> viewEnquiriesOfMyProjects(allApplicants, allProjects);
                case 13 -> generateBookingReport(sc, allApplications);
                case 14 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    // === PROJECT MANAGEMENT ===
    public void viewAllProjects(List<Project> allProjects) {
        System.out.println("\n== All Projects ==");
        for (Project p : allProjects) {
            System.out.println(p);
            System.out.println("------------------------");
        }
    }

    public void viewMyProjects(List<Project> allProjects) {
        System.out.println("\n== Projects You Manage ==");
        for (Project p : allProjects) {
            if (p.getManagerInCharge().getNric().equals(this.getNric())) {
                System.out.println(p);
                System.out.println("------------------------");
            }
        }
    }

    public void createProject(Scanner sc, List<Project> allProjects) {
        System.out.print("Enter project name: ");
        String name = sc.nextLine();
        System.out.print("Enter neighborhood: ");
        String neighborhood = sc.nextLine();
        System.out.print("Enter 2-room unit count: ");
        int twoRoom = Integer.parseInt(sc.nextLine());
        System.out.print("Enter 3-room unit count: ");
        int threeRoom = Integer.parseInt(sc.nextLine());
        System.out.print("Enter open date (yyyy-mm-dd): ");
        LocalDate open = LocalDate.parse(sc.nextLine());
        System.out.print("Enter close date (yyyy-mm-dd): ");
        LocalDate close = LocalDate.parse(sc.nextLine());

        Project p = new Project(name, neighborhood, open, close, this);
        p.setFlatUnits(FlatType.TWO_ROOM, twoRoom);
        p.setFlatUnits(FlatType.THREE_ROOM, threeRoom);
        allProjects.add(p);
        System.out.println("Project created.");
    }

    public void editProject(Scanner sc, List<Project> allProjects) {
        List<Project> myProjects = filterMyProjects(allProjects);
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

    public void deleteProject(Scanner sc, List<Project> allProjects) {
        List<Project> myProjects = filterMyProjects(allProjects);
        if (myProjects.isEmpty()) {
            System.out.println("You have no projects to delete.");
            return;
        }

        int idx = selectProject(sc, myProjects);
        if (idx == -1) return;

        Project p = myProjects.get(idx);
        allProjects.remove(p);
        System.out.println("Project deleted.");
    }

    public void toggleProjectVisibility(Scanner sc, List<Project> allProjects) {
        List<Project> myProjects = filterMyProjects(allProjects);
        if (myProjects.isEmpty()) {
            System.out.println("You have no projects to toggle.");
            return;
        }

        int idx = selectProject(sc, myProjects);
        if (idx == -1) return;

        Project p = myProjects.get(idx);
        p.toggleVisibility();
        System.out.println("Visibility toggled. Now: " + p.isVisible());
    }

    // === OFFICER REGISTRATION ===
    public void viewOfficerRegistrations(OfficerController officerController) {
        System.out.println("\n== Officer Registrations ==");
        for (OfficerRegistration r : officerController.getRegistrations()) {
            if (r.getProject().getManagerInCharge().getNric().equals(this.getNric())) {
                System.out.println(r);
            }
        }
    }

    public void approveOfficerRegistrations(Scanner sc, OfficerController officerController) {
        List<OfficerRegistration> pending = new ArrayList<>();
        for (OfficerRegistration r : officerController.getRegistrations()) {
            if (r.getProject().getManagerInCharge().getNric().equals(this.getNric())
                    && r.getStatus() == OfficerRegistration.Status.PENDING) {
                pending.add(r);
            }
        }

        if (pending.isEmpty()) {
            System.out.println("No pending registrations.");
            return;
        }

        for (int i = 0; i < pending.size(); i++) {
            System.out.println((i + 1) + ". " + pending.get(i));
        }

        System.out.print("Enter number to approve (0 to cancel): ");
        int choice = Integer.parseInt(sc.nextLine()) - 1;
        if (choice >= 0 && choice < pending.size()) {
            officerController.approveRegistration(pending.get(choice));
            System.out.println("Officer registration approved.");
        } else {
            System.out.println("Cancelled or invalid.");
        }
    }

    // === APPLICATION HANDLING ===
    public void viewApplications(List<Application> allApplications) {
        System.out.println("\n== All Applications ==");
        for (Application app : allApplications) {
            System.out.println(app);
        }
    }

    public void approveApplications(Scanner sc, List<Application> allApplications) {
        for (Application app : allApplications) {
            if (app.getStatus() == Application.Status.PENDING) {
                System.out.println(app);
                System.out.print("Approve this application? (y/n): ");
                String input = sc.nextLine();
                if (input.equalsIgnoreCase("y")) {
                    app.setStatus(Application.Status.SUCCESSFUL);
                } else {
                    app.setStatus(Application.Status.UNSUCCESSFUL);
                }
            }
        }
        System.out.println("All pending applications reviewed.");
    }

    public void handleWithdrawals(Scanner sc, List<Applicant> allApplicants) {
        for (Applicant a : allApplicants) {
            Application app = a.getApplication();
            if (app.getWithdrawal()==true && app!= null && (app.getStatus() == Application.Status.PENDING || app.getStatus() == Application.Status.SUCCESSFUL)) {
                System.out.println("Withdrawal request from: " + a.getNric());
                System.out.print("Approve withdrawal? (y/n): ");
                if (sc.nextLine().equalsIgnoreCase("y")) {
                    a.setApplication(null);
                    System.out.println("Application withdrawn.");
                }
            }
        }
    }

    // === ENQUIRIES & REPORT ===
    public void viewEnquiriesOfMyProjects(List<Applicant> applicants, List<Project> allProjects) {
        List<String> myProjectNames = new ArrayList<>();
        for (Project p : allProjects) {
            if (p.getManagerInCharge().getNric().equals(this.getNric())) {
                myProjectNames.add(p.getName());
            }
        }

        System.out.println("\n== Enquiries for Your Projects ==");
        for (Applicant a : applicants) {
            for (Enquiry e : a.getEnquiries()) {
                if (myProjectNames.contains(e.getProject().getName())) {
                    System.out.println("From: " + a.getNric());
                    System.out.println(e);
                    System.out.println("------------------------");
                }
            }
        }
    }

    public void generateBookingReport(Scanner sc, List<Application> allApplications) {
        System.out.print("Filter by (1) FlatType (2) Marital Status (3) All: ");
        int filter = Integer.parseInt(sc.nextLine());

        for (Application app : allApplications) {
            if (app.getStatus() == Application.Status.BOOKED) {
                Applicant a = app.getApplicant();
                boolean print = switch (filter) {
                    case 1 -> {
                        System.out.print("Enter flat type (2 or 3): ");
                        FlatType ft = sc.nextLine().equals("2") ? FlatType.TWO_ROOM : FlatType.THREE_ROOM;
                        yield app.getFlatType() == ft;
                    }
                    case 2 -> {
                        System.out.print("Enter marital status (single/married): ");
                        String input = sc.nextLine();
                        yield a.getMaritalStatus().equalsIgnoreCase(input);
                    }
                    case 3 -> true;
                    default -> false;
                };

                if (print) {
                    System.out.println("NRIC: " + a.getNric() + ", Age: " + a.getAge() +
                            ", Marital Status: " + a.getMaritalStatus() +
                            ", Flat: " + app.getFlatType() +
                            ", Project: " + app.getProject().getName());
                }
            }
        }
    }

    // === HELPERS ===
    private List<Project> filterMyProjects(List<Project> all) {
        List<Project> mine = new ArrayList<>();
        for (Project p : all) {
            if (p.getManagerInCharge().getNric().equals(this.getNric())) {
                mine.add(p);
            }
        }
        return mine;
    }

    private int selectProject(Scanner sc, List<Project> projects) {
        for (int i = 0; i < projects.size(); i++) {
            System.out.println((i + 1) + ". " + projects.get(i).getName());
        }
        System.out.print("Choose project number: ");
        int idx = Integer.parseInt(sc.nextLine()) - 1;
        return (idx >= 0 && idx < projects.size()) ? idx : -1;
    }
}
