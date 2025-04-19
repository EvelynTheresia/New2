package src;

public class OfficerRegistration {
    public enum Status {
        PENDING, APPROVED, REJECTED
    }

    private HDBOfficer officer;
    private Project project;
    private Status status;

    public OfficerRegistration(HDBOfficer officer, Project project) {
        this.officer = officer;
        this.project = project;
        this.status = Status.PENDING;
    }

    public HDBOfficer getOfficer() {
        return officer;
    }

    public Project getProject() {
        return project;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String toString() {
        return "Officer: " + officer.getNric() + ", Project: " + project.getName() + ", Status: " + status;
    }
}

