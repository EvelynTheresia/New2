package src;

import java.util.*;

public class LoginManager {
    private List<User> users;

    public LoginManager(List<User> users) {
        this.users = users;
    }

    public User login(Scanner sc) {
        System.out.print("Enter NRIC: ");
        String nric = sc.nextLine().trim();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        for (User user : users) {
            if (user.getNric().equalsIgnoreCase(nric) && user.login(password)) {
                System.out.println("Login successful!\n");
                return user;
            }
        }

        System.out.println("Invalid NRIC or Password. Try again.\n");
        return null;
    }
}
