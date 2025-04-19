package src;

import java.util.List;

public class EnquiryController {

    public void submitEnquiry(Applicant applicant, Project project, String content) {
        Enquiry enquiry = new Enquiry(content, project);
        applicant.addEnquiry(enquiry);
        System.out.println("Enquiry submitted for project: " + project.getName());
    }

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

