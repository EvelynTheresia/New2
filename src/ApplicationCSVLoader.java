package src;

import java.io.*;
import java.util.*;

/**
 * Handles loading and saving application data from and to CSV files.
 * Inherits file reading functionality from the DataReader class.
 */
public class ApplicationCSVLoader extends DataReader {

    /**
     * Loads applications from a CSV file into memory.
     *
     * @param filePath         path to the CSV file
     * @param allApplicants    list of all applicants
     * @param allProjects      list of all projects
     * @param allApplications  list where loaded applications will be stored
     * @return updated list of applications
     */
    public List<Application> loadApplicationsFromCSV(
            String filePath,
            List<Applicant> allApplicants,
            List<Project> allProjects,
            List<Application> allApplications
    ) {
        List<String[]> rows = readCSVFile(filePath);

        for (int i = 1; i < rows.size(); i++) {
            try {
                String[] row = rows.get(i);

                String applicantNric = row[0].trim();
                String projectName = row[1].trim();
                FlatType flatType = FlatType.valueOf(row[2].trim());
                Application.Status status = Application.Status.valueOf(row[3].trim());
                
                Applicant applicant = findApplicantByNric(allApplicants, applicantNric);
                Project project = findProjectByName(allProjects, projectName);
                boolean withdrawalRequested = "Withdrawal".equalsIgnoreCase(row[4].trim());

                if (applicant == null || project == null) {
                    System.out.println("Warning: Invalid applicant/project at row " + i);
                    continue;
                }

                Application app = new Application(applicant, project, flatType);
                app.setWithdrawal(withdrawalRequested);
                app.setStatus(status);
                applicant.setApplication(app);
                allApplications.add(app);
            } catch (Exception e) {
                System.out.println("Error loading application row " + i + ": " + e.getMessage());
            }
        }

        return allApplications;
    }

    /**
     * Saves the list of applications to a CSV file.
     *
     * @param filePath      path to the destination CSV file
     * @param applications  list of applications to be saved
     */
    public void saveApplicationsToCSV(String filePath, List<Application> applications) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            out.println("ApplicantNRIC;ProjectName;FlatType;Status;Action");

            for (Application app : applications) {
                out.println(String.join(";",
                    app.getApplicant().getNric(),
                    app.getProject().getName(),
                    app.getFlatType().toString(),
                    app.getStatus().toString(),
                    app.getAction()
                ));
            }

            System.out.println("Applications saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to save applications:");
            e.printStackTrace();
        }
    }

    /**
     * Finds an applicant in the list by NRIC.
     *
     * @param applicants  list of all applicants
     * @param nric        NRIC to search for
     * @return matching Applicant object or null if not found
     */
    private Applicant findApplicantByNric(List<Applicant> applicants, String nric) {
        for (Applicant a : applicants) {
            if (a.getNric().equalsIgnoreCase(nric)) return a;
        }
        return null;
    }

    /**
     * Finds a project in the list by name.
     *
     * @param projects  list of all projects
     * @param name      name of the project
     * @return matching Project object or null if not found
     */
    private Project findProjectByName(List<Project> projects, String name) {
        for (Project p : projects) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }
}
