package ui.pages;

import model.Caregiver;
import model.Child;
import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AttendanceSheetTab extends Tab {

    GridBagConstraints grid0;
    JPanel panel1;
    GridBagConstraints grid1;
    JFrame authorizedCaregiverWindow;

    DefaultTableModel notYetCheckedInSheetModel;
    DefaultTableModel checkedInSheetModel;
    DefaultTableModel checkedOutSheetModel;
    DefaultTableModel authorizedCaregiverSheetModel;

    JTable notYetCheckedInSheet;
    JTable checkedInSheet;
    JTable checkedOutSheet;
    JTable authorizedCaregiverSheet;

    String childToCheckInName;
    Child childToCheckIn;
    String childToCheckOutName;
    Child childToCheckOut;
    String caregiverToCheckOutName;
    Caregiver caregiverToCheckOut;

    public AttendanceSheetTab(AttendanceUI controller, String sheetType) {
        super(controller);

        grid0 = new GridBagConstraints();
        grid0.fill = GridBagConstraints.HORIZONTAL;

        if (sheetType.equals("NotYetCheckedIn")) {
            placeNotYetCheckedInSheet();
            placeNotYetCheckedInButtons();
        }
        if (sheetType.equals("CheckedIn")) {
            placeCheckedInSheet();
            placeCheckedInButtons();
        }
        if (sheetType.equals("CheckedOut")) {
            placeCheckedOutSheet();
        }
        if (sheetType.equals("Settings")) {
            placeSettingsButtons();
        }
    }

    public void placeNotYetCheckedInSheet() {
        notYetCheckedInSheetModel = new DefaultTableModel(0, 4);

        Object[] columnNames = {"Not Yet Checked In", "Primary Caregiver", "Caregiver Phone", "Caregiver Email"};
        notYetCheckedInSheetModel.setColumnIdentifiers(columnNames);

        for (Child c : getController().getAttendanceSheet().getNotCheckedIn()) {
            Object[] o = new Object[4];
            o[0] = c.getFullName();
            o[1] = c.getPrimaryCaregiver().getFullName();
            o[2] = c.getPrimaryCaregiver().getPhoneNum();
            o[3] = c.getPrimaryCaregiver().getEmail();
            notYetCheckedInSheetModel.addRow(o);
        }

        notYetCheckedInSheet = new JTable(notYetCheckedInSheetModel);

        JScrollPane scrollPane = new JScrollPane(notYetCheckedInSheet);
        grid0.gridx = 0;
        grid0.gridwidth = 5;
        grid0.gridy = 0;
        grid0.gridheight = 4;
        notYetCheckedInSheet.setFillsViewportHeight(true);
        add(scrollPane, grid0);
    }

    public void placeNotYetCheckedInButtons() {
        JButton b1 = new JButton(ButtonNames.CHECK_IN.getValue());

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.setLayout(new FlowLayout());
        grid0.gridx = 2;
        grid0.gridy = 4;
        add(buttonRow, grid0);

        b1.addActionListener(e -> {
            int selected = notYetCheckedInSheet.getSelectedRow();

            Object[] rowData = new Object[2];
            rowData[0] = childToCheckInName;
            rowData[1] = childToCheckIn.getCheckInTime();

            if (selected != -1) {
                childToCheckInName = notYetCheckedInSheetModel.getValueAt(selected, 0).toString();
                checkInChild();
                checkedInSheetModel.addRow(rowData);
                notYetCheckedInSheetModel.removeRow(selected);
                notYetCheckedInSheetModel.fireTableDataChanged();
                checkedInSheetModel.fireTableDataChanged();
                JOptionPane.showMessageDialog(null, childToCheckInName + " checked in at "
                        + childToCheckIn.getCheckInTime());
            }
        });
    }

    // REQUIRES: childFullName is first and last name separated by a space (case-sensitive).
    // MODIFIES: this, AttendanceSheet, Child
    // EFFECTS: Selects a child in the child registry with a full name matching the user input (case-sensitive). If
    //          child is not checked in yet, then this method removes the child from the list of notCheckedIn and adds
    //          them to the checkedIn list, and sets the child's check-in time to the current time. Prints that the
    //          child checked in at the check-in time. Otherwise, prints that the child was not found in list of
    //          children not yet checked in.
    public void checkInChild() {
        childToCheckIn = getController().getRegistry().selectChild(childToCheckInName);
        getController().getAttendanceSheet().checkIn(childToCheckIn);
    }

    public void placeCheckedInSheet() {
        checkedInSheetModel = new DefaultTableModel(0, 2);

        Object[] columnNames = {"Checked In", "Check In Time"};
        checkedInSheetModel.setColumnIdentifiers(columnNames);

        for (Child c : getController().getAttendanceSheet().getCheckedIn()) {
            Object[] o = new Object[2];
            o[0] = c.getFullName();
            o[1] = c.getCheckInTime();
            checkedInSheetModel.addRow(o);
        }

        checkedInSheet = new JTable(checkedInSheetModel);

        JScrollPane scrollPane = new JScrollPane(checkedInSheet);
        grid0.gridx = 0;
        grid0.gridwidth = 5;
        grid0.gridy = 0;
        grid0.gridheight = 4;
        checkedInSheet.setFillsViewportHeight(true);
        add(scrollPane, grid0);
    }

    public void placeCheckedInButtons() {
        JButton b1 = new JButton(ButtonNames.CHECK_OUT.getValue());

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.setLayout(new FlowLayout());
        grid0.gridx = 2;
        grid0.gridy = 4;
        add(buttonRow, grid0);

        b1.addActionListener(e -> {
            int selected = checkedInSheet.getSelectedRow();

            if (selected != -1) {
                childToCheckOutName = authorizedCaregiverSheetModel.getValueAt(selected, 0).toString();
                authorizedCaregiverWindow(childToCheckOut);
            }

            Object[] rowData = new Object[2];
            rowData[0] = childToCheckOutName;
            rowData[1] = childToCheckOut.getCheckOutTime();

            if (selected != -1) {
                childToCheckOutName = authorizedCaregiverSheetModel.getValueAt(selected, 0).toString();
                checkOutChild();
                checkedOutSheetModel.addRow(rowData);
                checkedInSheetModel.removeRow(selected);
                checkedInSheetModel.fireTableDataChanged();
                checkedOutSheetModel.fireTableDataChanged();
                JOptionPane.showMessageDialog(null, childToCheckOutName + " checked out at "
                        + childToCheckOut.getCheckOutTime() + " by " + caregiverToCheckOutName);
            }
        });
    }

    public void authorizedCaregiverWindow(Child child) {
        authorizedCaregiverWindow = new JFrame();
        authorizedCaregiverWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        grid1 = new GridBagConstraints();
        grid1.fill = GridBagConstraints.HORIZONTAL;

        placeAuthorizedCaregiverSheet(child);
        placeAuthorizedCaregiverButtons(child);

        authorizedCaregiverWindow.add(panel1);
    }

    public void placeAuthorizedCaregiverSheet(Child child) {
        authorizedCaregiverSheetModel = new DefaultTableModel(0, 3);

        Object[] columnNames = {"Authorized To Pick Up", "Phone", "Email"};
        authorizedCaregiverSheetModel.setColumnIdentifiers(columnNames);

        for (Caregiver c : child.getAuthorizedToPickUp()) {
            Object[] o = new Object[3];
            o[0] = c.getFullName();
            o[1] = c.getPhoneNum();
            o[2] = c.getEmail();
            authorizedCaregiverSheetModel.addRow(o);
        }

        authorizedCaregiverSheet = new JTable(authorizedCaregiverSheetModel);

        JScrollPane scrollPane = new JScrollPane(authorizedCaregiverSheet);
        grid1.gridx = 0;
        grid1.gridwidth = 5;
        grid1.gridy = 0;
        grid1.gridheight = 4;
        authorizedCaregiverSheet.setFillsViewportHeight(true);
        panel1.add(scrollPane, grid1);
    }

    public void placeAuthorizedCaregiverButtons(Child child) {
        JButton b1 = new JButton(ButtonNames.SELECT_PICKUP.getValue());

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.setLayout(new FlowLayout());
        grid1.gridx = 2;
        grid1.gridy = 4;
        panel1.add(buttonRow, grid1);

        b1.addActionListener(e -> {
            int selected = authorizedCaregiverSheet.getSelectedRow();

            Object[] rowData = new Object[3];
            rowData[0] = child.getFullName();
            rowData[1] = childToCheckOut.getCheckOutTime();
            rowData[2] = caregiverToCheckOutName;

            if (selected != -1) {
                caregiverToCheckOutName = authorizedCaregiverSheetModel.getValueAt(selected, 0).toString();
                checkOutChild();
                checkedOutSheetModel.addRow(rowData);
                authorizedCaregiverSheetModel.removeRow(selected);
                authorizedCaregiverSheetModel.fireTableDataChanged();
                checkedOutSheetModel.fireTableDataChanged();
                JOptionPane.showMessageDialog(null, childToCheckOutName + " checked out at "
                        + childToCheckOut.getCheckOutTime() + " by " + caregiverToCheckOutName);
                authorizedCaregiverWindow.dispose();
            }
        });
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
        childToCheckOut = getController().getRegistry().selectChild(childToCheckOutName);
        caregiverToCheckOut = childToCheckOut.getCheckOutCaregiver();

        getController().getAttendanceSheet().checkOut(childToCheckOut, caregiverToCheckOut);
    }

    public void placeCheckedOutSheet() {
        checkedOutSheetModel = new DefaultTableModel(0, 3);

        Object[] columnNames = {"Checked Out", "Check Out Time", "Caregiver Checking Out"};
        checkedOutSheetModel.setColumnIdentifiers(columnNames);

        for (Child c : getController().getAttendanceSheet().getCheckedOut()) {
            Object[] o = new Object[3];
            o[0] = c.getFullName();
            o[1] = c.getCheckOutTime();
            o[2] = c.getCheckOutCaregiver();
            checkedOutSheetModel.addRow(o);
        }

        checkedOutSheet = new JTable(checkedOutSheetModel);

        JScrollPane scrollPane = new JScrollPane(checkedOutSheet);
        grid0.gridx = 0;
        grid0.gridwidth = 5;
        grid0.gridy = 0;
        grid0.gridheight = 4;
        checkedOutSheet.setFillsViewportHeight(true);
        add(scrollPane, grid0);
    }

    public void placeSettingsButtons() {
        JButton b1 = new JButton(ButtonNames.RESET.getValue());

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.setLayout(new FlowLayout());
        grid0.gridy = 4;
        add(buttonRow, grid0);

        b1.addActionListener(e -> {
            super.getController().resetAttendance();
            notYetCheckedInSheetModel.fireTableDataChanged();
            checkedInSheetModel.fireTableDataChanged();
            checkedOutSheetModel.fireTableDataChanged();
        });
    }
}
