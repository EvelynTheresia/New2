package src;

import java.util.*;

public class ProjectController {
    private List<Project> projects;

    public ProjectController(List<Project> projects) {
        this.projects = projects;
    }

    public void viewProjectsForApplicant(Applicant applicant) {
        System.out.println("== Available Projects ==");

        for (Project project : projects) {
            if (!project.isVisible()) continue;

            if (applicant.getMaritalStatus().equalsIgnoreCase("single") && applicant.getAge() >= 35) {
                if (project.getAvailableUnits(FlatType.TWO_ROOM) > 0) {
                    System.out.println(project);
                    System.out.println("-------------------------");
                }
            } else if (applicant.getMaritalStatus().equalsIgnoreCase("married") && applicant.getAge() >= 21) {
                System.out.println(project);
                System.out.println("-------------------------");
            }
        }
    }

    public void viewAllProjects() {
        for (Project p : projects) {
            System.out.println(p);
        }
    }

    public Project getProjectByName(String name) {
        for (Project p : projects) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }
}


