package src;

/**
 * Abstract base class representing a user in the BTO system.
 * Stores common attributes shared across all user types such as name, NRIC, age, marital status, and password.
 */
public abstract class User {
    protected String name;
    protected String nric;
    protected String password;
    protected int age;
    protected String maritalStatus;

    /**
     * Constructs a new User with the given attributes and a default password.
     *
     * @param name           The name of the user
     * @param nric           The NRIC of the user
     * @param age            The age of the user
     * @param maritalStatus  The marital status of the user
     */
    public User(String name, String nric, int age, String maritalStatus) {
        this.name = name;
        this.nric = nric;
        this.age = age;
        this.maritalStatus = maritalStatus;
        this.password = "password";
    }

    /**
     * Validates the login attempt by comparing the input password to the stored password.
     *
     * @param inputPassword  The password entered by the user
     * @return true if the password is correct, false otherwise
     */
    public boolean login(String inputPassword) {
        return password.equals(inputPassword);
    }

    /**
     * Changes the password of the user.
     *
     * @param newPassword  The new password to be set
     */
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * Returns the NRIC of the user.
     *
     * @return the NRIC
     */
    public String getNric() {
        return nric;
    }

    /**
     * Returns the name of the user.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the password of the user.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the age of the user.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the marital status of the user.
     *
     * @return the marital status
     */
    public String getMaritalStatus() {
        return maritalStatus;
    }
}
