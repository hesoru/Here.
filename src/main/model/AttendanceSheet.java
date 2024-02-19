package model;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

// Contains attendance sheet with children that are not checked in, checked in, and checked out
public class AttendanceSheet {

    private final List<Child> notCheckedIn;
    private final List<Child> checkedIn;
    private final List<Child> checkedOut;

    // EFFECTS: Creates new instance of AttendanceSheet (with list of notCheckedIn, checkedIn, and checkedOut).
    public AttendanceSheet() {
        this.notCheckedIn = new LinkedList<>();
        this.checkedIn = new LinkedList<>();
        this.checkedOut = new LinkedList<>();
    }

    // REQUIRES: child exists (!null).
    // MODIFIES: this, Child
    // EFFECTS: Adds given child to notCheckedIn list.
    public void notCheckedIn(Child child) {
        notCheckedIn.add(child);
    }

    // REQUIRES: child exists (!null).
    // MODIFIES: this, Child
    // EFFECTS: If child is not checked in yet, then this method removes the child from the list of notCheckedIn and
    //          adds them to the checkedIn list, sets the child's check-in time to the current time, and returns true.
    //          If child was not found in notCheckedIn, returns false.
    public boolean checkIn(Child child) {
        for (Child c : notCheckedIn) {
            if (child == c) {
                notCheckedIn.remove(c);
                checkedIn.add(c);
                c.setCheckInTime(LocalTime.now());
                return true;
            }
        }
        return false;
    }

    // REQUIRES: childToCheckOut exists (!null).
    // EFFECTS: Returns true if caregiverToPickUp is in list of caregivers authorized to pick up childToCheckOut,
    //          otherwise returns false.
    public boolean isAuthorizedToPickUp(Caregiver caregiver, Child child) {
        for (Caregiver c : child.getAuthorizedToPickUp()) {
            if (caregiver == c) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: child exists (!null).
    // EFFECTS: Returns true if child is in checkedIn, otherwise returns false.
    public boolean childIsCheckedIn(Child child) {
        for (Child c : checkedIn) {
            if (child == c) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: child exists (!null).
    // MODIFIES: this, Child, Caregiver (Caregiver only if caregiver entered not found in caregiverRegistry)
    // EFFECTS: If given caregiver does not exist (null), returns "caregiver null". If given caregiver is authorized to
    //          pick up the given child and the child is in checkedIn, checks out the given child by moving them to the
    //          checkedOut list, setting their check-out time to the current time, and returning "child checked out". If
    //          the given caregiver is not authorized to pick up the child and the child is not checked in, returns
    //          "child not checked in and caregiver not authorized". If the given caregiver is only not authorized to
    //          pick up the child, returns "caregiver not authorized". Otherwise, returns "child not checked in".
    public String checkOut(Child child, Caregiver caregiver) {
        if (caregiver == null) {
            return "caregiver null";
        } else if (isAuthorizedToPickUp(caregiver, child) && childIsCheckedIn(child)) {
            checkedIn.remove(child);
            checkedOut.add(child);
            child.setCheckOutTime(LocalTime.now());
            return "child checked out";
        } else {
            if (!childIsCheckedIn(child) && !isAuthorizedToPickUp(caregiver, child)) {
                return "child not checked in and caregiver not authorized";
            } else if (!isAuthorizedToPickUp(caregiver, child)) {
                return "caregiver not authorized";
            } else {
                return "child not checked in";
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: If confirmChoice is "yes", the method resets the attendance sheet: clears the children in the
    //          notCheckedIn, checkedIn, and checkedOut list; adds all children in the given childRegistry to the
    //          notCheckedIn list; and returns true. Otherwise, returns false.
    public boolean reset(String confirmChoice, List<Child> childRegistry) {
        if (confirmChoice.equals("yes")) {
            notCheckedIn.clear();
            checkedIn.clear();
            checkedOut.clear();
            notCheckedIn.addAll(childRegistry);
            return true;
        } else {
            return false;
        }
    }

    public List<Child> getNotCheckedIn() {
        return notCheckedIn;
    }

    public List<Child> getCheckedIn() {
        return checkedIn;
    }

    public List<Child> getCheckedOut() {
        return checkedOut;
    }

}
