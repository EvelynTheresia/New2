package src;

/**
 * Represents a registration request from an HDB officer to handle a project.
 */
public class OfficerRegistration {

    /**
     * Enum representing the registration status.
     */
    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    private HDBOfficer officer;
    private Project project;
    private Status status;

    /**
     * Constructs a new OfficerRegistration with a default status of PENDING.
     *
     * @param officer the HDB officer registering
     * @param project the project they wish to handle
     */
    public OfficerRegistration(HDBOfficer officer, Project project) {
        this.officer = officer;
        this.project = project;
        this.status = Status.PENDING;
    }

    /**
     * Returns the officer associated with the registration.
     *
     * @return the officer
     */
    public HDBOfficer getOfficer() {
        return officer;
    }

    /**
     * Returns the project for which the officer has registered.
     *
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Returns the current registration status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the registration status.
     *
     * @param status the new status to assign
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Returns a string representation of the registration.
     *
     * @return a string showing officer NRIC, project name, and status
     */
    public String toString() {
        return "Officer: " + officer.getNric() + ", Project: " + project.getName() + ", Status: " + status;
    }
}
