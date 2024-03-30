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
        JPanel childRegistryTab = new RegistryTab(controller, "ChildRegistry");
        JPanel caregiverRegistryTab = new RegistryTab(controller, "CaregiverRegistry");

        sidebar.add(childRegistryTab, CHILD_REGISTRY_TAB_INDEX);
        sidebar.setTitleAt(CHILD_REGISTRY_TAB_INDEX, "Child Registry");
        sidebar.add(caregiverRegistryTab, CAREGIVER_REGISTRY_TAB_INDEX);
        sidebar.setTitleAt(CAREGIVER_REGISTRY_TAB_INDEX, "Caregiver Registry");
    }
}
