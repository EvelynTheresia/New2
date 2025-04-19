package src;

import java.util.List;

public class OfficerController {
    private List<OfficerRegistration> registrations;

    public OfficerController(List<OfficerRegistration> registrations) {
        this.registrations = registrations;
    }

    public void register(HDBOfficer officer, Project project) {
        if (officer.getApplication() != null && officer.getApplication().getProject() == project) {
            System.out.println("You already applied for this project. Cannot register as officer.");
            return;
        }

        for (OfficerRegistration reg : registrations) {
            if (reg.getOfficer().getNric().equals(officer.getNric())
                && reg.getStatus() == OfficerRegistration.Status.APPROVED
                && datesOverlap(reg.getProject(), project)) {
                System.out.println("You are already handling another project in the same application period.");
                return;
            }
        }

        OfficerRegistration reg = new OfficerRegistration(officer, project);
        registrations.add(reg);
        System.out.println("Registration submitted. Awaiting approval.");
    }

    public void viewMyRegistration(HDBOfficer officer) {
        for (OfficerRegistration reg : registrations) {
            if (reg.getOfficer().getNric().equals(officer.getNric())) {
                System.out.println(reg);
            }
        }
    }

    public void approveRegistration(OfficerRegistration reg) {
        reg.setStatus(OfficerRegistration.Status.APPROVED);
        reg.getOfficer().assignHandledProject(reg.getProject());
    }

    public List<OfficerRegistration> getRegistrations() {
        return registrations;
    }

    private boolean datesOverlap(Project p1, Project p2) {
        return !p1.getCloseDate().isBefore(p2.getOpenDate()) && !p1.getOpenDate().isAfter(p2.getCloseDate());
    }
}

