package ui.pages;

import model.Caregiver;
import model.Child;
import ui.AttendanceUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AttendanceSheetWindow extends Window {

    private JTabbedPane sidebar;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 525;

    public static final int NOT_YET_CHECKED_IN_TAB_INDEX = 0;
    public static final int CHECKED_IN_TAB_INDEX = 1;
    public static final int CHECKED_OUT_TAB_INDEX = 2;
    public static final int SETTINGS_TAB_INDEX = 3;

    DefaultTableModel notYetCheckedInSheetModel;
    DefaultTableModel checkedInSheetModel;
    DefaultTableModel checkedOutSheetModel;
    DefaultTableModel authorizedCaregiverSheetModel;

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

    //MODIFIES: this
    //EFFECTS: adds home tab, settings tab and report tab to this UI
    private void loadTabs() {
        JPanel notYetCheckedInTab = new AttendanceSheetTab(controller, this, "NotYetCheckedIn");
        JPanel checkedInTab = new AttendanceSheetTab(controller, this, "CheckedIn");
        JPanel checkedOutTab = new AttendanceSheetTab(controller, this,"CheckedOut");
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

    // REQUIRES: childFullName is first and last name separated by a space (case-sensitive).
    // MODIFIES: this, AttendanceSheet, Child
    // EFFECTS: Selects a child in the child registry with a full name matching the user input (case-sensitive). If
    //          child is not checked in yet, then this method removes the child from the list of notCheckedIn and adds
    //          them to the checkedIn list, and sets the child's check-in time to the current time. Prints that the
    //          child checked in at the check-in time. Otherwise, prints that the child was not found in list of
    //          children not yet checked in.
    public void checkInChild(String childToCheckInName) {
        Child childToCheckIn = getController().getRegistry().selectChild(childToCheckInName);
        getController().getAttendanceSheet().checkIn(childToCheckIn);
    }

    public void checkOutChild(String childToCheckOutName, String caregiverToCheckOutName) {
        Child childToCheckOut = getController().getRegistry().selectChild(childToCheckOutName);
        Caregiver caregiverToCheckOut = getController().getRegistry().selectCaregiver(caregiverToCheckOutName);

        getController().getAttendanceSheet().checkOut(childToCheckOut, caregiverToCheckOut);
    }

    public DefaultTableModel getNotYetCheckedInSheetModel() {
        return notYetCheckedInSheetModel;
    }

    public DefaultTableModel getCheckedInSheetModel() {
        return checkedInSheetModel;
    }

    public DefaultTableModel getCheckedInSheet() {
        return checkedInSheetModel;
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

    public void setAuthorizedCaregiverSheetModel(DefaultTableModel authorizedCaregiverSheetModel) {
        this.authorizedCaregiverSheetModel = authorizedCaregiverSheetModel;
    }

    public DefaultTableModel getCheckedOutSheetModel() {
        return checkedOutSheetModel;
    }
//    DefaultTableModel authorizedCaregiverSheetModel;
}
