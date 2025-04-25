package src;

import java.util.ArrayList;

/**
 * Represents an applicant in the BTO system. 
 * Inherits common user attributes and behavior from the User class.
 * Each applicant can apply for a flat and submit enquiries.
 */
public class Applicant extends User {
    private Application application;
    private ArrayList<Enquiry> enquiries = new ArrayList<>();

    /**
     * Constructs a new Applicant with the specified personal details.
     * 
     * @param name           the name of the applicant
     * @param nric           the NRIC of the applicant
     * @param age            the age of the applicant
     * @param maritalStatus  the marital status of the applicant
     */
    public Applicant(String name, String nric, int age, String maritalStatus) {
        super(name, nric, age, maritalStatus);
    }

    /**
     * Checks if the applicant is eligible to apply for the given flat type.
     * 
     * @param flatType the type of flat to apply for
     * @return true if the applicant is eligible, false otherwise
     */
    public boolean canApply(FlatType flatType) {
        if (maritalStatus.equalsIgnoreCase("single") && age >= 35) {
            return flatType == FlatType.TWO_ROOM;
        } else if (maritalStatus.equalsIgnoreCase("married") && age >= 21) {
            return true;
        }
        return false;
    }

    /**
     * Gets the application made by the applicant.
     * 
     * @return the application object, or null if none exists
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Sets the application object for this applicant.
     * 
     * @param application the application to associate with this applicant
     */
    public void setApplication(Application application) {
        this.application = application;
    }

    /**
     * Adds a new enquiry to the list of enquiries for this applicant.
     * 
     * @param enquiry the enquiry to add
     */
    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
    }

    /**
     * Gets the list of enquiries submitted by this applicant.
     * 
     * @return a list of enquiries
     */
    public ArrayList<Enquiry> getEnquiries() {
        return enquiries;
    }
}
