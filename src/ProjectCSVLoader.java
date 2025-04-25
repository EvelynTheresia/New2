package src;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving of Project data to and from CSV files.
 */
public class ProjectCSVLoader extends DataReader {

    /**
     * Loads project data from a CSV file and populates the given project list.
     * 
     * @param projectFile   Path to the CSV file
     * @param projects      List to populate with Project objects
     * @param allManagers   List of available HDBManager objects
     * @param allOfficers   List of available HDBOfficer objects
     */
    public void loadProjectsFromCSV(
        String projectFile,
        List<Project> projects,
        List<HDBManager> allManagers,
        List<HDBOfficer> allOfficers
    ) {
        List<String[]> projectRows = readCSVFile(projectFile);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (int i = 1; i < projectRows.size(); i++) {
            try {
                String[] row = projectRows.get(i);

                String name = row[0].trim();
                String neighborhood = row[1].trim();
                int twoRoom = Integer.parseInt(row[2].trim());
                int threeRoom = Integer.parseInt(row[3].trim());
                LocalDate open = LocalDate.parse(row[4].trim(), formatter);
                LocalDate close = LocalDate.parse(row[5].trim(), formatter);
                String managerNric = row[6].trim();
                int officerSlots = Integer.parseInt(row[7].trim());
                String officerNricList = row[8].trim();
                boolean isVisible = "yes".equalsIgnoreCase(row[9].trim());

                HDBManager manager = findManagerByNric(managerNric, allManagers);
                Project project = new Project(name, neighborhood, open, close, manager, isVisible, officerSlots);
                project.setFlatUnits(FlatType.TWO_ROOM, twoRoom);
                project.setFlatUnits(FlatType.THREE_ROOM, threeRoom);

                if (!officerNricList.isEmpty()) {
                    String[] officerNrics = officerNricList.split(",");
                    ArrayList<HDBOfficer> matchedOfficers = new ArrayList<>();
                    for (String officerNric : officerNrics) {
                        HDBOfficer officer = findOfficerByNric(officerNric.trim(), allOfficers);
                        if (officer != null) {
                            matchedOfficers.add(officer);
                            officer.assignHandledProject(project);
                        } else {
                            System.out.println("Warning: Officer not found for NRIC: " + officerNric);
                        }
                    }
                    project.setOfficerInCharge(matchedOfficers);
                }

                projects.add(project);

            } catch (Exception e) {
                System.out.println("Error parsing row " + i + ": " + e.getMessage());
            }
        }
    }

    /**
     * Saves the given list of projects into a CSV file.
     * 
     * @param filePath  Path to the output CSV file
     * @param projects  List of projects to save
     */
    public void saveProjectsToCSV(String filePath, List<Project> projects) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            out.println("ProjectID;Neighborhood;TWO_ROOM;THREE_ROOM;Application Opening Date;Application Closing Date;Manager;Officer Slot;Officer;Visibility");

            for (Project project : projects) {
                out.println(formatProjectAsCSV(project));
            }
            System.out.println("Projects saved successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to save projects:");
            e.printStackTrace();
        }
    }

    /**
     * Formats a Project object as a CSV row.
     * 
     * @param project  The project to format
     * @return         Formatted CSV line
     */
    private String formatProjectAsCSV(Project project) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String officerNrics = project.getOfficerInCharge().stream()
                .map(HDBOfficer::getNric)
                .reduce((a, b) -> a + "," + b)
                .orElse("");

        return String.join(";",
                project.getName(),
                project.getNeighborhood(),
                String.valueOf(project.getAvailableUnits(FlatType.TWO_ROOM)),
                String.valueOf(project.getAvailableUnits(FlatType.THREE_ROOM)),
                project.getOpenDate().format(formatter),
                project.getCloseDate().format(formatter),
                project.getManagerInCharge() != null ? project.getManagerInCharge().getNric() : "",
                String.valueOf(project.getOfficerSlots()),
                officerNrics,
                project.isVisible() ? "yes" : "no"
        );
    }

    /**
     * Finds the HDBManager with the given NRIC.
     * 
     * @param nric          NRIC to search
     * @param allManagers   List of managers
     * @return              Matching HDBManager or null
     */
    private HDBManager findManagerByNric(String nric, List<HDBManager> allManagers) {
        for (HDBManager manager : allManagers) {
            if (manager.getNric().equals(nric)) {
                return manager;
            }
        }
        return null;
    }

    /**
     * Finds the HDBOfficer with the given NRIC.
     * 
     * @param nric          NRIC to search
     * @param allOfficers   List of officers
     * @return              Matching HDBOfficer or null
     */
    private HDBOfficer findOfficerByNric(String nric, List<HDBOfficer> allOfficers) {
        for (HDBOfficer officer : allOfficers) {
            if (officer.getNric().equals(nric)) {
                return officer;
            }
        }
        return null;
    }
}
