package src;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ==== Data containers ====
        List<User> users = new ArrayList<>();
        List<Applicant> allApplicants = new ArrayList<>();
        List<HDBOfficer> allOfficers = new ArrayList<>();
        List<Project> allProjects = new ArrayList<>();
        List<Application> allApplications = new ArrayList<>();
        List<OfficerRegistration> allOfficerRegistrations = new ArrayList<>();

        // ==== Sample users ====
        Applicant a1 = new Applicant("S1234567A", 36, "single");
        HDBOfficer o1 = new HDBOfficer("T7654321B", 40, "married");
        HDBManager m1 = new HDBManager("S0001112C", 45, "married");

        users.add(a1);
        users.add(o1);
        users.add(m1);

        allApplicants.add(a1);
        allOfficers.add(o1);

        // ==== Controllers ====
        ProjectController projectController = new ProjectController(allProjects);
        ApplicationController applicationController = new ApplicationController(allApplications);
        OfficerController officerController = new OfficerController(allOfficerRegistrations);
        EnquiryController enquiryController = new EnquiryController();

        // ==== Login ====
        LoginManager loginManager = new LoginManager(users);
        
        while(true) {
        User loggedInUser = null;

        while (loggedInUser == null) {
            loggedInUser = loginManager.login(sc);
        }

        // ==== Role Routing ====
        if (loggedInUser instanceof HDBManager manager) {
            manager.viewManagerDashboard(
                sc,
                allProjects,
                officerController,
                allApplications,
                allApplicants
            );
        } else if (loggedInUser instanceof HDBOfficer officer) {
            officer.viewOfficerDashboard(sc, allApplications, officerController, projectController);
        } else if (loggedInUser instanceof Applicant applicant) {
            applicantDashboard(applicant, sc, projectController, applicationController, enquiryController);
        }
        
     // After logout, go back to login screen
        System.out.println("\nReturning to main menu...");}
    }

    // ==== Applicant Dashboard ====
    public static void applicantDashboard(Applicant applicant, Scanner sc,
                                          ProjectController pc,
                                          ApplicationController ac,
                                          EnquiryController ec) {
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
                case 1 -> pc.viewProjectsForApplicant(applicant);
                case 2 -> {
                    System.out.print("Enter Project Name to apply: ");
                    String pname = sc.nextLine();
                    Project p = pc.getProjectByName(pname);
                    if (p == null) {
                        System.out.println("Project not found.");
                        break;
                    }

                    System.out.print("Enter flat type (2 or 3): ");
                    String flatStr = sc.nextLine();
                    FlatType ft = flatStr.equals("2") ? FlatType.TWO_ROOM : FlatType.THREE_ROOM;

                    ac.apply(applicant, p, ft);
                }
                case 3 -> ac.viewStatus(applicant);
                case 4 -> ac.withdraw(applicant);
                case 5 -> enquiryMenu(applicant, sc, pc, ec);
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

    // ==== Enquiry Menu ====
    public static void enquiryMenu(Applicant applicant, Scanner sc,
                                   ProjectController pc, EnquiryController ec) {
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
                    pc.viewProjectsForApplicant(applicant);
                    System.out.print("Enter project name: ");
                    String pname = sc.nextLine();
                    Project p = pc.getProjectByName(pname);
                    if (p != null) {
                        System.out.print("Enter your enquiry: ");
                        String msg = sc.nextLine();
                        ec.submitEnquiry(applicant, p, msg);
                    } else {
                        System.out.println("Project not found.");
                    }
                }
                case 2 -> ec.viewMyEnquiries(applicant);
                case 3 -> {
                    ec.viewMyEnquiries(applicant);
                    System.out.print("Enter Enquiry ID to edit: ");
                    int id = Integer.parseInt(sc.nextLine());
                    System.out.print("Enter new content: ");
                    String newText = sc.nextLine();
                    ec.editEnquiry(applicant, id, newText);
                }
                case 4 -> {
                    ec.viewMyEnquiries(applicant);
                    System.out.print("Enter Enquiry ID to delete: ");
                    int id = Integer.parseInt(sc.nextLine());
                    ec.deleteEnquiry(applicant, id);
                }
                case 5 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }
}
