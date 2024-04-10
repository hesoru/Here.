package ui.pages;

import model.Child;
import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Creates tab for attendance sheet window: displays children that are either not yet checked in, checked in, or
// checked out, or displays settings.
public class AttendanceSheetTab extends Tab {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 525;

    private final JPanel panel0;

    private final DefaultTableModel notYetCheckedInSheetModel = new DefaultTableModel(0, 4);
    private final DefaultTableModel checkedInSheetModel = new DefaultTableModel(0, 2);
    private final DefaultTableModel checkedOutSheetModel = new DefaultTableModel(0, 3);

    private final AttendanceSheetWindow attendanceSheetWindow;

    private JTable notYetCheckedInSheet;
    private JTable checkedInSheet;
    private JTable checkedOutSheet;

    private String childToCheckInName;
    private String childToCheckOutName;
    private Child childToCheckOut;

    // REQUIRES: Arguments must exist (!=null), and tabType must be either "Not Yet Checked In", "Checked In",
    //           "Checked Out", or "Settings" only.
    // EFFECTS: Constructs a new tab for attendance sheet window: displays children that are either not yet checked in,
    //          checked in, or checked out, or displays settings.
    public AttendanceSheetTab(AttendanceUI controller, AttendanceSheetWindow attendanceSheetWindow, String tabType) {
        super(controller);
        this.attendanceSheetWindow = attendanceSheetWindow;

        panel0 = new JPanel(new BorderLayout());

        if (tabType.equals("Not Yet Checked In")) {
            placeNotYetCheckedInSheet();
            placeNotYetCheckedInButtons();
        }
        if (tabType.equals("Checked In")) {
            placeCheckedInSheet();
            placeCheckedInButtons();
        }
        if (tabType.equals("Checked Out")) {
            placeCheckedOutSheet();
        }
        if (tabType.equals("Settings")) {
            placeSettingsButtons();
        }

        add(panel0);
    }

    // MODIFIES: this
    // EFFECTS: Places sheet on "Not Yet Checked In" attendance sheet tab: displays children that are not yet checked
    //          in.
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

        JScrollPane scrollPane = new JScrollPane(notYetCheckedInSheet,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel0.add(scrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: this, AttendanceSheet, Child
    // EFFECTS: Places button on "Not Yet Checked In" attendance sheet tab: allows user to check in selected child.
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

    // MODIFIES: this
    // EFFECTS: Places sheet on "Checked In" attendance sheet tab: displays children that are checked in.
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

        JScrollPane scrollPane = new JScrollPane(checkedInSheet,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel0.add(scrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: this, AttendanceSheet, Child
    // EFFECTS: Places button on "Checked In" attendance sheet tab: allows user to check out selected child and select
    //          caregiver picking up child.
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
                new SelectAuthorizedCaregiverWindow(attendanceSheetWindow, this, getController(), selected);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: Places sheet on "Checked Out" attendance sheet tab: displays children that are checked out.
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

        JScrollPane scrollPane = new JScrollPane(checkedOutSheet,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel0.add(scrollPane, BorderLayout.CENTER);
    }

    // MODIFIES: this, AttendanceSheet, Child, JsonWriter
    // EFFECTS: Places buttons on "Settings" attendance sheet tab: allows user to reset attendance sheet (so all
    //          children are not yet checked in) or save app data.
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
            resetAttendanceSheet();
        });

        b2.addActionListener(e -> {
            super.getController().saveState();
        });
    }

    // MODIFIES: this
    // EFFECTS: Resets attendance sheet tables displayed on the attendance sheet window (so all children are not yet
    //          checked in)
    public void resetAttendanceSheet() {
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
        attendanceSheetWindow.getCheckedInSheetModel().fireTableDataChanged();
        attendanceSheetWindow.getCheckedOutSheetModel().fireTableDataChanged();
    }

    public Child getChildToCheckOut() {
        return childToCheckOut;
    }

}
