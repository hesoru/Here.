package ui.pages;

import ui.AttendanceUI;

import javax.swing.*;
import java.awt.*;

public class RegistryWindow extends Window {

    private JTabbedPane sidebar;

    public static final int CHILD_REGISTRY_TAB_INDEX = 0;
    public static final int CAREGIVER_REGISTRY_TAB_INDEX = 1;

    public RegistryWindow(AttendanceUI controller) {
        super("Registry", controller);
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
        JPanel childRegistryTab = new RegistryTab(controller, "ChildRegistry");
        JPanel caregiverRegistryTab = new RegistryTab(controller, "CaregiverRegistry");

        sidebar.add(childRegistryTab, CHILD_REGISTRY_TAB_INDEX);
        sidebar.setTitleAt(CHILD_REGISTRY_TAB_INDEX, "Child Registry");
        sidebar.add(caregiverRegistryTab, CAREGIVER_REGISTRY_TAB_INDEX);
        sidebar.setTitleAt(CAREGIVER_REGISTRY_TAB_INDEX, "Caregiver Registry");
    }
}
