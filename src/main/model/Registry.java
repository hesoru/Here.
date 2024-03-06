package model;

import java.util.ArrayList;
import java.util.List;

// Contains child registry of children on attendance sheet, and caregiver registry of children's caregivers
public class Registry {

    private final List<Child> childRegistry;
    private final List<Caregiver> caregiverRegistry;

    // EFFECTS: Creates new instance of Registry (with childRegistry and caregiverRegistry).
    public Registry() {
        this.childRegistry = new ArrayList<>();
        this.caregiverRegistry = new ArrayList<>();
    }

    // REQUIRES: caregiverFullName is first and last name separated by a space (case-sensitive), caregiverPhoneNum is
    //           10 digits without spaces, and caregiverEmail is not an empty string.
    // MODIFIES: this, Caregiver
    // EFFECTS: Creates new caregiver using given arguments and adds caregiver to the caregiver registry.
    public void addNewCaregiver(String caregiverFullName, Long caregiverPhoneNum, String caregiverEmail) {
        Caregiver caregiver = new Caregiver(caregiverFullName, caregiverPhoneNum, caregiverEmail);
        caregiverRegistry.add(caregiver);
    }

    // REQUIRES: childFullName and caregiverFullName is first and last name separated by a space (case-sensitive).
    // MODIFIES: this, Child
    // EFFECTS: Searches caregiver registry for caregiver with full name matching primaryCaregiverFullName
    //          (case-sensitive). If primaryCaregiver exists (!null), creates new child using given arguments, then adds
    //          child to the child registry and returns true. If primaryCaregiver is not found in caregiverRegistry,
    //          returns false.
    public Child addNewChild(String childFullName, String primaryCaregiverFullName) {
        Caregiver primaryCaregiver = selectCaregiver(primaryCaregiverFullName);
        if (primaryCaregiver != null) {
            Child child = new Child(childFullName, primaryCaregiver);
            childRegistry.add(child);
            return child;
        }
        return null;
    }

    // REQUIRES: caregiverFullName is first and last name separated by a space (case-sensitive).
    // EFFECTS: Searches caregiver registry for caregiver with full name matching caregiverFullName (case-sensitive).
    //          Returns caregiver if found, otherwise returns null.
    public Caregiver selectCaregiver(String caregiverFullName) {
        for (Caregiver c : caregiverRegistry) {
            if (c.getCaregiverFullName().equals(caregiverFullName)) {
                return c;
            }
        }
        return null;
    }

    // REQUIRES: childFullName is first and last name separated by a space (case-sensitive).
    // EFFECTS: Searches child registry for child with full name matching childFullName (case-sensitive). Returns
    //          child if found, otherwise returns null.
    public Child selectChild(String childFullName) {
        for (Child c : childRegistry) {
            if (c.getChildFullName().equals(childFullName)) {
                return c;
            }
        }
        return null;
    }

    // REQUIRES: caregiverFullName is first and last name separated by a space (case-sensitive), and caregiver exists
    //           in caregiverRegistry (!null).
    // MODIFIES: this, Caregiver
    // EFFECTS: Searches caregiver registry for caregiver with full name matching caregiverFullName (case-sensitive).
    //          Removes selected caregiver from registry and returns "caregiver removed" if confirmChoice is "yes" and
    //          caregiver is not a primary caregiver for an existing child. If confirmChoice is not "yes", returns
    //          "choice not confirmed". If selected caregiver is a primary caregiver for an existing child, returns the
    //          name of the child.
    public String removeCaregiver(String caregiverFullName, String confirmChoice) {
        Caregiver caregiver = selectCaregiver(caregiverFullName);

        for (Child c : childRegistry) {
            if (c.getPrimaryCaregiver() == caregiver) {
                return c.getChildFullName();
            }
        }

        if (confirmChoice.equals("yes")) {
            caregiverRegistry.remove(caregiver);
            return "caregiver removed";
        }
        return "choice not confirmed";
    }

    // REQUIRES: childFullName is first and last name separated by a space (case-sensitive), and child exists
    //           in childRegistry (!null).
    // MODIFIES: this, Child
    // EFFECTS: Searches child registry for child with full name matching childFullName (case-sensitive).
    //          Removes selected child from registry and returns "child removed" if confirmChoice is "yes". If
    //          confirmChoice is not "yes", returns "choice not confirmed".
    public String removeChild(String childFullName, String confirmChoice) {
        Child child = selectChild(childFullName);

        if (confirmChoice.equals("yes")) {
            childRegistry.remove(child);
            return "child removed";
        }
        return "choice not confirmed";
    }

    public List<Child> getChildRegistry() {
        return childRegistry;
    }

    public List<Caregiver> getCaregiverRegistry() {
        return caregiverRegistry;
    }

}
