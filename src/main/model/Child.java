package model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONArray;
import persistence.Writable;

// Contains information pertaining to child on child registry/attendance sheet
public class Child implements Writable {

    private final String fullName;
    private Caregiver primaryCaregiver;
    private final List<Caregiver> authorizedToPickUp;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private Caregiver checkOutCaregiver;

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
        this.checkOutCaregiver = null;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("fullName", fullName);
        json.put("primaryCaregiver", primaryCaregiver.toJson());
        json.put("authorizedToPickUp", authorizedToPickUpToJson());
        json.put("checkInTime", checkInTime);
        json.put("checkOutTime", checkOutTime);
        json.put("checkOutCaregiver", checkOutTime);
        return json;
    }

    // EFFECTS: returns caregivers in authorizedToPickUp as a JSON array
    private JSONArray authorizedToPickUpToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Caregiver c : authorizedToPickUp) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
    }

    // REQUIRES: authorizedToPickUp exists in caregiverRegistry (!null).
    // MODIFIES: this
    // EFFECTS: Adds caregiver to authorizedToPickUp list of child.
    public void addAuthorizedToPickUp(Caregiver authorizedToPickUp) {
        this.authorizedToPickUp.add(authorizedToPickUp);
    }

    public void setPrimaryCaregiver(Caregiver primaryCaregiver) {
        this.primaryCaregiver = primaryCaregiver;
    }

    public String getFullName() {
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

    public Caregiver getCheckOutCaregiver() {
        return checkOutCaregiver;
    }

    public void setCheckInTime(LocalTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public void setCheckOutTime(LocalTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

}
