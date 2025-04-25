package src;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * EnquiryCSVLoader is responsible for reading enquiry data from a CSV file
 * and saving enquiry data back into a CSV file. It uses helper methods to
 * find matching applicants and projects and to format enquiries.
 */
public class EnquiryCSVLoader extends DataReader {

    /**
     * Loads enquiries from a CSV file and adds them to the matching applicants.
     *
     * @param filePath   the path of the CSV file
     * @param projects   the list of projects available
     * @param applicants the list of applicants
     * @return a list of enquiries parsed from the file
     */
    public List<Enquiry> loadEnquiriesFromCSV(String filePath, List<Project> projects, List<Applicant> applicants) {
        List<Enquiry> enquiries = new ArrayList<>();
        List<String[]> rows = readCSVFile(filePath);

        for (int i = 1; i < rows.size(); i++) { // Skip header
            String[] row = rows.get(i);
            try {
                Enquiry enquiry = parseEnquiryRow(row, projects, applicants);
                if (enquiry != null) {
                    enquiries.add(enquiry);
                }
            } catch (Exception e) {
                System.err.println("Error parsing row " + i);
                e.printStackTrace();
            }
        }
        return enquiries;
    }

    /**
     * Saves all enquiries associated with the provided applicants to a CSV file.
     *
     * @param filePath   the destination CSV file path
     * @param applicants the list of applicants whose enquiries are to be saved
     */
    public void saveEnquiriesToCSV(String filePath, List<Applicant> applicants) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            out.println("Applicant;ProjectID;Content;Reply");

            for (Applicant applicant : applicants) {
                for (Enquiry enquiry : applicant.getEnquiries()) {
                    out.println(formatEnquiryAsCSV(applicant, enquiry));
                }
            }
            System.out.println("Enquiries saved successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to save enquiries:");
            e.printStackTrace();
        }
    }

    /**
     * Parses a row of data and constructs a new Enquiry object.
     *
     * @param row        the row data from the CSV
     * @param projects   the list of projects
     * @param applicants the list of applicants
     * @return the constructed Enquiry object or null if not found
     */
    private Enquiry parseEnquiryRow(String[] row, List<Project> projects, List<Applicant> applicants) {
        String applicantNric = row[0].trim();
        String projectId = row[1].trim();
        String content = row[2].trim();
        String reply = row.length > 3 ? row[3].trim() : "";

        Applicant applicant = findApplicantByNric(applicants, applicantNric);
        Project project = findProjectById(projects, projectId);

        if (applicant == null || project == null) {
            System.err.println("Skipping enquiry - applicant or project not found");
            return null;
        }

        Enquiry enquiry = new Enquiry(content, project);
        enquiry.setReply(reply);
        applicant.addEnquiry(enquiry);
        return enquiry;
    }

    /**
     * Formats a single enquiry as a CSV line.
     *
     * @param applicant the applicant who submitted the enquiry
     * @param enquiry   the enquiry to format
     * @return formatted CSV string
     */
    private String formatEnquiryAsCSV(Applicant applicant, Enquiry enquiry) {
        return String.join(";",
            applicant.getNric(),
            enquiry.getProject().getName(),
            escapeSemicolons(enquiry.getContent()),
            escapeSemicolons(enquiry.getReply())
        );
    }

    /**
     * Escapes semicolons to prevent corruption in CSV fields.
     *
     * @param text the input string
     * @return escaped string
     */
    private String escapeSemicolons(String text) {
        return text.replace(";", ",");
    }

    /**
     * Finds an applicant by NRIC.
     *
     * @param applicants the list of applicants
     * @param nric       the NRIC to search
     * @return the matched Applicant or null
     */
    private Applicant findApplicantByNric(List<Applicant> applicants, String nric) {
        for (Applicant a : applicants) {
            if (a.getNric().equals(nric)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Finds a project by name (used as ID).
     *
     * @param projects  the list of projects
     * @param projectId the project name to search
     * @return the matched Project or null
     */
    private Project findProjectById(List<Project> projects, String projectId) {
        for (Project p : projects) {
            if (p.getName().equals(projectId)) {
                return p;
            }
        }
        return null;
    }
}
