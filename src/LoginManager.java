package src;

import java.util.*;

/**
 * Handles user login functionality by validating credentials against a list of users.
 */
public class LoginManager {
    private List<User> users;

    /**
     * Constructs a LoginManager with a list of users.
     *
     * @param users List of registered users in the system
     */
    public LoginManager(List<User> users) {
        this.users = users;
    }

    /**
     * Prompts the user to input their NRIC and password, and attempts to authenticate.
     *
     * @param sc Scanner object for reading user input
     * @return the authenticated User object if successful; null otherwise
     */
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
