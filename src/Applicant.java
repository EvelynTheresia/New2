package src;

import java.util.ArrayList;

public class Applicant extends User {
    private Application application;
    private ArrayList<Enquiry> enquiries = new ArrayList<>();

    public Applicant(String nric, int age, String maritalStatus) {
        super(nric, age, maritalStatus);
    }

    public boolean canApply(FlatType flatType) {
        if (maritalStatus.equalsIgnoreCase("single") && age >= 35) {
            return flatType == FlatType.TWO_ROOM;
        } else if (maritalStatus.equalsIgnoreCase("married") && age >= 21) {
            return true;
        }
        return false;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

 
    public void addEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
    }

    public ArrayList<Enquiry> getEnquiries() {
        return enquiries;
    }
}

