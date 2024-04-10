package ui;

// Provides text inside buttons for attendance application UI
public enum ButtonNames {
    SUBMIT("SUBMIT"),
    LOAD("Load app data from file."),
    NEW("Create new app data."),
    ADD_CHILD("Add a child."),
    REMOVE_CHILD("Remove selected child."),
    CHECK_IN("Check in selected child."),
    CHECK_OUT("Check out selected child."),
    SELECT_PICKUP("Select caregiver to pick up this child."),
    SELECT_CAREGIVER("Select primary caregiver for new child."),
    ADD_CAREGIVER("Add a caregiver."),
    REMOVE_CAREGIVER("Remove selected caregiver."),
    ATTENDANCE("ATTENDANCE SHEET"),
    REGISTRY("REGISTRY"),
    SAVE("SAVE"),
    RESET("RESET ATTENDANCE SHEET");

    private final String name;

    // EFFECTS: Constructs button name
    ButtonNames(String name) {
        this.name = name;
    }

    // EFFECTS: Returns name value of this button
    public String getValue() {
        return name;
    }
}
