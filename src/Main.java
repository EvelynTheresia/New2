package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Entry point of the HDB BTO Management System.
 * Handles initialization, data loading from CSV files,
 * user login, and dashboard routing based on roles.
 */
public class Main {

    /**
     * Main method to run the BTO Management System.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ==== Data containers ====
        List<User> users = new ArrayList<>();
        List<Applicant> allApplicants = new ArrayList<>();
        List<HDBOfficer> allOfficers = new ArrayList<>();
        List<HDBManager> allManagers = new ArrayList<>();
        List<Project> allProjects = new ArrayList<>();
        List<Application> allApplications = new ArrayList<>();
        List<OfficerRegistration> allOfficerRegistrations = new ArrayList<>();
        List<Enquiry> enquiries = new ArrayList<>();

        // ==== Load users from CSV ====
        UserCSVLoader loader = new UserCSVLoader();
        loader.loadUsersFromCSV(
                "ApplicantList.csv",
                "OfficerList.csv",
                "ManagerList.csv",
                users,
                allApplicants,
                allOfficers,
                allManagers
        );

        ProjectCSVLoader projectLoader = new ProjectCSVLoader();
        projectLoader.loadProjectsFromCSV("ProjectList.csv", allProjects, allManagers, allOfficers);

        EnquiryCSVLoader enquiryLoader = new EnquiryCSVLoader();
        enquiries = enquiryLoader.loadEnquiriesFromCSV("EnquiryList.csv", allProjects, allApplicants);

        ApplicationCSVLoader appLoader = new ApplicationCSVLoader();
        allApplications = appLoader.loadApplicationsFromCSV("ApplicationList.csv", allApplicants, allProjects, allApplications);

        OfficerRegistrationCSVLoader regLoader = new OfficerRegistrationCSVLoader();
        allOfficerRegistrations = regLoader.loadRegistrationsFromCSV("RegistrationList.csv", allOfficers, allProjects);

        // ==== Controllers ====
        ProjectController projectController = new ProjectController(allProjects);
        ApplicationController applicationController = new ApplicationController(allApplications);
        OfficerController officerController = new OfficerController(allOfficerRegistrations);
        EnquiryController enquiryController = new EnquiryController();

        // ==== Login loop ====
        LoginManager loginManager = new LoginManager(users);

        while (true) {
            User loggedInUser = null;

            while (loggedInUser == null) {
                loggedInUser = loginManager.login(sc);
            }

            // ==== Role Routing ====
            if (loggedInUser instanceof HDBManager manager) {
                new ManagerUI(manager).showDashboard(sc, allProjects, officerController, allApplications, allApplicants);
            } else if (loggedInUser instanceof HDBOfficer officer) {
                new OfficerUI(officer).showDashboard(sc, allApplications, officerController, projectController, applicationController, enquiryController, allApplicants);
            } else if (loggedInUser instanceof Applicant applicant) {
                ApplicantUI applicantUI = new ApplicantUI(projectController, applicationController, enquiryController);
                applicantUI.show(applicant, sc);
            }

            // ==== Save data back to CSV ====
            System.out.println("\nReturning to main menu...");
            enquiryLoader.saveEnquiriesToCSV("EnquiryList.csv", allApplicants);
            projectLoader.saveProjectsToCSV("ProjectList.csv", allProjects);
            loader.saveUserToCSV("ApplicantList.csv", allApplicants);
            appLoader.saveApplicationsToCSV("ApplicationList.csv", allApplications);
            regLoader.saveRegistrationsToCSV("RegistrationList.csv", allOfficerRegistrations);
            projectLoader.saveProjectsToCSV("ProjectList.csv", allProjects);
        }
    }
}
