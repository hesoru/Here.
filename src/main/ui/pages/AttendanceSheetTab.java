package ui.pages;

import model.Caregiver;
import model.Child;
import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalTime;

public class AttendanceSheetTab extends Tab {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 525;

    private JPanel panel0;

    private SelectAuthorizedCaregiverWindow authorizedCaregiverWindow;

    private DefaultTableModel notYetCheckedInSheetModel = new DefaultTableModel(0, 4);
    private DefaultTableModel checkedInSheetModel = new DefaultTableModel(0, 2);
    private DefaultTableModel checkedOutSheetModel = new DefaultTableModel(0, 3);
    private DefaultTableModel authorizedCaregiverSheetModel;

    DefaultTableModel tableModel;
    private AttendanceSheetWindow attendanceSheetWindow;

    private JTable notYetCheckedInSheet;
    private JTable checkedInSheet;
    private JTable checkedOutSheet;

    private String childToCheckInName;
    Child childToCheckIn;
    private String childToCheckOutName;
    private Child childToCheckOut;
    String caregiverToCheckOutName;
    Caregiver caregiverToCheckOut;

    public AttendanceSheetTab(AttendanceUI controller, AttendanceSheetWindow attendanceSheetWindow, String tabType) {
        super(controller);
        this.attendanceSheetWindow = attendanceSheetWindow;

        panel0 = new JPanel(new BorderLayout());

        if (tabType.equals("NotYetCheckedIn")) {
            placeNotYetCheckedInSheet();
            placeNotYetCheckedInButtons();
        }
        if (tabType.equals("CheckedIn")) {
            placeCheckedInSheet();
            placeCheckedInButtons();
        }
        if (tabType.equals("CheckedOut")) {
            placeCheckedOutSheet();
        }
        if (tabType.equals("Settings")) {
            placeSettingsButtons();
        }

        add(panel0);
    }

    public void placeNotYetCheckedInSheet() {
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
        attendanceSheetWindow.setNotYetCheckedInSheetModel(notYetCheckedInSheetModel);
        notYetCheckedInSheet = new JTable(notYetCheckedInSheetModel) {
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };

        notYetCheckedInSheet.setAutoCreateRowSorter(true);
        notYetCheckedInSheet.setRowHeight(30);
        notYetCheckedInSheet.setFillsViewportHeight(true);
        notYetCheckedInSheet.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(notYetCheckedInSheet,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setSize(panel0.getWidth(), panel0.getHeight());
        panel0.add(scrollPane, BorderLayout.CENTER);
    }

    public void placeNotYetCheckedInButtons() {
        JButton b1 = new JButton(ButtonNames.CHECK_IN.getValue());

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.setLayout(new FlowLayout());
        buttonRow.setSize(WIDTH, HEIGHT / 5);
        panel0.add(buttonRow, BorderLayout.SOUTH);

        b1.addActionListener(e -> {
            int selected = notYetCheckedInSheet.getSelectedRow();

            if (selected != -1) {
                childToCheckInName = notYetCheckedInSheetModel.getValueAt(selected, 0).toString();
                attendanceSheetWindow.checkInChild(childToCheckInName);
                Child childToCheckIn = getController().getRegistry().selectChild(childToCheckInName);

                Object[] rowData = new Object[2];
                rowData[0] = childToCheckInName;
                rowData[1] = childToCheckIn.getCheckInTime();
                attendanceSheetWindow.getCheckedInSheetModel().addRow(rowData);
                attendanceSheetWindow.getNotYetCheckedInSheetModel().removeRow(selected);

                attendanceSheetWindow.getNotYetCheckedInSheetModel().fireTableDataChanged();
                attendanceSheetWindow.getCheckedInSheetModel().fireTableDataChanged();

                JOptionPane.showMessageDialog(null, childToCheckInName + " checked in at "
                        + childToCheckIn.getCheckInTime());
            }
        });
    }

    public void placeCheckedInSheet() {
        Object[] columnNames = {"Checked In", "Check In Time"};
        checkedInSheetModel.setColumnIdentifiers(columnNames);
        for (Child c : getController().getAttendanceSheet().getCheckedIn()) {
            Object[] o = new Object[2];
            o[0] = c.getFullName();
            o[1] = c.getCheckInTime();
            checkedInSheetModel.addRow(o);
        }
        attendanceSheetWindow.setCheckedInSheetModel(checkedInSheetModel);
        checkedInSheet = new JTable(checkedInSheetModel) {
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };

        checkedInSheet.setAutoCreateRowSorter(true);
        checkedInSheet.setRowHeight(30);
        checkedInSheet.setFillsViewportHeight(true);
        checkedInSheet.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(checkedInSheet,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setSize(panel0.getWidth(), panel0.getHeight());
        panel0.add(scrollPane, BorderLayout.CENTER);
    }

    public void placeCheckedInButtons() {
        JButton b1 = new JButton(ButtonNames.CHECK_OUT.getValue());

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.setLayout(new FlowLayout());
        buttonRow.setSize(WIDTH, HEIGHT / 5);
        panel0.add(buttonRow, BorderLayout.SOUTH);

        b1.addActionListener(e -> {
            int selected = checkedInSheet.getSelectedRow();

            if (selected != -1) {
                childToCheckOutName = checkedInSheet.getValueAt(selected, 0).toString();
                childToCheckOut = getController().getRegistry().selectChild(childToCheckOutName);
                authorizedCaregiverWindow = new SelectAuthorizedCaregiverWindow(attendanceSheetWindow, this, getController(), selected);
            }
        });
    }

    public void setAuthorizedCaregiverSheetModel(DefaultTableModel authorizedCaregiverSheetModel) {
        this.authorizedCaregiverSheetModel = authorizedCaregiverSheetModel;
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

    public void placeCheckedOutSheet() {
        Object[] columnNames = {"Checked Out", "Check Out Time", "Caregiver Checking Out"};
        checkedOutSheetModel.setColumnIdentifiers(columnNames);
        for (Child c : getController().getAttendanceSheet().getCheckedOut()) {
            Object[] o = new Object[3];
            o[0] = c.getFullName();
            o[1] = c.getCheckOutTime();
            o[2] = c.getCheckOutCaregiver();
            checkedOutSheetModel.addRow(o);
        }
        attendanceSheetWindow.setCheckedOutSheetModel(checkedOutSheetModel);
        checkedOutSheet = new JTable(checkedOutSheetModel) {
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };

        checkedOutSheet.setAutoCreateRowSorter(true);
        checkedOutSheet.setRowHeight(30);
        checkedOutSheet.setFillsViewportHeight(true);
        checkedOutSheet.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(checkedOutSheet,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setSize(panel0.getWidth(), panel0.getHeight());
        panel0.add(scrollPane, BorderLayout.CENTER);
    }

    public void placeSettingsButtons() {
        JButton b1 = new JButton(ButtonNames.RESET.getValue());
        JButton b2 = new JButton(ButtonNames.SAVE.getValue());

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.setLayout(new FlowLayout());
        buttonRow.setSize(WIDTH, HEIGHT / 5);
        panel0.add(buttonRow, BorderLayout.CENTER);

        b1.addActionListener(e -> {
            super.getController().resetAttendance();
            attendanceSheetWindow.getNotYetCheckedInSheetModel().setRowCount(0);
            attendanceSheetWindow.getCheckedInSheetModel().setRowCount(0);
            attendanceSheetWindow.getCheckedOutSheetModel().setRowCount(0);
            for (Child c : getController().getAttendanceSheet().getNotCheckedIn()) {
                Object[] o = new Object[4];
                o[0] = c.getFullName();
                o[1] = c.getPrimaryCaregiver().getFullName();
                o[2] = c.getPrimaryCaregiver().getPhoneNum();
                o[3] = c.getPrimaryCaregiver().getEmail();
                attendanceSheetWindow.getNotYetCheckedInSheetModel().addRow(o);
            }
            attendanceSheetWindow.getNotYetCheckedInSheetModel().fireTableDataChanged();
//            attendanceSheetWindow.getCheckedInSheetModel().fireTableDataChanged();
//            attendanceSheetWindow.getCheckedOutSheetModel().fireTableDataChanged();
        });

        b2.addActionListener(e -> {
            super.getController().saveState();
        });
    }

    public Child getChildToCheckOut() {
        return childToCheckOut;
    }

    public DefaultTableModel getCheckedOutSheetModel() {
        return checkedOutSheetModel;
    }
}
