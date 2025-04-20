package src;

import java.util.List;

public class UserCSVLoader extends DataReader {

    public void loadUsersFromCSV(
        String applicantFile, String officerFile, String managerFile,
        List<User> users, List<Applicant> allApplicants,
        List<HDBOfficer> allOfficers, List<HDBManager> allManagers
    ) {
        // === Load Applicants ===
        List<String[]> applicantRows = readCSVFile(applicantFile);
        for (int i = 1; i < applicantRows.size(); i++) {
            String[] row = applicantRows.get(i);
            Applicant a = new Applicant(row[1], Integer.parseInt(row[2]), row[3]);
            a.changePassword(row[4]); // Assuming: Name;NRIC;Age;Marital;Password
            allApplicants.add(a);
            users.add(a);
        }

        // === Load Officers ===
        List<String[]> officerRows = readCSVFile(officerFile);
        for (int i = 1; i < officerRows.size(); i++) {
            String[] row = officerRows.get(i);
            HDBOfficer o = new HDBOfficer(row[1], Integer.parseInt(row[2]), row[3]);
            o.changePassword(row[4]);
            allOfficers.add(o);
            users.add(o);
        }

        // === Load Managers ===
        List<String[]> managerRows = readCSVFile(managerFile);
        for (int i = 1; i < managerRows.size(); i++) {
            String[] row = managerRows.get(i);
            HDBManager m = new HDBManager(row[1], Integer.parseInt(row[2]), row[3]);
            m.changePassword(row[4]);
            allManagers.add(m);
            users.add(m);
        }
    }
}
