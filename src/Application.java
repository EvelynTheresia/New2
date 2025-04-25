package src;

/**
 * Represents a Build-To-Order (BTO) flat application submitted by an applicant.
 */
public class Application {

    /**
     * Enum representing the status of an application.
     */
    public enum Status {
        PENDING, 
        SUCCESSFULAPPLY, 
        UNSUCCESSFULAPPLY, 
        BOOKED, 
        SUCCESSFULWITHDRAW, 
        UNSUCCESSFULWITHDRAW
    }

    private Applicant applicant;
    private Project project;
    private FlatType flatType;
    private Status status;
    private boolean withdrawalRequested = false; 

    /**
     * Constructs a new Application with default status set to PENDING.
     *
     * @param applicant the applicant submitting the application
     * @param project the project being applied for
     * @param flatType the type of flat being requested
     */
    public Application(Applicant applicant, Project project, FlatType flatType) {
        this.applicant = applicant;
        this.project = project;
        this.flatType = flatType;
        this.status = Status.PENDING;
    }

    /**
     * Returns the applicant who submitted this application.
     *
     * @return the applicant
     */
    public Applicant getApplicant() {
        return applicant;
    }

    /**
     * Returns the project the application is for.
     *
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * Returns the flat type being applied for.
     *
     * @return the flat type
     */
    public FlatType getFlatType() {
        return flatType;
    }

    /**
     * Returns the current status of the application.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the status of the application.
     *
     * @param status the new status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Sets the withdrawal request flag.
     *
     * @param withdrawalRequested true if withdrawal was requested
     */
    public void setWithdrawal(boolean withdrawalRequested) {
        this.withdrawalRequested = withdrawalRequested;
    }

    /**
     * Checks whether withdrawal was requested.
     *
     * @return true if withdrawal requested, false otherwise
     */
    public boolean isWithdrawalRequested() {
        return withdrawalRequested;
    }

    /**
     * Flags this application as having requested withdrawal.
     */
    public void requestWithdrawal() {
        this.withdrawalRequested = true;
    }

    /**
     * Returns the action type: "Withdrawal" or "Application".
     *
     * @return the action type as string
     */
    public String getAction() {
        return this.withdrawalRequested ? "Withdrawal" : "Application";
    }

    /**
     * Deletes this application reference from the applicant.
     */
    public void deleteForApplicant() {
        if (this.applicant != null && this.applicant.getApplication() == this) {
            this.applicant.setApplication(null);
        }
    }

    /**
     * Returns a string representation of the application.
     *
     * @return formatted string with application details
     */
    public String toString() {
        return applicant.getNric() + " applied for " + flatType + " at " + project.getName()
                + " | Status: " + status + " | Action: " + getAction();
    }
}
