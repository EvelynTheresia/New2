package src;

import java.util.List;
import java.util.Scanner;

/**
 * Provides functionality to generate booking reports for BTO applications.
 * Reports can be filtered by flat type, marital status, or show all pending applications.
 */
public class ReportService {

    /**
     * Generates a booking report based on the user's selected filter.
     * Options include filtering by flat type, marital status, or displaying all.
     *
     * @param sc               Scanner object for user input
     * @param allApplications List of all applications in the system
     */
    public void generateBookingReport(Scanner sc, List<Application> allApplications) {
        System.out.print("Filter by (1) FlatType (2) Marital Status (3) All: ");
        int filter = Integer.parseInt(sc.nextLine());

        switch (filter) {
            case 1 -> {
                System.out.print("Enter flat type (2 or 3): ");
                FlatType ft = sc.nextLine().equals("2") ? FlatType.TWO_ROOM : FlatType.THREE_ROOM;
                allApplications.stream()
                        .filter(app -> app.getStatus() == Application.Status.PENDING
                                && app.getAction().equalsIgnoreCase("Application")
                                && app.getFlatType() == ft)
                        .forEach(this::printApplicationDetails);
            }

            case 2 -> {
                System.out.print("Enter marital status (single/married): ");
                String maritalStatus = sc.nextLine();
                allApplications.stream()
                        .filter(app -> app.getStatus() == Application.Status.PENDING
                                && app.getAction().equalsIgnoreCase("Application")
                                && app.getApplicant().getMaritalStatus().equalsIgnoreCase(maritalStatus))
                        .forEach(this::printApplicationDetails);
            }

            case 3 -> allApplications.stream()
                        .filter(app -> app.getStatus() == Application.Status.PENDING
                                && app.getAction().equalsIgnoreCase("Application"))
                        .forEach(this::printApplicationDetails);

            default -> System.out.println("Invalid filter option");
        }
    }

    /**
     * Prints the details of an application in a structured format.
     *
     * @param app The application whose details are to be printed
     */
    private void printApplicationDetails(Application app) {
        Applicant a = app.getApplicant();
        System.out.printf("NRIC: %s, Age: %d, Marital Status: %s, Flat: %s, Project: %s%n",
                a.getNric(), a.getAge(), a.getMaritalStatus(), app.getFlatType(), app.getProject().getName());
    }
}
