package ui.pages;

import ui.AttendanceUI;

import javax.swing.*;
import java.awt.*;

public class AttendanceSheetWindow extends Window {

    private JTabbedPane sidebar;

    public static final int NOT_YET_CHECKED_IN_TAB_INDEX = 0;
    public static final int CHECKED_IN_TAB_INDEX = 1;
    public static final int CHECKED_OUT_TAB_INDEX = 2;

    public AttendanceSheetWindow(AttendanceUI controller) {
        super("Attendance Sheet", controller);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        getContentPane().setBackground(new Color(220, 240, 255));
        setLayout(new GridLayout(3, 1));
        ImageIcon icon = new ImageIcon("src\\App_Icon.png");
        setIconImage(icon.getImage());

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

        sidebar.add(notYetCheckedInTab, NOT_YET_CHECKED_IN_TAB_INDEX);
        sidebar.setTitleAt(NOT_YET_CHECKED_IN_TAB_INDEX, "Not Yet Checked In");
        sidebar.add(checkedInTab,CHECKED_IN_TAB_INDEX);
        sidebar.setTitleAt(CHECKED_IN_TAB_INDEX, "Checked In");
        sidebar.add(checkedOutTab, CHECKED_OUT_TAB_INDEX);
        sidebar.setTitleAt(CHECKED_OUT_TAB_INDEX, "Checked Out");
    }

    //MODIFIES: this
    //EFFECTS: adds home tab, settings tab and report tab to this UI
    private void updateTabs() {
        JPanel notYetCheckedInTab = new AttendanceSheetTab(controller, "NotYetCheckedIn");
        JPanel checkedInTab = new AttendanceSheetTab(controller, "CheckedIn");
        JPanel checkedOutTab = new AttendanceSheetTab(controller, "CheckedOut");

        sidebar.add(notYetCheckedInTab, NOT_YET_CHECKED_IN_TAB_INDEX);
        sidebar.setTitleAt(NOT_YET_CHECKED_IN_TAB_INDEX, "Not Yet Checked In");
        sidebar.add(checkedInTab,CHECKED_IN_TAB_INDEX);
        sidebar.setTitleAt(CHECKED_IN_TAB_INDEX, "Checked In");
        sidebar.add(checkedOutTab, CHECKED_OUT_TAB_INDEX);
        sidebar.setTitleAt(CHECKED_OUT_TAB_INDEX, "Checked Out");
    }
}
