package src;

import java.util.List;

/**
 * Controller responsible for handling officer registration-related operations
 * such as registering for projects, approving/rejecting registrations,
 * and viewing current officer registrations.
 */
public class OfficerController {
    private List<OfficerRegistration> registrations;

    /**
     * Constructs an OfficerController with the provided list of registrations.
     *
     * @param registrations the list of all officer registrations
     */
    public OfficerController(List<OfficerRegistration> registrations) {
        this.registrations = registrations;
    }

    /**
     * Allows an officer to register for a project, with checks to avoid
     * duplicate applications and overlapping project periods.
     *
     * @param officer the officer attempting to register
     * @param project the project they want to register for
     */
    public void register(HDBOfficer officer, Project project) {
        if (officer.getApplication() != null && officer.getApplication().getProject() == project) {
            System.out.println("You already applied for this project. Cannot register as officer.");
            return;
        }

        for (OfficerRegistration reg : registrations) {
            if (reg.getOfficer().getNric().equals(officer.getNric())
                && reg.getStatus() == OfficerRegistration.Status.APPROVED
                && datesOverlap(reg.getProject(), project)) {
                System.out.println("You are already handling another project in the same application period.");
                return;
            }
        }

        OfficerRegistration reg = new OfficerRegistration(officer, project);
        registrations.add(reg);
        System.out.println("Registration submitted. Awaiting approval.");
    }

    /**
     * Displays all registrations associated with the given officer.
     *
     * @param officer the officer whose registrations are to be shown
     */
    public void viewMyRegistration(HDBOfficer officer) {
        boolean found = false;
        for (OfficerRegistration reg : registrations) {
            if (reg.getOfficer().getNric().equals(officer.getNric())) {
                System.out.println(reg);
                found = true;
            }
        }
        if (!found) System.out.println("No registration found");
    }

    /**
     * Approves a pending officer registration and assigns the project to the officer.
     *
     * @param reg the officer registration to approve
     */
    public void approveRegistration(OfficerRegistration reg) {
        reg.setStatus(OfficerRegistration.Status.APPROVED);
        reg.getOfficer().assignHandledProject(reg.getProject());
    }

    /**
     * Rejects an officer registration.
     *
     * @param reg the registration to reject
     */
    public void rejectRegistration(OfficerRegistration reg) {
        reg.setStatus(OfficerRegistration.Status.REJECTED);
    }

    /**
     * Returns the list of all officer registrations.
     *
     * @return the list of registrations
     */
    public List<OfficerRegistration> getRegistrations() {
        return registrations;
    }

    /**
     * Helper method to check if two project periods overlap.
     *
     * @param p1 first project
     * @param p2 second project
     * @return true if the project dates overlap, false otherwise
     */
    private boolean datesOverlap(Project p1, Project p2) {
        return !p1.getCloseDate().isBefore(p2.getOpenDate()) && !p1.getOpenDate().isAfter(p2.getCloseDate());
    }
}
