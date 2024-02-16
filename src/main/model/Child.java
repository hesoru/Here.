package model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// contains information pertaining to child on attendance sheet
public class Child {

    private final String fullName;
    private final Caregiver primaryCaregiver;
    private final List<Caregiver> authorizedToPickUp;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;

    // REQUIRES: fullName is first and last name separated by a space, primaryCaregiver exists.
    // EFFECTS: Creates a child with a full name, primary caregiver, and list of caregivers
    //          authorized to pick up child. Adds primary caregiver to authorized pickup list.
    public Child(String fullName, Caregiver primaryCaregiver) {
        this.fullName = fullName;
        this.primaryCaregiver = primaryCaregiver;
        this.authorizedToPickUp = new ArrayList<>();
        this.authorizedToPickUp.add(primaryCaregiver);
        this.checkInTime = null;
        this.checkOutTime = null;
    }

    // REQUIRES: authorizedToPickUp exists in caregiverRegistry.
    // MODIFIES: this
    // EFFECTS: Adds caregiver to list of authorized pickup list of child.
    public void addAuthorizedToPickUp(Caregiver authorizedToPickUp) {
        this.authorizedToPickUp.add(authorizedToPickUp);
    }

    public String getChildFullName() {
        return fullName;
    }

    public Caregiver getPrimaryCaregiver() {
        return primaryCaregiver;
    }

    public List<Caregiver> getAuthorizedToPickUp() {
        return authorizedToPickUp;
    }

    public LocalTime getCheckInTime() {
        return checkInTime;
    }

    public LocalTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckInTime(LocalTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public void setCheckOutTime(LocalTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

}
