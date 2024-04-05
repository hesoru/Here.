package ui;

import model.AttendanceSheet;
import model.Caregiver;
import model.Child;
import model.Registry;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.pages.LoginWindow;
import ui.pages.RegistryWindow;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class AttendanceUI extends JFrame {

    private Registry registry;
    private AttendanceSheet attendanceSheet;
    private static final String JSON_STORE_ATTENDANCE = "./data/attendance_sheet.json";
    private static final String JSON_STORE_REGISTRY = "./data/registry.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public static void main(String[] args) {
        new AttendanceUI();
    }

    private AttendanceUI() {
        new LoginWindow(this);

//        sidebar = new JTabbedPane();
//        sidebar.setTabPlacement(JTabbedPane.LEFT);
//
//        loadTabs();
//        add(sidebar);
//
//        setVisible(true);
    }

    public void loadHomeWindow() {

    }


    public void createRegistry(String name) {
        this.registry = new Registry(name);
    }

    public void createAttendanceSheet(String name) {
        this.attendanceSheet = new AttendanceSheet(name);
    }

    public void createJsonWriter() {
        this.jsonWriter = new JsonWriter(JSON_STORE_ATTENDANCE, JSON_STORE_REGISTRY);
    }

    public void createJsonReader() {
        this.jsonReader = new JsonReader(JSON_STORE_ATTENDANCE, JSON_STORE_REGISTRY);
    }

    public Registry getRegistry() {
        return registry;
    }

    public AttendanceSheet getAttendanceSheet() {
        return attendanceSheet;
    }

    // MODIFIES: this
    // EFFECTS: Loads attendance sheet and registry from file.
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

//    // EFFECTS: returns sidebar of this UI
//    public JTabbedPane getTabbedPane() {
//        return sidebar;
//    }

//    private static void listOptions() {
//        System.out.println("Type the number of an option and press Enter:");
//        System.out.println("0. Exit application.");
//        System.out.println("1. Add new caregiver to caregiver registry.");
//        System.out.println("2. Add new child to child registry.");
//        System.out.println("3. Add caregiver (besides primary caregiver) authorized to pick up child.");
//        System.out.println("4. Reset attendance sheet (no children checked in).");
//        System.out.println("5. Check in child.");
//        System.out.println("6. View attendance sheet.");
//        System.out.println("7. View primary caregiver information for a child.");
//        System.out.println("8. View caregivers authorized to pick up a child.");
//        System.out.println("9. Check out child.");
//        System.out.println("10. Save attendance sheet, child registry, and caregiver registry to file.");
//        System.out.println("11. Load attendance sheet, child registry, and caregiver registry from file.");
//        System.out.println("12. Remove caregiver from caregiver registry.");
//        System.out.println("13. Remove child from child registry.");
//    }
//
//    // REQUIRES: User input must be integer from 1 to 13.
//    // MODIFIES: this, Child, Caregiver (depending on option selected)
//    // EFFECTS: Selects method to run based on number selected by user. Returns user to options at the end of every
//    //          method selected.
//    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
//    private void selectOption() {
//        for (; ; ) {
//            listOptions();
//            switch (Integer.parseInt(scanner.nextLine())) {
//                case 0:
//                    savePrompt();
//                    break;
//                case 1:
//                    addNewCaregiverToRegistry();
//                    break;
//                case 2:
//                    addNewChildToRegistry();
//                    break;
//                case 3:
//                    addAuthorizedToPickUp();
//                    break;
//                case 4:
//                    resetAttendance();
//                    break;
//                case 5:
//                    checkInChild();
//                    break;
//                case 6:
//                    viewAttendance();
//                    break;
//                case 7:
//                    viewPrimaryCaregiverInfo();
//                    break;
//                case 8:
//                    viewAuthorizedToPickUp();
//                    break;
//                case 9:
//                    checkOutChild();
//                    break;
//                case 10:
//                    saveState();
//                    break;
//                case 11:
//                    loadState();
//                    break;
//                case 12:
//                    removeCaregiverFromRegistry();
//                    break;
//                case 13:
//                    removeChildFromRegistry();
//                    break;
//            }
//        }
//    }
//
//    // REQUIRES: caregiverFullName is first and last name separated by a space (case-sensitive), caregiverPhoneNum is
//    //           10 digits without spaces, and caregiverEmail is not an empty string.
//    // MODIFIES: this, Registry, Caregiver
//    // EFFECTS: Creates new caregiver using user input and adds caregiver to the caregiver registry. Prints that
//    //          caregiver was added to caregiver registry.
//    private void addNewCaregiverToRegistry() {
//        System.out.println("Enter full name of caregiver (First Last) to add and press Enter:");
//        String caregiverFullName = scanner.nextLine();
//        System.out.println("Enter phone number (NNNNNNNNNN) of primary caregiver and press Enter:");
//        Long caregiverPhoneNumber = Long.valueOf(scanner.nextLine());
//        System.out.println("Enter email of primary caregiver and press Enter:");
//        String caregiverEmail = scanner.nextLine();
//
//        registry.addNewCaregiver(caregiverFullName, caregiverPhoneNumber, caregiverEmail);
//        System.out.println(caregiverFullName + " added to caregiver registry.\n");
//    }
//
//    // REQUIRES: childFullName and caregiverFullName is first and last name separated by a space (case-sensitive).
//    //           If caregiver does not exist in caregiverRegistry (null), caregiverPhoneNum is 10 digits without spaces,
//    //           and caregiverEmail is not an empty string.
//    // MODIFIES: this, Registry, Child, Caregiver (Caregiver only if caregiver entered not found in caregiverRegistry)
//    // EFFECTS: Creates new child using user input, then adds child to the child registry and list of children not
//    //          yet checked in. Prints that child was added to child registry and is not checked in. If
//    //          primaryCaregiver is not found in caregiverRegistry, the method directs the user to add a new caregiver
//    //          and then returns the user to the beginning of the method.
//    private void addNewChildToRegistry() {
//        System.out.println("Enter full name of child (First Last) and press Enter:");
//        String childFullName = scanner.nextLine();
//
//        System.out.println("Enter full name of primary caregiver (First Last) for " + childFullName
//                + " and press Enter:");
//        String primaryCaregiverFullName = scanner.nextLine();
//
//        Child child = registry.addNewChild(childFullName, primaryCaregiverFullName);
//
//        if (child != null) {
//            System.out.println(childFullName + " added to child registry.");
//            attendanceSheet.notCheckedIn(child);
//            System.out.println(childFullName + " is not checked in.\n");
//        } else {
//            caregiverIsNull();
//            System.out.println("Please try adding the child to the registry again.\n");
//            addNewChildToRegistry();
//        }
//    }
//
//    // REQUIRES: Caregiver does not exist (null).
//    // MODIFIES: this, Caregiver
//    // EFFECTS: Prints that caregiver was not found in the registry, and directs the user to add a new
//    //          caregiver to the caregiver registry.
//    private void caregiverIsNull() {
//        System.out.println("Caregiver not found in registry! Please add a new caregiver and try again.\n");
//        addNewCaregiverToRegistry();
//    }
//
//    // REQUIRES: childFullName and caregiverFullName are first and last name separated by a space
//    //           (case-sensitive), and child exists in childRegistry (!null).
//    // MODIFIES: Child, Caregiver (Caregiver only if caregiver entered not found in caregiverRegistry)
//    // EFFECTS: Selects a child in the child registry with a full name matching the user input, then selects a caregiver
//    //          from the caregiver registry with a full name matching the user input. Adds selected caregiver to the
//    //          list of caregivers authorized to pick up the selected child. If the caregiver entered is not in the
//    //          caregiver registry, the method directs the user to add a new caregiver and then returns the user to the
//    //          beginning of the method.
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
    // MODIFIES: this, AttendanceSheet
    // EFFECTS: Asks user to confirm they want to reset the attendance sheet. With user confirmation, resets the
    //          attendance sheet: clears the children in the notCheckedIn, checkedIn, and checkedOut list,
    //          and adds all children in the childRegistry to the notCheckedIn list. Otherwise, returns the user to the
    //          options. Prints whether the attendance sheet was reset or the method is returning the user to the
    //          options.
    public void resetAttendance() {
        attendanceSheet.reset("yes", registry.getChildRegistry());
    }
//
//
//
//    // EFFECTS: Prints out list of children that are not yet checked in, already checked in, and checked out (along
//    //          with check in and check out times).
//    private void viewAttendance() {
//        System.out.println("Not Yet Checked In:");
//        for (Child c : attendanceSheet.getNotCheckedIn()) {
//            System.out.println(c.getFullName());
//        }
//        System.out.println("\n" + "Checked In:");
//        for (Child c : attendanceSheet.getCheckedIn()) {
//            System.out.println(c.getFullName() + " (checked in at " + c.getCheckInTime() + ")");
//        }
//        System.out.println("\n" + "Checked Out:");
//        for (Child c : attendanceSheet.getCheckedOut()) {
//            System.out.println(c.getFullName() + " (checked out at " + c.getCheckOutTime() + ")" + "\n");
//        }
//    }
//
//    // REQUIRES: childFullName are first and last name separated by a space (case-sensitive), and child exists in
//    //           childRegistry (!null).
//    // EFFECTS: Selects a child in the child registry with a full name matching the user input (case-sensitive),
//    //          and prints their primary caregiver's full name, phone number, and email.
//    private void viewPrimaryCaregiverInfo() {
//        System.out.println("Enter full name of child and press Enter:");
//        String childFullName = scanner.nextLine();
//        Child child = registry.selectChild(childFullName);
//
//        System.out.println("Primary Caregiver: " + child.getPrimaryCaregiver().getFullName());
//        System.out.println("Phone Number: " + child.getPrimaryCaregiver().getPhoneNum());
//        System.out.println("Email: " + child.getPrimaryCaregiver().getEmail() + "\n");
//    }
//
//    // REQUIRES: childFullName is first and last name separated by a space (case-sensitive), and child exists in
//    //           childRegistry (!null).
//    // EFFECTS: Selects a child in the child registry with a full name matching the user input (case-sensitive), and
//    //          prints information about all caregivers authorized to pick up the selected child (full name, phone
//    //          number, and email).
//    private void viewAuthorizedToPickUp() {
//        System.out.println("Enter full name of child and press Enter:");
//        String childFullName = scanner.nextLine();
//        Child child = registry.selectChild(childFullName);
//
//        System.out.println("Caregivers authorized to pick up " + childFullName + ":" + "\n");
//        for (Caregiver c : child.getAuthorizedToPickUp()) {
//            System.out.println("Caregiver: " + c.getFullName());
//            System.out.println("Phone Number: " + c.getPhoneNum());
//            System.out.println("Email: " + c.getEmail() + "\n");
//        }
//    }
//
//    // REQUIRES: childToCheckOut exists in child registry (!null).
//    // MODIFIES: this, AttendanceSheet, Child, Caregiver (Caregiver only if caregiver entered not found in
//    //           caregiverRegistry)
//    // EFFECTS: Selects a child in the child registry with a full name matching the user input (case-sensitive), then
//    //          checks out the child if both the caregiver entered by the user is authorized to pick up the child and
//    //          if the child checked in today. When the child is checked out, this method removes the child from the
//    //          list of CheckedIn and adds them to the checkedOut list, and sets the child's check-out time to the
//    //          current time. Prints child, check-out time, and caregiver that picked up the child. If the caregiver
//    //          entered does not exist, then the method directs the user to add a new caregiver and then returns the
//    //          user to the beginning of the method. Prints if the caregiver is not authorized to pick
//    //          up the child and/or if the child has not checked in today.
//    private void checkOutChild() {
//        System.out.println("Enter full name of child and press Enter:");
//        String childFullName = scanner.nextLine();
//        Child child = registry.selectChild(childFullName);
//
//        System.out.println("Enter full name of caregiver (First Last) picking up " + childFullName + " and hit Enter:");
//        String caregiverFullName = scanner.nextLine();
//        Caregiver caregiver = registry.selectCaregiver(caregiverFullName);
//
//        String checkedOut = attendanceSheet.checkOut(child, caregiver);
//
//        if (checkedOut.equals("caregiver null")) {
//            caregiverIsNull();
//            System.out.println("Please try checking out the child again.\n");
//            checkOutChild();
//        } else if (checkedOut.equals("child checked out")) {
//            System.out.println(childFullName + " was checked out at " + child.getCheckOutTime() + " by "
//                    + caregiverFullName + ".\n");
//        } else if (checkedOut.equals("child not checked in")) {
//            System.out.println(childFullName + " has not been checked in today.\n");
//        } else if (checkedOut.equals("caregiver not authorized")) {
//            System.out.println(caregiverFullName + " is not authorized to pick up " + childFullName + "!\n");
//        } else {
//            System.out.println(childFullName + " has not been checked in today.");
//            System.out.println(caregiverFullName + " is not authorized to pick up " + childFullName + "!\n");
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

    // EFFECTS: Saves the attendance sheet and registry to file.
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

//    // REQUIRES: caregiverFullName is first and last name separated by a space (case-sensitive), and caregiver exists
//    //           in caregiverRegistry (!null).
//    // MODIFIES: this, Registry, Caregiver
//    // EFFECTS: Searches caregiver registry for caregiver with full name matching the user input (case-sensitive).
//    //          Asks user to confirm they want to remove the selected caregiver. With confirmation from the user,
//    //          removes caregiver from registry, and prints that caregiver was removed from the registry. Without user
//    //          confirmation, returns the user to the options. Prints if entered caregiver is a primary caregiver for an
//    //          existing child, and does not remove caregiver from registry.
//    private void removeCaregiverFromRegistry() {
//        System.out.println("Enter full name of caregiver (First Last) to remove and press Enter:");
//        String caregiverFullName = scanner.nextLine();
//
//        System.out.println("Are you sure you want to remove " + caregiverFullName + " from the caregiver registry?");
//        System.out.println("Type 'yes' and press Enter to remove caregiver.");
//        System.out.println("Type anything else and press Enter to return to options.");
//        String confirmChoice = scanner.nextLine();
//
//        String caregiverRemovedOrChildFullName = registry.removeCaregiver(caregiverFullName, confirmChoice);
//
//        if (caregiverRemovedOrChildFullName.equals("caregiver removed")) {
//            System.out.println(caregiverFullName + " removed from the caregiver registry.\n");
//        } else if (caregiverRemovedOrChildFullName.equals("choice not confirmed")) {
//            System.out.println("Returning to options.\n");
//        } else {
//            System.out.println(caregiverFullName + " is the primary caregiver listed for "
//                    + caregiverRemovedOrChildFullName + ".\n" + "Remove this child before removing their caregiver.\n");
//        }
//    }
//
////    public AttendanceUI() {
////
////        loginScreen();
//
//
////        sidebar = new JTabbedPane();
////        sidebar.setTabPlacement(JTabbedPane.LEFT);
////        loadTabs();
////        this.add(sidebar);
//
//
//
////        if (Arrays.equals(c, p)) {
////            super("Attendance");
////            System.out.println("Type the number of an option and press Enter:");
////            System.out.println("1. Load attendance sheet, child registry, and caregiver registry from file.");
////            System.out.println("2. Start new attendance sheet, child registry, and caregiver registry.");
////            int userInputChoice = (Integer.parseInt(scanner.nextLine()));
////            this.jsonWriter = new JsonWriter(JSON_STORE_ATTENDANCE, JSON_STORE_REGISTRY);
////            this.jsonReader = new JsonReader(JSON_STORE_ATTENDANCE, JSON_STORE_REGISTRY);
////            if (userInputChoice == 1) {
////                loadState();
////            } else {
////                System.out.println("Type in new registry name and press Enter:");
////                String registryName = scanner.nextLine();
////                System.out.println("Type in new attendance sheet name and press Enter:");
////                String attendanceSheetName = scanner.nextLine();
////                this.registry = new Registry(registryName);
////                this.attendanceSheet = new AttendanceSheet(attendanceSheetName);
////            }
////            selectOption();
////        } else {
////            System.out.println("The password is incorrect!");
////        }
//
////        this.pack(); // frame adjusts to fit all components automatically (put this method last)
////    }
////
////    public void loginScreen() {
////
////
////    //MODIFIES: this
////    //EFFECTS: adds home tab, settings tab and report tab to this UI
////    private void loadTabs() {
////        JPanel attendanceSheetTab = new AttendanceSheetTab(this);
////        JPanel childRegistryTab = new ChildRegistryTab(this);
////        JPanel caregiverRegistryTab = new CaregiverRegistryTab(this);
////
////        sidebar.add(attendanceSheetTab, ATTENDANCE_SHEET_TAB_INDEX);
////        sidebar.setTitleAt(ATTENDANCE_SHEET_TAB_INDEX, "Attendance Sheet");
////        sidebar.add(childRegistryTab, CAREGIVER_REGISTRY_TAB_INDEX);
////        sidebar.setTitleAt(ATTENDANCE_SHEET_TAB_INDEX, "Child Registry");
////        sidebar.add(caregiverRegistryTab, CHILD_REGISTRY_TAB_INDEX);
////        sidebar.setTitleAt(ATTENDANCE_SHEET_TAB_INDEX, "Caregiver Registry");
////    }
////
}




