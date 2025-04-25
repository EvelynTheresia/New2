package src;

import java.util.*;

/**
 * Handles logic related to project viewing and lookup for both applicants and officers.
 */
public class ProjectController {
    private List<Project> projects;

    /**
     * Constructs a ProjectController with a list of all projects.
     *
     * @param projects The list of projects to manage.
     */
    public ProjectController(List<Project> projects) {
        this.projects = projects;
    }

    /**
     * Displays the list of available projects to the applicant based on their eligibility.
     * Single applicants aged 35 and above can see 2-room flats,
     * married applicants aged 21 and above can see both 2-room and 3-room flats.
     *
     * @param applicant The applicant viewing the project list.
     */
    public void viewProjectsForApplicant(Applicant applicant) {
        System.out.println("== Available Projects ==");

        for (Project project : projects) {
            if (!project.isVisible()) continue;

            String projectInfo = project.getName() + " at " + project.getNeighborhood()
                    + " (Open: " + project.getOpenDate() + " to " + project.getCloseDate() + ")";

            if (applicant.getMaritalStatus().equalsIgnoreCase("single") && applicant.getAge() >= 35) {
                System.out.println(projectInfo + "\n2-Room: " + project.getAvailableUnits(FlatType.TWO_ROOM));
                System.out.println("-------------------------");
            } else if (applicant.getMaritalStatus().equalsIgnoreCase("married") && applicant.getAge() >= 21) {
                System.out.println(projectInfo + "\n2-Room: " + project.getAvailableUnits(FlatType.TWO_ROOM)
                        + ", 3-Room: " + project.getAvailableUnits(FlatType.THREE_ROOM));
                System.out.println("-------------------------");
            } else {
                System.out.println("You are not applicable for BTO.");
            }
        }
    }

    /**
     * Displays all projects regardless of applicant eligibility or visibility.
     */
    public void viewAllProjects() {
        for (Project p : projects) {
            System.out.println(p);
        }
    }

    /**
     * Retrieves a project by its name (case-insensitive).
     *
     * @param name The name of the project.
     * @return The project object if found, otherwise null.
     */
    public Project getProjectByName(String name) {
        for (Project p : projects) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
}
