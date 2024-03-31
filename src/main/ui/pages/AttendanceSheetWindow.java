package ui.pages;

import ui.AttendanceUI;

import javax.swing.*;
import java.awt.*;

public class AttendanceSheetWindow extends Window {

    private JTabbedPane sidebar;

    public static final int WIDTH = 700;
    public static final int HEIGHT = 500;

    public static final int NOT_YET_CHECKED_IN_TAB_INDEX = 0;
    public static final int CHECKED_IN_TAB_INDEX = 1;
    public static final int CHECKED_OUT_TAB_INDEX = 2;
    public static final int SETTINGS_TAB_INDEX = 3;

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
        JPanel notYetCheckedInTab = new AttendanceSheetTab(controller, "NotYetCheckedIn");
        JPanel checkedInTab = new AttendanceSheetTab(controller, "CheckedIn");
        JPanel checkedOutTab = new AttendanceSheetTab(controller, "CheckedOut");
        JPanel settingsTab = new AttendanceSheetTab(controller, "Settings");

        sidebar.add(notYetCheckedInTab, NOT_YET_CHECKED_IN_TAB_INDEX);
        sidebar.setTitleAt(NOT_YET_CHECKED_IN_TAB_INDEX, "Not Yet Checked In");
        sidebar.add(checkedInTab,CHECKED_IN_TAB_INDEX);
        sidebar.setTitleAt(CHECKED_IN_TAB_INDEX, "Checked In");
        sidebar.add(checkedOutTab, CHECKED_OUT_TAB_INDEX);
        sidebar.setTitleAt(CHECKED_OUT_TAB_INDEX, "Checked Out");
        sidebar.add(settingsTab, SETTINGS_TAB_INDEX);
        sidebar.setTitleAt(SETTINGS_TAB_INDEX, "Settings");
    }
}
