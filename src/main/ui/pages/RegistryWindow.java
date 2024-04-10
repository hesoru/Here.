package ui.pages;

import ui.AttendanceUI;

import javax.swing.*;

// Creates registry window with tabbed interface: displays child registry, caregiver registry, and settings.
public class RegistryWindow extends Window {

    private final JTabbedPane sidebar;

    private static final int WIDTH = 800;
    private static final int HEIGHT = 525;

    private static final int CHILD_REGISTRY_TAB_INDEX = 0;
    private static final int CAREGIVER_REGISTRY_TAB_INDEX = 1;
    private static final int SETTINGS_TAB_INDEX = 2;

    // REQUIRES: Arguments must exist (!=null)
    // EFFECTS: Constructs registry window with tabbed interface: displays child registry, caregiver registry, and
    //          settings.
    public RegistryWindow(AttendanceUI controller) {
        super("Registry", controller);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);
        loadTabs();
        add(sidebar);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: Adds tabs to registry window: child registry, caregiver registry, and settings.
    private void loadTabs() {
        JPanel childRegistryTab = new RegistryTab(controller, "Child Registry");
        JPanel caregiverRegistryTab = new RegistryTab(controller, "Caregiver Registry");
        JPanel settingsTab = new RegistryTab(controller, "Settings");

        sidebar.add(childRegistryTab, CHILD_REGISTRY_TAB_INDEX);
        sidebar.setTitleAt(CHILD_REGISTRY_TAB_INDEX, "Child Registry");
        sidebar.add(caregiverRegistryTab, CAREGIVER_REGISTRY_TAB_INDEX);
        sidebar.setTitleAt(CAREGIVER_REGISTRY_TAB_INDEX, "Caregiver Registry");
        sidebar.add(settingsTab, SETTINGS_TAB_INDEX);
        sidebar.setTitleAt(SETTINGS_TAB_INDEX, "Settings");
    }
}
