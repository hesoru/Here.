package ui;

import model.AttendanceSheet;
import model.Registry;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.pages.LoginWindow;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// User interface for attendance application
public class AttendanceUI extends JFrame {

    private Registry registry;
    private AttendanceSheet attendanceSheet;
    private static final String JSON_STORE_ATTENDANCE = "./data/attendance_sheet.json";
    private static final String JSON_STORE_REGISTRY = "./data/registry.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: Main method to run UI for attendance application
    public static void main(String[] args) {
        new AttendanceUI();
    }

    // MODIFIES: this, LoginWindow
    // EFFECTS: Creates login window for attendance application
    private AttendanceUI() {
        new LoginWindow(this);
    }

    // REQUIRES: name should be non-empty string
    // MODIFIES: this, Registry
    // EFFECTS: Creates new registry with given name
    public void createRegistry(String name) {
        this.registry = new Registry(name);
    }

    // REQUIRES: name should be non-empty string
    // MODIFIES: this, AttendanceSheet
    // EFFECTS: Creates new attendance sheet with given name
    public void createAttendanceSheet(String name) {
        this.attendanceSheet = new AttendanceSheet(name);
    }

    // MODIFIES: this, JsonWriter
    // EFFECTS: Creates new JsonWriter to save application data to a JSON file
    public void createJsonWriter() {
        this.jsonWriter = new JsonWriter(JSON_STORE_ATTENDANCE, JSON_STORE_REGISTRY);
    }

    // MODIFIES: this, JsonReader
    // EFFECTS: Creates new JsonReader to load application data from a JSON file
    public void createJsonReader() {
        this.jsonReader = new JsonReader(JSON_STORE_ATTENDANCE, JSON_STORE_REGISTRY);
    }

    // MODIFIES: this, JsonWriter
    // EFFECTS: Saves attendance sheet and registry data to a JSON file.
    public void saveState() {
        try {
            jsonWriter.write(attendanceSheet, registry);
            System.out.println("Saved " + attendanceSheet.getName() + " attendance sheet to "
                    + JSON_STORE_ATTENDANCE + "\n");
            System.out.println("Saved " + registry.getName() + " registry to " + JSON_STORE_REGISTRY + "\n");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file:" + JSON_STORE_ATTENDANCE + " and "
                    + JSON_STORE_REGISTRY + "\n");
        }
    }

    // MODIFIES: this, JsonReader, Registry, AttendanceSheet, Child, Caregiver
    // EFFECTS: Loads attendance sheet and registry data from a JSON file.
    public void loadState() {
        try {
            this.registry = jsonReader.readRegistry();
            this.attendanceSheet = jsonReader.readAttendance(registry);
            System.out.println("Loaded " + attendanceSheet.getName() + " attendance sheet from "
                    + JSON_STORE_ATTENDANCE + "\n");
            System.out.println("Loaded " + registry.getName() + " registry from " + JSON_STORE_REGISTRY + "\n");
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE_ATTENDANCE + " and "
                    + JSON_STORE_REGISTRY + "\n");
        }
    }

    // MODIFIES: this, AttendanceSheet, Child
    // EFFECTS: Asks user to confirm they want to reset the attendance sheet. With user confirmation, resets the
    //          attendance sheet: clears the children in the notCheckedIn, checkedIn, and checkedOut list,
    //          and adds all children in the childRegistry to the notCheckedIn list. Otherwise, returns the user to the
    //          options. Prints whether the attendance sheet was reset or the method is returning the user to the
    //          options.
    // TODO: reset check-in and check-out time
    public void resetAttendance() {
        attendanceSheet.reset("yes", registry.getChildRegistry());
    }

    public Registry getRegistry() {
        return registry;
    }

    public AttendanceSheet getAttendanceSheet() {
        return attendanceSheet;
    }

    // TODO:
//    // REQUIRES: childFullName and caregiverFullName are first and last name separated by a space
//    //           (case-sensitive), and child exists in childRegistry (!null).
//    // MODIFIES: Child, Caregiver (Caregiver only if caregiver entered not found in caregiverRegistry)
//    // EFFECTS: Selects a child in the child registry with a full name matching the user input, then selects a
//    //          caregiver from the caregiver registry with a full name matching the user input.
//    //          Adds selected caregiver to the list of caregivers authorized to pick up the selected child.
//    //          If the caregiver entered is not in the caregiver registry, the method directs the user to add
//    //          a new caregiver and then returns the user to the beginning of the method.
//    private void addAuthorizedToPickUp() {
//        System.out.println("Enter full name of child and press Enter:");
//        String childFullName = scanner.nextLine();
//        Child child = registry.selectChild(childFullName);
//
//        System.out.println("Enter full name of caregiver (First Last) authorized to pick up " + childFullName
//                + " and press Enter:");
//        String caregiverFullName = scanner.nextLine();
//        Caregiver caregiver = registry.selectCaregiver(caregiverFullName);
//
//        if (caregiver != null) {
//            child.addAuthorizedToPickUp(caregiver);
//            System.out.println(caregiverFullName + " added as caregiver authorized to pick up " + childFullName
//                    + "." + "\n");
//        } else {
//            caregiverIsNull();
//            System.out.println("Please try adding an authorized caregiver to pick up a child again.\n");
//            addAuthorizedToPickUp();
//        }
//    }
//
//    // REQUIRES: userInputChoice must be integer 1 or 2.
//    // EFFECTS: Asks the user if they want to save the attendance sheet and registry to file. If yes, saves the
//    //          attendance sheet and registry to file and quits the application. If no, quits the application.
//    private void savePrompt() {
//        System.out.println("Do you want to save your data? Type in the number of an option and press Enter:");
//        System.out.println("1. Save and quit.");
//        System.out.println("2. Don't save and quit.");
//        int userInputChoice = (Integer.parseInt(scanner.nextLine()));
//        if (userInputChoice == 1) {
//            saveState();
//            System.exit(0);
//        } else {
//            System.exit(0);
//        }
//    }

}




