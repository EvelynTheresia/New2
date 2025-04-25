package src;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * This class handles loading and saving user data (Applicants, Officers, Managers)
 * from and to CSV files. It extends DataReader for CSV reading functionality.
 */
public class UserCSVLoader extends DataReader {

    /**
     * Formats an applicant's data as a single CSV string.
     *
     * @param applicant the applicant to format
     * @return a CSV-formatted string
     */
    private String formatUserAsCSV(Applicant applicant) {
        return String.join(";",
            applicant.getName(),
            applicant.getNric(),
            String.valueOf(applicant.getAge()),
            applicant.getMaritalStatus(),
            applicant.getPassword()
        );
    }

    /**
     * Saves a list of applicants to a CSV file.
     *
     * @param filePath the file path to save the CSV to
     * @param applicants the list of applicants to save
     */
    public void saveUserToCSV(String filePath, List<Applicant> applicants) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            // CSV header
            out.println("Name;NRIC;Age;MaritalStatus;Password");

            // Write each applicant as a line
            for (Applicant applicant : applicants) {
                out.println(formatUserAsCSV(applicant));
            }

            System.out.println("Applicants saved successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to save applicants:");
            e.printStackTrace();
        }
    }

    /**
     * Loads users from multiple CSV files into their respective lists.
     * Supports loading of applicants, officers, and managers.
     *
     * @param applicantFile path to the applicant CSV file
     * @param officerFile path to the officer CSV file
     * @param managerFile path to the manager CSV file
     * @param users combined user list to populate
     * @param allApplicants list to store Applicant objects
     * @param allOfficers list to store HDBOfficer objects
     * @param allManagers list to store HDBManager objects
     */
    public void loadUsersFromCSV(
        String applicantFile, String officerFile, String managerFile,
        List<User> users, List<Applicant> allApplicants,
        List<HDBOfficer> allOfficers, List<HDBManager> allManagers
    ) {
        // === Load Applicants ===
        List<String[]> applicantRows = readCSVFile(applicantFile);
        for (int i = 1; i < applicantRows.size(); i++) {
            String[] row = applicantRows.get(i);
            try {
                String name = row[0].trim();
                String nric = row[1].trim();
                int age = Integer.parseInt(row[2].trim());
                String marital = row[3].trim();
                String password = row[4].trim();

                Applicant a = new Applicant(name, nric, age, marital);
                a.changePassword(password);
                allApplicants.add(a);
                users.add(a);
            } catch (Exception e) {
                System.out.println("Skipping invalid applicant row " + i + ": " + e.getMessage());
            }
        }

        // === Load Officers ===
        List<String[]> officerRows = readCSVFile(officerFile);
        for (int i = 1; i < officerRows.size(); i++) {
            String[] row = officerRows.get(i);
            try {
                String name = row[0].trim();
                String nric = row[1].trim();
                int age = Integer.parseInt(row[2].trim());
                String marital = row[3].trim();
                String password = row[4].trim();

                HDBOfficer o = new HDBOfficer(name, nric, age, marital);
                o.changePassword(password);
                allOfficers.add(o);
                users.add(o);
            } catch (Exception e) {
                System.out.println("Skipping invalid officer row " + i + ": " + e.getMessage());
            }
        }

        // === Load Managers ===
        List<String[]> managerRows = readCSVFile(managerFile);
        for (int i = 1; i < managerRows.size(); i++) {
            String[] row = managerRows.get(i);
            try {
                String name = row[0].trim();
                String nric = row[1].trim();
                int age = Integer.parseInt(row[2].trim());
                String marital = row[3].trim();
                String password = row[4].trim();

                HDBManager m = new HDBManager(name, nric, age, marital);
                m.changePassword(password);
                allManagers.add(m);
                users.add(m);
            } catch (Exception e) {
                System.out.println("Skipping invalid manager row " + i + ": " + e.getMessage());
            }
        }
    }
}
