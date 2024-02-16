package ui;

import model.Caregiver;
import model.Child;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class AttendanceApp {

    List<Child> childRegistry;
    List<Caregiver> caregiverRegistry;
    List<Child> notCheckedIn;
    List<Child> checkedIn;
    List<Child> checkedOut;

    Scanner scanner = new Scanner(System.in);

    // EFFECTS: Creates new instance of AttendanceApp.
    public AttendanceApp() {
        this.childRegistry = new ArrayList<>();
        this.caregiverRegistry = new ArrayList<>();
        this.checkedIn = new LinkedList<>();
        this.notCheckedIn = new LinkedList<>();
        this.checkedOut = new LinkedList<>();
        selectOption();
    }

    // EFFECTS: Lists options for attendance application.
    private void listOptions() {
        System.out.println("Type the number of an option to select it:");
        System.out.println("1. Add new caregiver to caregiver registry.");
        System.out.println("2. Add new child to child registry.");
        System.out.println("3. Add caregiver (besides primary caregiver) authorized to pick up child.");
        System.out.println("4. Reset attendance sheet (no children checked in).");
        System.out.println("5. Check in child.");
        System.out.println("6. View attendance sheet.");
        System.out.println("7. View primary caregiver information for a child.");
        System.out.println("8. View caregivers authorized to pick up a child.");
        System.out.println("9. Check out child.");
    }

    // REQUIRES: User input must be number from 1 to 9.
    // MODIFIES: ???
    // EFFECTS: Selects method to run based on number selected by user.
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    private void selectOption() {
        listOptions();
        switch (Integer.parseInt(scanner.nextLine())) {
            case 1:
                addNewCaregiverToRegistry();
                break;
            case 2:
                addNewChildToRegistry();
                break;
            case 3:
                addAuthorizedToPickup();
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
        }
    }

    // REQUIRES: Caregiver fullName is first and last name separated by a space, phoneNum is 10 digits
    //           without spaces, and email is not an empty string.
    // MODIFIES: this, Caregiver
    // EFFECTS: Creates new caregiver using user input and adds caregiver to the caregiver registry.
    //          Informs user that caregiver was added to caregiver registry.
    private void addNewCaregiverToRegistry() {
        System.out.println("Enter full name of caregiver (First Last):");
        String caregiverFullName = scanner.nextLine();
        System.out.println("Enter phone number (NNNNNNNNNN) of primary caregiver:");
        Long caregiverPhoneNumber = Long.valueOf(scanner.nextLine());
        System.out.println("Enter email of primary caregiver:");
        String caregiverEmail = scanner.nextLine();

        Caregiver caregiver = new Caregiver(caregiverFullName, caregiverPhoneNumber, caregiverEmail);
        caregiverRegistry.add(caregiver);
        System.out.println(caregiver.getCaregiverFullName() + " added to caregiver registry.");
    }

    // REQUIRES: childFullName is first and last name separated by a space, primaryCaregiver exists.
    // MODIFIES: this, Child (+ Caregiver if not found in caregiverRegister)
    // EFFECTS: Creates new child using user input and adds child to the caregiver registry. Adds child
    //          to the list of children not yet checked in. Informs user that child was added to child registry
    //          and is not yet checked in. If primaryCaregiver is not found in caregiverRegistry, the method
    //          directs the user to add a new caregiver and then returns the user to the beginning of the method.
    private void addNewChildToRegistry() {
        System.out.println("Enter full name of child (First Last):");
        String childFullName = scanner.nextLine();
        System.out.println("Enter full name of primary caregiver (First Last) for " + childFullName + ":");
        String primaryCaregiverFullName = scanner.nextLine();
        if (selectCaregiverInRegistry(primaryCaregiverFullName) != null) {
            Caregiver primaryCaregiver = selectCaregiverInRegistry(primaryCaregiverFullName);
            Child child = new Child(childFullName, primaryCaregiver);
            childRegistry.add(child);
            System.out.println(child.getChildFullName() + " added to child registry.");
            notCheckedIn.add(child);
            System.out.println(child.getChildFullName() + " not yet checked in.");
        } else {
            System.out.println("Caregiver not found in registry! Add a new caregiver and try again.");
            addNewCaregiverToRegistry();
            addNewChildToRegistry();
        }
    }

    // REQUIRES: childFullName is first and last name separated by a space.
    // EFFECTS: Searches child registry for child with full name matching the user input. Returns child if found,
    //          otherwise returns null.
    private Child selectChildInRegistry() {
        System.out.println("Enter full name of child:");
        String childFullName = scanner.nextLine();
        for (Child c : childRegistry) {
            if (c.getChildFullName().equals(childFullName)) {
                return c;
            }
        }
        return null;
    }

    // REQUIRES: caregiverFullName is first and last name separated by a space.
    // EFFECTS: Searches caregiver registry for caregiver with full name matching the user input.
    //          Returns caregiver if found, otherwise returns null.
    private Caregiver selectCaregiverInRegistry(String caregiverFullName) {
        for (Caregiver c : caregiverRegistry) {
            if (c.getCaregiverFullName().equals(caregiverFullName)) {
                return c;
            }
        }
        return null;
    }

    // REQUIRES: childFullName and authorizedCaregiverFullName are first and last name separated by a space, and
    //           child is in child registry.
    // MODIFIES: Child (+ Caregiver if not found in caregiver registry)
    // EFFECTS: Selects a child in the child registry based on user input, then takes user input to add a caregiver
    //          from the caregiver registry to the list of caregivers authorized to pick up the selected child.
    //          If the caregiver entered is not in the caregiver registry, then the method directs the user to
    //          add a new caregiver and then returns the user to the beginning of the method.
    private void addAuthorizedToPickup() {
        Child child = selectChildInRegistry();
        String childFullName = child.getChildFullName();

        System.out.println("Enter full name of caregiver (First Last) authorized to pick up " + childFullName + ":");
        String authorizedCaregiverFullName = scanner.nextLine();
        if (selectCaregiverInRegistry(authorizedCaregiverFullName) != null) {
            Caregiver authorizedCaregiver = selectCaregiverInRegistry(authorizedCaregiverFullName);
            child.addAuthorizedToPickUp(authorizedCaregiver);
            System.out.println(authorizedCaregiver.getCaregiverFullName()
                    + " added as caregiver authorized to pick up"
                    + childFullName);
        } else {
            System.out.println("Caregiver not found in registry! Add a new caregiver and try again.");
            addNewCaregiverToRegistry();
            addAuthorizedToPickup();
        }
    }

    // MODIFIES: this
    // EFFECTS: Resets the attendance sheet: clears the children in the notCheckedIn, checkedIn, and checkedOut list,
    //          and adds all children in the child registry to the notCheckedIn list.
    private void resetAttendance() {
        notCheckedIn.clear();
        checkedIn.clear();
        checkedOut.clear();
        notCheckedIn.addAll(childRegistry);
    }

    // REQUIRES: childFullName is first and last name separated by a space.
    // MODIFIES: this, Child
    // EFFECTS: Selects a child in the child registry based on user input. If child is not checked in yet, then
    //          this method removes the child from the list of notCheckedIn and adds them to the checkedIn list,
    //          and sets the checkout time to the current time.
    private void checkInChild() {
        Child childToCheckIn = selectChildInRegistry();

        for (Child c : notCheckedIn) {
            if (childToCheckIn == c) {
                notCheckedIn.remove(c);
                checkedIn.add(c);
                c.setCheckInTime(LocalTime.now());
                System.out.println(c.getChildFullName() + " checked in at " + c.getCheckInTime() + ".");
            } else {
                System.out.println(childToCheckIn + " not found in list of children not yet checked in today.");
            }
        }
    }

    // EFFECTS: Prints out list of children that are not yet checked in, already checked in, and checked out.
    private void viewAttendance() {
        System.out.println("Not Yet Checked In:");
        for (Child c : notCheckedIn) {
            System.out.println(c.getChildFullName());
        }
        System.out.println("Checked In:");
        for (Child c : checkedIn) {
            System.out.println(c.getChildFullName());
        }
        System.out.println("Checked Out:");
        for (Child c : checkedOut) {
            System.out.println(c.getChildFullName());
        }
    }

    // REQUIRES: childFullName are first and last name separated by a space, and child is found in child registry.
    // EFFECTS: Selects child based on user input, and prints primary caregiver's full name, phone number, and email.
    private void viewPrimaryCaregiverInfo() {
        Child child = selectChildInRegistry();
        System.out.println("Primary Caregiver: " + child.getPrimaryCaregiver().getCaregiverFullName());
        System.out.println("Phone Number: " + child.getPrimaryCaregiver().getCaregiverPhoneNum());
        System.out.println("Email: " + child.getPrimaryCaregiver().getCaregiverEmail());
    }

    // REQUIRES: childFullName are first and last name separated by a space, and child is found in child registry.
    // EFFECTS: Selects child based on user input, and prints information about caregivers authorized to pick up the
    //          selected child (full name, phone number, and email).
    private void viewAuthorizedToPickUp() {
        Child child = selectChildInRegistry();
        System.out.println("Caregivers authorized to pick up " + child + ":");
        for (Caregiver c : child.getAuthorizedToPickUp()) {
            System.out.println("Caregiver: " + c.getCaregiverFullName());
            System.out.println("Phone Number: " + c.getCaregiverPhoneNum());
            System.out.println("Email: " + c.getCaregiverEmail());
        }
    }

    // REQUIRES: caregiverToPickUp and childToCheckOut exists.
    // EFFECTS: Returns true if caregiverToPickUp is in list of caregivers authorized to pick up childToCheckOut,
    //          otherwise returns false.
    private boolean isAuthorizedToPickUp(Caregiver caregiverToPickUp, Child childToCheckOut) {
        for (Caregiver c : childToCheckOut.getAuthorizedToPickUp()) {
            if (caregiverToPickUp == c) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: child exists.
    // EFFECTS: Returns true if child has checked in today, otherwise returns false.
    private boolean childIsCheckedIn(Child child) {
        for (Child c : checkedIn) {
            if (child == c) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: Caregiver does not exist.
    // MODIFIES: this, Caregiver
    // EFFECTS: Prints that caregiver was not found in the registry, and directs the user to add a new
    //          caregiver to the caregiver registry.
    private void caregiverIsNull() {
        System.out.println("Caregiver not found in registry! Add a new caregiver and try again.");
        addNewCaregiverToRegistry();
    }

    // REQUIRES: childToCheckOut exists in child registry.
    // MODIFIES: this, Child (+ Caregiver if not found in caregiver registry)
    // EFFECTS: Selects child based on user input, then checks out the child if both the caregiver entered by the user
    // `        is authorized to pick up the child and if the child checked in today. If the caregiver entered does not
    //          exist, then this method directs the user to add a new caregiver to the registry and then returns the
    //          user to the beginning of this method. Prints if the caregiver is not authorized to pick up the child or
    //          if the child has not checked in today.
    private void checkOutChild() {
        Child childToCheckOut = selectChildInRegistry();

        System.out.println("Enter full name of authorized caregiver (First Last) picking up "
                + childToCheckOut.getChildFullName() + ":");
        Caregiver caregiverToPickUp = selectCaregiverInRegistry(scanner.nextLine());

        if (caregiverToPickUp == null) {
            caregiverIsNull();
            checkOutChild();
        }
        if (isAuthorizedToPickUp(caregiverToPickUp, childToCheckOut) && childIsCheckedIn(childToCheckOut)) {
            checkedIn.remove(childToCheckOut);
            checkedOut.add(childToCheckOut);
            childToCheckOut.setCheckOutTime(LocalTime.now());
            System.out.println(childToCheckOut.getChildFullName() + " checked out at "
                    + childToCheckOut.getCheckOutTime() + " by " + caregiverToPickUp.getCaregiverFullName() + ".");
        } else {
            if (!childIsCheckedIn(childToCheckOut)) {
                System.out.println(childToCheckOut + " has not checked in today.");
            }
            if (!isAuthorizedToPickUp(caregiverToPickUp, childToCheckOut)) {
                System.out.println(caregiverToPickUp.getCaregiverFullName() + " is not authorized to pick up "
                        + childToCheckOut + ".");
            }
        }
    }

}
