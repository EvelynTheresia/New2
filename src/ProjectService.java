package src;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Service class to handle project-related logic and actions such as
 * creating, updating, toggling visibility, and filtering projects
 * managed by a specific HDB Manager.
 */
public class ProjectService {

    /**
     * Returns a list of projects managed by a specific HDB Manager.
     *
     * @param allProjects List of all projects in the system
     * @param managerNric NRIC of the HDB Manager
     * @return List of projects managed by the specified manager
     */
    public List<Project> getManagerProjects(List<Project> allProjects, String managerNric) {
        return allProjects.stream()
                .filter(p -> p.getManagerInCharge().getNric().equals(managerNric))
                .collect(Collectors.toList());
    }

    /**
     * Checks if the manager is already managing another project during the given period.
     *
     * @param manager     The HDB Manager
     * @param allProjects List of all projects
     * @param newOpen     Proposed open date
     * @param newClose    Proposed close date
     * @return true if there is an overlapping project, false otherwise
     */
    public boolean hasOverlappingProject(HDBManager manager, List<Project> allProjects, LocalDate newOpen, LocalDate newClose) {
        return allProjects.stream()
                .filter(p -> p.getManagerInCharge() != null && p.getManagerInCharge().equals(manager))
                .anyMatch(p -> !(newClose.isBefore(p.getOpenDate()) || newOpen.isAfter(p.getCloseDate())));
    }

    /**
     * Toggles the visibility of a given project.
     *
     * @param p The project to toggle visibility for
     */
    public void toggleVisibility(Project p) {
        p.toggleVisibility();
    }

    /**
     * Guides a manager through the creation of a new housing project, collecting
     * and validating user input for all required fields.
     *
     * @param sc           Scanner for input
     * @param manager      The HDB Manager creating the project
     * @param allProjects  The list of existing projects to validate overlaps and add the new one
     */
    public void createProject(Scanner sc, HDBManager manager, List<Project> allProjects) {
        System.out.print("Enter project name: ");
        String name = sc.nextLine();

        for (Project proj : allProjects) {
            if (proj.getName().equalsIgnoreCase(name)) {
                System.out.println("A project with this name already exists. Creation cancelled.");
                return;
            }
        }

        System.out.print("Enter neighborhood: ");
        String neighborhood = sc.nextLine().trim();
        if (neighborhood.isEmpty()) {
            System.out.println("Error: Neighborhood cannot be empty.");
            return;
        }

        System.out.print("Enter 2-room unit count: ");
        String twoRoomInput = sc.nextLine().trim();
        if (twoRoomInput.isEmpty()) {
            System.out.println("Error: 2-room count cannot be empty.");
            return;
        }
        int twoRoom;
        try {
            twoRoom = Integer.parseInt(twoRoomInput);
            if (twoRoom < 0) {
                System.out.println("Error: 2-room count cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number for 2-room count.");
            return;
        }

        System.out.print("Enter 3-room unit count: ");
        String threeRoomInput = sc.nextLine().trim();
        if (threeRoomInput.isEmpty()) {
            System.out.println("Error: 3-room count cannot be empty.");
            return;
        }
        int threeRoom;
        try {
            threeRoom = Integer.parseInt(threeRoomInput);
            if (threeRoom < 0) {
                System.out.println("Error: 3-room count cannot be negative.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid number for 3-room count.");
            return;
        }

        System.out.print("Enter open date (yyyy-MM-dd): ");
        String openInput = sc.nextLine().trim();
        if (openInput.isEmpty()) {
            System.out.println("Error: Open date cannot be empty.");
            return;
        }
        LocalDate open;
        try {
            open = LocalDate.parse(openInput);
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid open date format (use yyyy-MM-dd).");
            return;
        }

        System.out.print("Enter close date (yyyy-MM-dd): ");
        String closeInput = sc.nextLine().trim();
        if (closeInput.isEmpty()) {
            System.out.println("Error: Close date cannot be empty.");
            return;
        }
        LocalDate close;
        try {
            close = LocalDate.parse(closeInput);
            if (close.isBefore(open)) {
                System.out.println("Error: Close date must be after open date.");
                return;
            }
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid close date format (use yyyy-MM-dd).");
            return;
        }

        if (hasOverlappingProject(manager, allProjects, open, close)) {
            System.out.println("You are already managing another project during this period.");
            return;
        }

        int officerSlots;
        do {
            System.out.print("Enter number of officer slots (must be less than 10): ");
            officerSlots = Integer.parseInt(sc.nextLine());
        } while (officerSlots >= 10);

        Project p = new Project(name, neighborhood, open, close, manager, false, officerSlots);
        p.setFlatUnits(FlatType.TWO_ROOM, twoRoom);
        p.setFlatUnits(FlatType.THREE_ROOM, threeRoom);
        allProjects.add(p);
        System.out.println("Project created.");
    }
}
