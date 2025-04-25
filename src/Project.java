package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a Build-To-Order (BTO) housing project with details such as
 * name, location, unit availability, officers in charge, and visibility status.
 */
public class Project {
    private String name;
    private String neighborhood;
    private HashMap<FlatType, Integer> flatAvailability;
    private LocalDate openDate;
    private LocalDate closeDate;
    private HDBManager managerInCharge;
    private boolean isVisible;
    private int officerSlots;
    private ArrayList<HDBOfficer> officerInCharge;

    /**
     * Constructs a new Project.
     *
     * @param name              Name of the project
     * @param neighborhood      Location of the project
     * @param openDate          Application opening date
     * @param closeDate         Application closing date
     * @param managerInCharge   Manager responsible for this project
     * @param isVisible         Project visibility status
     * @param officerSlots      Number of officers that can be assigned to this project
     */
    public Project(String name, String neighborhood, LocalDate openDate, LocalDate closeDate,
                   HDBManager managerInCharge, boolean isVisible, int officerSlots) {
        this.name = name;
        this.neighborhood = neighborhood;
        this.flatAvailability = new HashMap<>();
        this.flatAvailability.put(FlatType.TWO_ROOM, 0);
        this.flatAvailability.put(FlatType.THREE_ROOM, 0);
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.managerInCharge = managerInCharge;
        this.isVisible = isVisible;
        this.officerSlots = officerSlots;
        this.officerInCharge = new ArrayList<>();
    }

    /**
     * Adds an officer to the list of officers in charge, if not already added.
     *
     * @param officer The officer to add
     */
    public void addOfficerInCharge(HDBOfficer officer) {
        if (!officerInCharge.contains(officer)) {
            officerInCharge.add(officer);
        }
    }

    /**
     * Returns the list of officers currently assigned to this project.
     *
     * @return List of officers
     */
    public ArrayList<HDBOfficer> getOfficerInCharge() {
        return officerInCharge;
    }

    /**
     * Sets the number of available units for a given flat type.
     *
     * @param type  Flat type (e.g., TWO_ROOM or THREE_ROOM)
     * @param count Number of available units
     */
    public void setFlatUnits(FlatType type, int count) {
        flatAvailability.put(type, count);
    }

    /**
     * Returns the manager in charge of this project.
     *
     * @return Manager object
     */
    public HDBManager getManagerInCharge() {
        return managerInCharge;
    }

    /**
     * Manually sets the list of officers in charge.
     *
     * @param officerInCharge List of officers to assign
     */
    public void setOfficerInCharge(ArrayList<HDBOfficer> officerInCharge) {
        this.officerInCharge = officerInCharge;
    }

    /**
     * Returns the number of officer slots available for assignment.
     *
     * @return Officer slot count
     */
    public int getOfficerSlots() {
        return officerSlots;
    }

    /**
     * Sets the number of officer slots for this project.
     *
     * @param officerSlots New number of officer slots
     */
    public void setOfficerSlots(int officerSlots) {
        this.officerSlots = officerSlots;
    }

    /**
     * Returns the name of the project.
     *
     * @return Project name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the neighborhood (location) of the project.
     *
     * @return Neighborhood name
     */
    public String getNeighborhood() {
        return neighborhood;
    }

    /**
     * Gets the application opening date.
     *
     * @return Opening date
     */
    public LocalDate getOpenDate() {
        return openDate;
    }

    /**
     * Gets the application closing date.
     *
     * @return Closing date
     */
    public LocalDate getCloseDate() {
        return closeDate;
    }

    /**
     * Returns the number of available units for a given flat type.
     *
     * @param type The flat type to check
     * @return Number of units available
     */
    public int getAvailableUnits(FlatType type) {
        return flatAvailability.getOrDefault(type, 0);
    }

    /**
     * Checks whether the project is currently visible to users.
     *
     * @return True if visible, false otherwise
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Toggles the project's visibility status.
     */
    public void toggleVisibility() {
        this.isVisible = !this.isVisible;
    }

    /**
     * Returns a string representation of the project for display.
     *
     * @return String with project summary
     */
    public String toString() {
        return name + " at " + neighborhood + " (Open: " + openDate + " to " + closeDate + ")"
                + "\n2-Room: " + flatAvailability.get(FlatType.TWO_ROOM)
                + ", 3-Room: " + flatAvailability.get(FlatType.THREE_ROOM)
                + "\nVisible: " + isVisible;
    }
}
