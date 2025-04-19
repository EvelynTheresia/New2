package src;

public class Application {
    public enum Status {
        PENDING, SUCCESSFUL, UNSUCCESSFUL, BOOKED
    }

    private Applicant applicant;
    private Project project;
    private FlatType flatType;
    private Status status;
    private boolean withdrawal;

    public Application(Applicant applicant, Project project, FlatType flatType) {
        this.applicant = applicant;
        this.project = project;
        this.flatType = flatType;
        this.status = Status.PENDING;
        this.withdrawal=false;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Project getProject() {
        return project;
    }

    public FlatType getFlatType() {
        return flatType;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public void setWithdrawal(boolean withdraw) {
    	this.withdrawal=true;
    }
    public boolean getWithdrawal() {
    	return withdrawal;
    }

    public String toString() {
        return applicant.getNric() + " applied for " + flatType + " at " + project.getName() + " | Status: " + status;
    }
}


