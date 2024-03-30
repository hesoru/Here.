package ui;

public enum ButtonNames {
    SUBMIT("SUBMIT"),
    LOAD("Load app data from file."),
    NEW("Create new app data."),
    ADD_CHILD("Add a child."),
    REMOVE_CHILD("Remove selected child."),
    CHECK_IN("Check in a child."),
    CHECK_OUT("Check out a child."),
    SELECT_PICKUP("Select caregiver to pick up."),
    SELECT_CAREGIVER("Select primary caregiver for new child."),
    ADD_CAREGIVER("Add a caregiver."),
    REMOVE_CAREGIVER("Remove selected caregiver."),
    ATTENDANCE("ATTENDANCE SHEET"),
    REGISTRY("REGISTRY"),
    SAVE("Save data"),
    RESET("RESET ATTENDANCE SHEET");

    private final String name;

    ButtonNames(String name) {
        this.name = name;
    }

    //EFFECTS: returns name value of this button
    public String getValue() {
        return name;
    }
}