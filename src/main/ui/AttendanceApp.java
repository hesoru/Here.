package ui;

import model.AttendanceSheet;
import model.Caregiver;
import model.Child;
import model.Registry;

import java.util.Scanner;

// Represents the user interface of the attendance application
public class AttendanceApp {

    private Registry registry;
    private AttendanceSheet attendanceSheet;

    private final Scanner scanner = new Scanner(System.in);

    // EFFECTS: Creates a new instance of AttendanceApp (with registry and attendanceSheet) if the user enters the
    //          correct password. Exits the program if the correct password is not entered.
    public AttendanceApp() {
        String password = "demo";

        System.out.println("Type in password and press Enter:");
        String userInput = scanner.nextLine();

        if (userInput.equals(password)) {
            this.registry = new Registry();
            this.attendanceSheet = new AttendanceSheet();
            selectOption();
        } else {
            System.exit(0);
        }
    }

    // EFFECTS: Lists options for attendance application.
    private static void listOptions() {
        System.out.println("Type the number of an option and press Enter:");
        System.out.println("0. Exit application.");
        System.out.println("1. Add new caregiver to caregiver registry.");
        System.out.println("2. Add new child to child registry.");
        System.out.println("3. Add caregiver (besides primary caregiver) authorized to pick up child.");
        System.out.println("4. Reset attendance sheet (no children checked in).");
        System.out.println("5. Check in child.");
        System.out.println("6. View attendance sheet.");
        System.out.println("7. View primary caregiver information for a child.");
        System.out.println("8. View caregivers authorized to pick up a child.");
        System.out.println("9. Check out child.");
        System.out.println("10. Remove caregiver from caregiver registry.");
        System.out.println("11. Remove child from child registry.");
    }

    // REQUIRES: User input must be integer from 1 to 11.
    // MODIFIES: this, Child, Caregiver (depending on option selected)
    // EFFECTS: Selects method to run based on number selected by user. Returns user to options at the end of every
    //          method selected.
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void selectOption() {
        for (; ; ) {
            listOptions();
            switch (Integer.parseInt(scanner.nextLine())) {
                case 0:
                    System.exit(0);
                case 1:
                    addNewCaregiverToRegistry();
                    break;
                case 2:
                    addNewChildToRegistry();
                    break;
                case 3:
                    addAuthorizedToPickUp();
                    break;
                case 4:
                    resetAttendance();
                    break;
                case 5:
                    checkInChild();
                    break;
                case 6:
                    viewAttendance();
                    break;
                case 7:
                    viewPrimaryCaregiverInfo();
                    break;
                case 8:
                    viewAuthorizedToPickUp();
                    break;
                case 9:
                    checkOutChild();
                    break;
                case 10:
                    removeCaregiverFromRegistry();
                    break;
                case 11:
                    removeChildFromRegistry();
                    break;
            }
        }
    }

    // REQUIRES: caregiverFullName is first and last name separated by a space (case-sensitive), caregiverPhoneNum is
    //           10 digits without spaces, and caregiverEmail is not an empty string.
    // MODIFIES: this, Registry, Caregiver
    // EFFECTS: Creates new caregiver using user input and adds caregiver to the caregiver registry. Prints that
    //          caregiver was added to caregiver registry.
    private void addNewCaregiverToRegistry() {
        System.out.println("Enter full name of caregiver (First Last) to add and press Enter:");
        String caregiverFullName = scanner.nextLine();
        System.out.println("Enter phone number (NNNNNNNNNN) of primary caregiver and press Enter:");
        Long caregiverPhoneNumber = Long.valueOf(scanner.nextLine());
        System.out.println("Enter email of primary caregiver and press Enter:");
        String caregiverEmail = scanner.nextLine();

        registry.addNewCaregiver(caregiverFullName, caregiverPhoneNumber, caregiverEmail);
        System.out.println(caregiverFullName + " added to caregiver registry.\n");
    }

    // REQUIRES: childFullName and caregiverFullName is first and last name separated by a space (case-sensitive).
    //           If caregiver does not exist in caregiverRegistry (null), caregiverPhoneNum is 10 digits without spaces,
    //           and caregiverEmail is not an empty string.
    // MODIFIES: this, Registry, Child, Caregiver (Caregiver only if caregiver entered not found in caregiverRegistry)
    // EFFECTS: Creates new child using user input, then adds child to the child registry and list of children not
    //          yet checked in. Prints that child was added to child registry and is not checked in. If
    //          primaryCaregiver is not found in caregiverRegistry, the method directs the user to add a new caregiver
    //          and then returns the user to the beginning of the method.
    private void addNewChildToRegistry() {
        System.out.println("Enter full name of child (First Last) and press Enter:");
        String childFullName = scanner.nextLine();
        Child child = registry.selectChild(childFullName);

        System.out.println("Enter full name of primary caregiver (First Last) for " + childFullName
                + " and press Enter:");
        String primaryCaregiverFullName = scanner.nextLine();

        boolean childAdded = registry.addNewChild(childFullName, primaryCaregiverFullName);

        if (childAdded) {
            System.out.println(childFullName + " added to child registry.");
            attendanceSheet.notCheckedIn(child);
            System.out.println(childFullName + " is not checked in.\n");
        } else {
            caregiverIsNull();
            System.out.println("Please try adding the child to the registry again.\n");
            addNewChildToRegistry();
        }
    }

    // REQUIRES: Caregiver does not exist (null).
    // MODIFIES: this, Caregiver
    // EFFECTS: Prints that caregiver was not found in the registry, and directs the user to add a new
    //          caregiver to the caregiver registry.
    private void caregiverIsNull() {
        System.out.println("Caregiver not found in registry! Please add a new caregiver and try again.\n");
        addNewCaregiverToRegistry();
    }

    // REQUIRES: childFullName and caregiverFullName are first and last name separated by a space
    //           (case-sensitive), and child exists in childRegistry (!null).
    // MODIFIES: Child, Caregiver (Caregiver only if caregiver entered not found in caregiverRegistry)
    // EFFECTS: Selects a child in the child registry with a full name matching the user input, then selects a caregiver
    //          from the caregiver registry with a full name matching the user input. Adds selected caregiver to the
    //          list of caregivers authorized to pick up the selected child. If the caregiver entered is not in the
    //          caregiver registry, the method directs the user to add a new caregiver and then returns the user to the
    //          beginning of the method.
    private void addAuthorizedToPickUp() {
        System.out.println("Enter full name of child and press Enter:");
        String childFullName = scanner.nextLine();
        Child child = registry.selectChild(childFullName);

        System.out.println("Enter full name of caregiver (First Last) authorized to pick up " + childFullName
                + " and press Enter:");
        String caregiverFullName = scanner.nextLine();
        Caregiver caregiver = registry.selectCaregiver(caregiverFullName);

        if (caregiver != null) {
            child.addAuthorizedToPickUp(caregiver);
            System.out.println(caregiverFullName + " added as caregiver authorized to pick up " + childFullName
                    + "." + "\n");
        } else {
            caregiverIsNull();
            System.out.println("Please try adding an authorized caregiver to pick up a child again.\n");
            addAuthorizedToPickUp();
        }
    }

    // MODIFIES: this, AttendanceSheet
    // EFFECTS: Asks user to confirm they want to reset the attendance sheet. With user confirmation, resets the
    //          attendance sheet: clears the children in the notCheckedIn, checkedIn, and checkedOut list,
    //          and adds all children in the childRegistry to the notCheckedIn list. Otherwise, returns the user to the
    //          options. Prints whether the attendance sheet was reset or the method is returning the user to the
    //          options.
    private void resetAttendance() {
        System.out.println("Are you sure you want to reset the attendance sheet? No children will be checked in.");
        System.out.println("Type 'yes' and press Enter to reset attendance sheet.");
        System.out.println("Type anything else and press Enter to return to options.");
        String confirmChoice = scanner.nextLine();

        boolean isReset = attendanceSheet.reset(confirmChoice, registry.getChildRegistry());

        if (isReset) {
            System.out.println("Attendance sheet reset: no children are checked in.\n");
        } else {
            System.out.println("Returning to options.\n");
        }
    }

    // REQUIRES: childFullName is first and last name separated by a space (case-sensitive).
    // MODIFIES: this, AttendanceSheet, Child
    // EFFECTS: Selects a child in the child registry with a full name matching the user input (case-sensitive). If
    //          child is not checked in yet, then this method removes the child from the list of notCheckedIn and adds
    //          them to the checkedIn list, and sets the child's check-in time to the current time. Prints that the
    //          child checked in at the check-in time. Otherwise, prints that the child was not found in list of
    //          children not yet checked in.
    private void checkInChild() {
        System.out.println("Enter full name of child and press Enter:");
        String childFullName = scanner.nextLine();
        Child child = registry.selectChild(childFullName);

        boolean childFound = attendanceSheet.checkIn(child);

        if (childFound) {
            System.out.println(childFullName + " checked in at " + child.getCheckInTime() + ".\n");
        } else {
            System.out.println(childFullName + " not found in list of children not yet checked in.\n");
        }
    }

    // EFFECTS: Prints out list of children that are not yet checked in, already checked in, and checked out (along
    //          with check in and check out times).
    private void viewAttendance() {
        System.out.println("Not Yet Checked In:");
        for (Child c : attendanceSheet.getNotCheckedIn()) {
            System.out.println(c.getChildFullName());
        }
        System.out.println("\n" + "Checked In:");
        for (Child c : attendanceSheet.getCheckedIn()) {
            System.out.println(c.getChildFullName() + " (checked in at " + c.getCheckInTime() + ")");
        }
        System.out.println("\n" + "Checked Out:");
        for (Child c : attendanceSheet.getCheckedOut()) {
            System.out.println(c.getChildFullName() + " (checked out at " + c.getCheckOutTime() + ")" + "\n");
        }
    }

    // REQUIRES: childFullName are first and last name separated by a space (case-sensitive), and child exists in
    //           childRegistry (!null).
    // EFFECTS: Selects a child in the child registry with a full name matching the user input (case-sensitive),
    //          and prints their primary caregiver's full name, phone number, and email.
    private void viewPrimaryCaregiverInfo() {
        System.out.println("Enter full name of child and press Enter:");
        String childFullName = scanner.nextLine();
        Child child = registry.selectChild(childFullName);

        System.out.println("Primary Caregiver: " + child.getPrimaryCaregiver().getCaregiverFullName());
        System.out.println("Phone Number: " + child.getPrimaryCaregiver().getCaregiverPhoneNum());
        System.out.println("Email: " + child.getPrimaryCaregiver().getCaregiverEmail() + "\n");
    }

    // REQUIRES: childFullName is first and last name separated by a space (case-sensitive), and child exists in
    //           childRegistry (!null).
    // EFFECTS: Selects a child in the child registry with a full name matching the user input (case-sensitive), and
    //          prints information about all caregivers authorized to pick up the selected child (full name, phone
    //          number, and email).
    private void viewAuthorizedToPickUp() {
        System.out.println("Enter full name of child and press Enter:");
        String childFullName = scanner.nextLine();
        Child child = registry.selectChild(childFullName);

        System.out.println("Caregivers authorized to pick up " + childFullName + ":" + "\n");
        for (Caregiver c : child.getAuthorizedToPickUp()) {
            System.out.println("Caregiver: " + c.getCaregiverFullName());
            System.out.println("Phone Number: " + c.getCaregiverPhoneNum());
            System.out.println("Email: " + c.getCaregiverEmail() + "\n");
        }
    }

    // REQUIRES: childToCheckOut exists in child registry (!null).
    // MODIFIES: this, AttendanceSheet, Child, Caregiver (Caregiver only if caregiver entered not found in
    //           caregiverRegistry)
    // EFFECTS: Selects a child in the child registry with a full name matching the user input (case-sensitive), then
    //          checks out the child if both the caregiver entered by the user is authorized to pick up the child and
    //          if the child checked in today. When the child is checked out, this method removes the child from the
    //          list of CheckedIn and adds them to the checkedOut list, and sets the child's check-out time to the
    //          current time. Prints child, check-out time, and caregiver that picked up the child. If the caregiver
    //          entered does not exist, then the method directs the user to add a new caregiver and then returns the
    //          user to the beginning of the method. Prints if the caregiver is not authorized to pick
    //          up the child and/or if the child has not checked in today.
    private void checkOutChild() {
        System.out.println("Enter full name of child and press Enter:");
        String childFullName = scanner.nextLine();
        Child child = registry.selectChild(childFullName);

        System.out.println("Enter full name of caregiver (First Last) picking up " + childFullName + " and hit Enter:");
        String caregiverFullName = scanner.nextLine();
        Caregiver caregiver = registry.selectCaregiver(caregiverFullName);

        String checkedOut = attendanceSheet.checkOut(child, caregiver);

        if (checkedOut.equals("caregiver null")) {
            caregiverIsNull();
            System.out.println("Please try checking out the child again.\n");
            checkOutChild();
        } else if (checkedOut.equals("child checked out")) {
            System.out.println(childFullName + " was checked out at " + child.getCheckOutTime() + " by "
                    + caregiverFullName + ".\n");
        } else if (checkedOut.equals("child not checked in")) {
            System.out.println(childFullName + " has not been checked in today.\n");
        } else if (checkedOut.equals("caregiver not authorized")) {
            System.out.println(caregiverFullName + " is not authorized to pick up " + childFullName + "!\n");
        } else {
            System.out.println(childFullName + " has not been checked in today.");
            System.out.println(caregiverFullName + " is not authorized to pick up " + childFullName + "!\n");
        }
    }

    // REQUIRES: caregiverFullName is first and last name separated by a space (case-sensitive), and caregiver exists
    //           in caregiverRegistry (!null).
    // MODIFIES: this, Registry, Caregiver
    // EFFECTS: Searches caregiver registry for caregiver with full name matching the user input (case-sensitive).
    //          Asks user to confirm they want to remove the selected caregiver. With confirmation from the user,
    //          removes caregiver from registry, and prints that caregiver was removed from the registry. Without user
    //          confirmation, returns the user to the options. Prints if entered caregiver is a primary caregiver for an
    //          existing child, and does not remove caregiver from registry.
    private void removeCaregiverFromRegistry() {
        System.out.println("Enter full name of caregiver (First Last) to remove and press Enter:");
        String caregiverFullName = scanner.nextLine();

        System.out.println("Are you sure you want to remove " + caregiverFullName + " from the caregiver registry?");
        System.out.println("Type 'yes' and press Enter to remove caregiver.");
        System.out.println("Type anything else and press Enter to return to options.");
        String confirmChoice = scanner.nextLine();

        String caregiverRemovedOrChildFullName = registry.removeCaregiver(caregiverFullName, confirmChoice);

        if (caregiverRemovedOrChildFullName.equals("caregiver removed")) {
            System.out.println(caregiverFullName + " removed from the caregiver registry.\n");
        } else if (caregiverRemovedOrChildFullName.equals("choice not confirmed")) {
            System.out.println("Returning to options.\n");
        } else {
            System.out.println(caregiverFullName + " is the primary caregiver listed for "
                    + caregiverRemovedOrChildFullName + ".\n" + "Remove this child before removing their caregiver.\n");
        }
    }

    // REQUIRES: childFullName is first and last name separated by a space (case-sensitive), and child exists
    //           in childRegistry (!null).
    // MODIFIES: this, Registry, Child
    // EFFECTS: Searches child registry for child with full name matching the user input (case-sensitive). Asks user to
    //          confirm they want to remove the selected child. With user confirmation, removes child from registry
    //          and prints that child was removed from the registry. Without confirmation, returns user to the options.
    private void removeChildFromRegistry() {
        System.out.println("Enter full name of child and press Enter:");
        String childFullName = scanner.nextLine();
        Child child = registry.selectChild(childFullName);

        System.out.println("Are you sure you want to remove " + child.getChildFullName() + " from the child registry?");
        System.out.println("Type 'yes' and press Enter to remove child.");
        System.out.println("Type anything else and press Enter to return to options.");
        String confirmChoice = scanner.nextLine();

        String childRemoved = registry.removeChild(childFullName, confirmChoice);
        if (childRemoved.equals("child removed")) {
            System.out.println(child.getChildFullName() + " removed from the child registry.\n");
        } else {
            System.out.println("Returning to options.\n");
        }
    }

}
