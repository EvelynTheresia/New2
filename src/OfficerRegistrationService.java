package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Service class that manages officer registration approvals and views
 * for HDB Managers. This class encapsulates logic related to
 * handling officer registration processes such as approving or rejecting applications.
 */
public class OfficerRegistrationService {

    /**
     * Displays all officer registrations related to the projects managed by a specific manager.
     *
     * @param manager            the HDB manager who owns the projects
     * @param officerController  the controller managing officer registration data
     */
    public void viewRegistrations(HDBManager manager, OfficerController officerController) {
        System.out.println("\\n== Officer Registrations ==");
        boolean found = false;

        for (OfficerRegistration r : officerController.getRegistrations()) {
            if (r.getProject().getManagerInCharge().getNric().equals(manager.getNric())) {
                System.out.println(r);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No registrations found for your projects.");
        }
    }

    /**
     * Allows a manager to approve or reject pending officer registrations for their projects.
     *
     * @param manager            the manager reviewing the registrations
     * @param officerController  the controller managing the registrations
     * @param sc                 the scanner to receive user input
     */
    public void approveRegistrations(HDBManager manager, OfficerController officerController, Scanner sc) {
        List<OfficerRegistration> pending = new ArrayList<>();

        for (OfficerRegistration r : officerController.getRegistrations()) {
            if (r.getProject().getManagerInCharge().getNric().equals(manager.getNric())
                    && r.getStatus() == OfficerRegistration.Status.PENDING) {
                pending.add(r);
            }
        }

        if (pending.isEmpty()) {
            System.out.println("No pending registrations.");
            return;
        }

        for (int i = 0; i < pending.size(); i++) {
            System.out.println((i + 1) + ". " + pending.get(i));
        }

        System.out.print("Approve (y) or Reject (n): ");
        String decision = sc.nextLine();

        if (decision.equalsIgnoreCase("y")) {
            System.out.print("Enter number to approve (0 to cancel): ");
            int choice = Integer.parseInt(sc.nextLine()) - 1;

            if (choice >= 0 && choice < pending.size()) {
                OfficerRegistration selected = pending.get(choice);
                Project project = selected.getProject();
                HDBOfficer officer = selected.getOfficer();

                if (project.getOfficerSlots() <= 0) {
                    System.out.println("Cannot approve. No remaining officer slots.");
                    return;
                }

                project.setOfficerSlots(project.getOfficerSlots() - 1);
                project.addOfficerInCharge(officer);
                officer.assignHandledProject(project);

                officerController.approveRegistration(selected);
                System.out.println("Officer registration approved and assigned to project.");
            } else {
                System.out.println("Cancelled or invalid.");
            }
        } else if (decision.equalsIgnoreCase("n")) {
            System.out.print("Enter number to reject (0 to cancel): ");
            int choice = Integer.parseInt(sc.nextLine()) - 1;

            if (choice >= 0 && choice < pending.size()) {
                OfficerRegistration selected = pending.get(choice);
                selected.setStatus(OfficerRegistration.Status.REJECTED);
                System.out.println("Officer registration has been rejected.");
            }
        } else {
            System.out.println("Invalid input.");
        }
    }
}
