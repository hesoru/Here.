package ui.pages;

import model.Caregiver;
import model.Child;
import ui.AttendanceUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

// Creates attendance sheet window with tabbed interface: displays children that are either not yet checked in, checked
// in, or checked out, or displays settings.
public class AttendanceSheetWindow extends Window {

    private final JTabbedPane sidebar;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 525;

    private static final int NOT_YET_CHECKED_IN_TAB_INDEX = 0;
    private static final int CHECKED_IN_TAB_INDEX = 1;
    private static final int CHECKED_OUT_TAB_INDEX = 2;
    private static final int SETTINGS_TAB_INDEX = 3;

    private DefaultTableModel notYetCheckedInSheetModel;
    private DefaultTableModel checkedInSheetModel;
    private DefaultTableModel checkedOutSheetModel;
    private DefaultTableModel authorizedCaregiverSheetModel;

    // REQUIRES: Argument must exist (!=null)
    // EFFECTS: Constructs attendance sheet window with tabbed interface: displays children that are either not yet
    //          checked in, checked in, or checked out, or displays settings.
    public AttendanceSheetWindow(AttendanceUI controller) {
        super("Attendance Sheet", controller);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);
        loadTabs();
        add(sidebar);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Adds tabs to attendance sheet window: not yet checked in, checked in, checked out, and settings.
    private void loadTabs() {
        JPanel notYetCheckedInTab = new AttendanceSheetTab(controller, this, "Not Yet Checked In");
        JPanel checkedInTab = new AttendanceSheetTab(controller, this, "Checked In");
        JPanel checkedOutTab = new AttendanceSheetTab(controller, this,"Checked Out");
        JPanel settingsTab = new AttendanceSheetTab(controller, this,"Settings");

        sidebar.add(notYetCheckedInTab, NOT_YET_CHECKED_IN_TAB_INDEX);
        sidebar.setTitleAt(NOT_YET_CHECKED_IN_TAB_INDEX, "Not Yet Checked In");
        sidebar.add(checkedInTab,CHECKED_IN_TAB_INDEX);
        sidebar.setTitleAt(CHECKED_IN_TAB_INDEX, "Checked In");
        sidebar.add(checkedOutTab, CHECKED_OUT_TAB_INDEX);
        sidebar.setTitleAt(CHECKED_OUT_TAB_INDEX, "Checked Out");
        sidebar.add(settingsTab, SETTINGS_TAB_INDEX);
        sidebar.setTitleAt(SETTINGS_TAB_INDEX, "Settings");
    }

    // REQUIRES: childToCheckInName is first and last name separated by a space (case-sensitive), and matches an
    //           existing child in the child registry.
    // MODIFIES: this, AttendanceSheet, Child
    // EFFECTS: Selects a child in the child registry and checks them in. If child is not checked in yet, then this
    //          method removes the child from the list of notCheckedIn and adds them to the checkedIn list, and sets the
    //          child's check-in time to the current time.
    public void checkInChild(String childToCheckInName) {
        Child childToCheckIn = getController().getRegistry().selectChild(childToCheckInName);
        getController().getAttendanceSheet().checkIn(childToCheckIn);
    }

    // REQUIRES: childFullName and caregiverToCheckOutName is first and last name separated by a space (case-sensitive),
    //           and matches an existing child and caregiver in the registry.
    // MODIFIES: this, AttendanceSheet, Child
    // EFFECTS: Selects a child in the child registry and checks them out. If child is checked in, then this method
    //          removes the child from the list of checkedIn and adds them to the checkedOut list, and sets the child's
    //          check-out time to the current time.
    public void checkOutChild(String childToCheckOutName, String caregiverToCheckOutName) {
        Child childToCheckOut = getController().getRegistry().selectChild(childToCheckOutName);
        Caregiver caregiverToCheckOut = getController().getRegistry().selectCaregiver(caregiverToCheckOutName);

        getController().getAttendanceSheet().checkOut(childToCheckOut, caregiverToCheckOut);
    }

    public void setNotYetCheckedInSheetModel(DefaultTableModel notYetCheckedInSheetModel) {
        this.notYetCheckedInSheetModel = notYetCheckedInSheetModel;
    }

    public void setCheckedInSheetModel(DefaultTableModel checkedInSheetModel) {
        this.checkedInSheetModel = checkedInSheetModel;
    }

    public void setCheckedOutSheetModel(DefaultTableModel checkedOutSheetModel) {
        this.checkedOutSheetModel = checkedOutSheetModel;
    }

    public DefaultTableModel getNotYetCheckedInSheetModel() {
        return notYetCheckedInSheetModel;
    }

    public DefaultTableModel getCheckedInSheetModel() {
        return checkedInSheetModel;
    }

    public DefaultTableModel getCheckedOutSheetModel() {
        return checkedOutSheetModel;
    }
}
