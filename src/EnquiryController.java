package src;

import java.util.List;

/**
 * Controller class responsible for managing enquiries made by applicants.
 * Includes functionality to submit, view, edit, and delete enquiries.
 */
public class EnquiryController {

    /**
     * Submits a new enquiry from an applicant regarding a specific project.
     *
     * @param applicant the applicant submitting the enquiry
     * @param project   the project related to the enquiry
     * @param content   the enquiry message content
     */
    public void submitEnquiry(Applicant applicant, Project project, String content) {
        Enquiry enquiry = new Enquiry(content, project);
        applicant.addEnquiry(enquiry);
        System.out.println("Enquiry submitted for project: " + project.getName());
    }

    /**
     * Displays all enquiries submitted by a specific applicant.
     *
     * @param applicant the applicant whose enquiries are to be viewed
     */
    public void viewMyEnquiries(Applicant applicant) {
        List<Enquiry> enquiries = applicant.getEnquiries();
        if (enquiries.isEmpty()) {
            System.out.println("No enquiries submitted.");
            return;
        }
        for (Enquiry e : enquiries) {
            System.out.println(e);
            System.out.println("-----------------------");
        }
    }

    /**
     * Edits the content of an existing enquiry by ID.
     *
     * @param applicant   the applicant who owns the enquiry
     * @param id          the ID of the enquiry to be edited
     * @param newContent  the new content/message for the enquiry
     */
    public void editEnquiry(Applicant applicant, int id, String newContent) {
        for (Enquiry e : applicant.getEnquiries()) {
            if (e.getId() == id) {
                e.setContent(newContent);
                System.out.println("Enquiry updated.");
                return;
            }
        }
        System.out.println("Enquiry ID not found.");
    }

    /**
     * Deletes an enquiry from the applicant's list by ID.
     *
     * @param applicant the applicant whose enquiry is to be deleted
     * @param id        the ID of the enquiry to delete
     */
    public void deleteEnquiry(Applicant applicant, int id) {
        List<Enquiry> list = applicant.getEnquiries();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == id) {
                list.remove(i);
                System.out.println("Enquiry deleted.");
                return;
            }
        }
        System.out.println("Enquiry ID not found.");
    }
}
