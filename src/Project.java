package src;

import java.time.LocalDate;
import java.util.HashMap;

public class Project {
    private String name;
    private String neighborhood;
    private HashMap<FlatType, Integer> flatAvailability;
    private LocalDate openDate;
    private LocalDate closeDate;
    private HDBManager managerInCharge;
    private boolean isVisible;
    private int officerSlots;

    public Project(String name, String neighborhood, LocalDate openDate, LocalDate closeDate, HDBManager managerInCharge) {
        this.name = name;
        this.neighborhood = neighborhood;
        this.flatAvailability = new HashMap<>();
        this.flatAvailability.put(FlatType.TWO_ROOM, 0);
        this.flatAvailability.put(FlatType.THREE_ROOM, 0);
        this.openDate = openDate;
        this.closeDate = closeDate;
        this.managerInCharge = managerInCharge;
        this.isVisible = true;
        this.officerSlots = 10;
    }

    public void setFlatUnits(FlatType type, int count) {
        flatAvailability.put(type, count);
    }
    
    public HDBManager getManagerInCharge() {
        return managerInCharge;
    }
    
    public int getOfficerSlots() {
    	return officerSlots;
    }

    public String getName() {
        return name;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public LocalDate getOpenDate() {
        return openDate;
    }

    public LocalDate getCloseDate() {
        return closeDate;
    }

    public int getAvailableUnits(FlatType type) {
        return flatAvailability.getOrDefault(type, 0);
    }
    
    public boolean isVisible() {
        return isVisible;
    }

    public void toggleVisibility() {
        this.isVisible = !this.isVisible;
    }

    public String toString() {
        return name + " at " + neighborhood + " (Open: " + openDate + " to " + closeDate + ")"
                + "\n2-Room: " + flatAvailability.get(FlatType.TWO_ROOM)
                + ", 3-Room: " + flatAvailability.get(FlatType.THREE_ROOM)
                + "\nVisible: " + isVisible;
    }
}


