package src;

import java.io.*;
import java.util.*;

/**
 * Handles loading and saving officer registration data from and to CSV files.
 * Extends {@link DataReader} to use common CSV reading functionality.
 */
public class OfficerRegistrationCSVLoader extends DataReader {

    /**
     * Saves the list of officer registrations to a CSV file.
     *
     * @param filePath       the file path to save the CSV to
     * @param registrations  the list of officer registrations
     */
    public void saveRegistrationsToCSV(String filePath, List<OfficerRegistration> registrations) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            out.println("OfficerNRIC;ProjectName;Status");
            for (OfficerRegistration reg : registrations) {
                out.println(String.join(";",
                    reg.getOfficer().getNric(),
                    reg.getProject().getName(),
                    reg.getStatus().name()
                ));
            }
            System.out.println("Officer registrations saved to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving officer registrations:");
            e.printStackTrace();
        }
    }

    /**
     * Loads officer registrations from a CSV file.
     *
     * @param filePath     the path to the CSV file
     * @param allOfficers  the list of all available officers for reference
     * @param allProjects  the list of all projects for reference
     * @return the list of loaded officer registrations
     */
    public List<OfficerRegistration> loadRegistrationsFromCSV(
        String filePath,
        List<HDBOfficer> allOfficers,
        List<Project> allProjects
    ) {
        List<OfficerRegistration> registrations = new ArrayList<>();
        List<String[]> rows = readCSVFile(filePath);

        for (int i = 1; i < rows.size(); i++) {
            try {
                String[] row = rows.get(i);
                String officerNric = row[0].trim();
                String projectName = row[1].trim();
                String statusStr = row[2].trim();

                HDBOfficer officer = findOfficerByNric(officerNric, allOfficers);
                Project project = findProjectByName(projectName, allProjects);
                OfficerRegistration.Status status = OfficerRegistration.Status.valueOf(statusStr);

                if (officer != null && project != null) {
                    OfficerRegistration reg = new OfficerRegistration(officer, project);
                    reg.setStatus(status);
                    registrations.add(reg);
                } else {
                    System.out.println("Warning: Could not match officer/project at row " + i);
                }
            } catch (Exception e) {
                System.out.println("Error parsing officer registration row " + i + ": " + e.getMessage());
            }
        }

        return registrations;
    }

    /**
     * Finds an officer by NRIC from a list.
     *
     * @param nric      the NRIC to search for
     * @param officers  the list of all officers
     * @return the matching officer or null if not found
     */
    private HDBOfficer findOfficerByNric(String nric, List<HDBOfficer> officers) {
        for (HDBOfficer o : officers) {
            if (o.getNric().equalsIgnoreCase(nric)) return o;
        }
        return null;
    }

    /**
     * Finds a project by name from a list.
     *
     * @param name      the name of the project to search for
     * @param projects  the list of all projects
     * @return the matching project or null if not found
     */
    private Project findProjectByName(String name, List<Project> projects) {
        for (Project p : projects) {
            if (p.getName().equalsIgnoreCase(name)) return p;
        }
        return null;
    }
}
