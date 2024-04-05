package ui.pages;

import model.Caregiver;
import model.Child;
import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RegistryTab extends Tab {

    // add privacy labels!

    public static final int WIDTH = 800;
    public static final int HEIGHT = 525;

    private JPanel panel0;

    private DefaultTableModel childRegistrySheetModel;
    private DefaultTableModel caregiverRegistrySheetModel;

    private JTable childRegistrySheet;
    private JTable caregiverRegistrySheet;

    private String childToRemoveName;
    private String caregiverName;

    public RegistryTab(AttendanceUI controller, String tabType) {
        super(controller);

        panel0 = new JPanel(new BorderLayout());

        if (tabType.equals("Child Registry")) {
            placeChildRegistrySheet();
            placeChildRegistryButtons();
        }
        if (tabType.equals("Caregiver Registry")) {
            createCaregiverRegistrySheet();
            placeCaregiverRegistryButtons();
        }
        if (tabType.equals("Settings")) {
            placeSettingsButtons();
        }

        add(panel0);
    }

    public void placeChildRegistrySheet() {
        childRegistrySheetModel = new DefaultTableModel(0, 4);
        Object[] columnNames = {"Child", "Primary Caregiver", "Caregiver Phone Number", "Caregiver Email"};
        childRegistrySheetModel.setColumnIdentifiers(columnNames);
        for (Child c : getController().getRegistry().getChildRegistry()) {
            Object[] o = new Object[4];
            o[0] = c.getFullName();
            o[1] = c.getPrimaryCaregiver().getFullName();
            o[2] = c.getPrimaryCaregiver().getPhoneNum();
            o[3] = c.getPrimaryCaregiver().getEmail();
            childRegistrySheetModel.addRow(o);
        }
        childRegistrySheet = new JTable(childRegistrySheetModel) {
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };

        childRegistrySheet.setAutoCreateRowSorter(true);
        childRegistrySheet.setRowHeight(30);
        childRegistrySheet.setFillsViewportHeight(true);
        childRegistrySheet.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(childRegistrySheet,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setSize(panel0.getWidth(), panel0.getHeight());
        panel0.add(scrollPane, BorderLayout.CENTER);
    }

    public void placeChildRegistryButtons() {
        JButton b1 = new JButton(ButtonNames.ADD_CHILD.getValue());
        JButton b2 = new JButton(ButtonNames.REMOVE_CHILD.getValue());

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.setLayout(new FlowLayout());
        buttonRow.setSize(WIDTH, HEIGHT / 5);
        panel0.add(buttonRow, BorderLayout.SOUTH);

        b1.addActionListener(e -> {
            new AddNewPersonWindow(childRegistrySheetModel, caregiverRegistrySheetModel, getController(), "Child");
        });

        b2.addActionListener(e -> {
            int selected = childRegistrySheet.getSelectedRow();

            if (selected != -1) {
                childToRemoveName = childRegistrySheetModel.getValueAt(selected, 0).toString();
                removeChildFromRegistry();
                childRegistrySheetModel.removeRow(selected);
                childRegistrySheetModel.fireTableDataChanged();
                JOptionPane.showMessageDialog(null, childToRemoveName + " removed successfully.");
            }
        });
    }

    // REQUIRES: childFullName is first and last name separated by a space (case-sensitive), and child exists
    //           in childRegistry (!null).
    // MODIFIES: this, Registry, Child
    // EFFECTS: Searches child registry for child with full name matching the user input (case-sensitive). Asks user to
    //          confirm they want to remove the selected child. With user confirmation, removes child from registry
    //          and prints that child was removed from the registry. Without confirmation, returns user to the options.
    public void removeChildFromRegistry() {
        getController().getRegistry().removeChild(childToRemoveName, "yes");
    }

    public void createCaregiverRegistrySheet() {
        caregiverRegistrySheetModel = new DefaultTableModel(0, 3);
        Object[] columnNames = {"Caregiver", "Phone", "Email"};
        caregiverRegistrySheetModel.setColumnIdentifiers(columnNames);
        for (Caregiver c : getController().getRegistry().getCaregiverRegistry()) {
            Object[] o = new Object[3];
            o[0] = c.getFullName();
            o[1] = c.getPhoneNum();
            o[2] = c.getEmail();
            caregiverRegistrySheetModel.addRow(o);
        }
        caregiverRegistrySheet = new JTable(caregiverRegistrySheetModel) {
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };

        caregiverRegistrySheet.setAutoCreateRowSorter(true);
        caregiverRegistrySheet.setRowHeight(30);
        caregiverRegistrySheet.setFillsViewportHeight(true);
        caregiverRegistrySheet.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        JScrollPane scrollPane = new JScrollPane(caregiverRegistrySheet,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel0.add(scrollPane, BorderLayout.CENTER);
    }

    public void placeCaregiverRegistryButtons() {
        JButton b1 = new JButton(ButtonNames.ADD_CAREGIVER.getValue());
        JButton b2 = new JButton(ButtonNames.REMOVE_CAREGIVER.getValue());

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.setLayout(new FlowLayout());
        buttonRow.setSize(WIDTH, HEIGHT / 5);
        panel0.add(buttonRow, BorderLayout.SOUTH);

        b1.addActionListener(e -> {
            new AddNewPersonWindow(childRegistrySheetModel, caregiverRegistrySheetModel, getController(), "Caregiver");
        });

        b2.addActionListener(e -> {
            int selected = caregiverRegistrySheet.getSelectedRow();

            if (selected != -1) {
                caregiverName = caregiverRegistrySheetModel.getValueAt(selected, 0).toString();
                removeCaregiverFromRegistry();
                caregiverRegistrySheetModel.removeRow(selected);
                caregiverRegistrySheetModel.fireTableDataChanged();
                JOptionPane.showMessageDialog(null, caregiverName + " removed successfully.");
            }
        });
    }

    public DefaultTableModel getChildRegistrySheetModel() {
        return childRegistrySheetModel;
    }

    public DefaultTableModel getCaregiverRegistrySheetModel() {
        return caregiverRegistrySheetModel;
    }

    public JTable getChildRegistrySheet() {
        return childRegistrySheet;
    }

    public JTable getCaregiverRegistrySheet() {
        return caregiverRegistrySheet;
    }

    // REQUIRES: caregiverFullName is first and last name separated by a space (case-sensitive), and caregiver exists
    //           in caregiverRegistry (!null).
    // MODIFIES: this, Registry, Caregiver
    // EFFECTS: Searches caregiver registry for caregiver with full name matching the user input (case-sensitive).
    //          Asks user to confirm they want to remove the selected caregiver. With confirmation from the user,
    //          removes caregiver from registry, and prints that caregiver was removed from the registry. Without user
    //          confirmation, returns the user to the options. Prints if entered caregiver is a primary caregiver for an
    //          existing child, and does not remove caregiver from registry.
    private void removeCaregiverFromRegistry() {
        String caregiverRemovedOrChildFullName = getController().getRegistry().removeCaregiver(caregiverName, "yes");

        if (caregiverRemovedOrChildFullName.equals("caregiver removed")) {
            System.out.println(caregiverName + " removed from the caregiver registry.\n");
        } else {
            // !!!
            System.out.println(caregiverName + " is the primary caregiver listed for "
                    + caregiverRemovedOrChildFullName + ".\n" + "Remove this child before removing their caregiver.\n");
        }
    }

    public void placeSettingsButtons() {
        JButton b1 = new JButton(ButtonNames.SAVE.getValue());

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.setLayout(new FlowLayout());
        buttonRow.setSize(WIDTH, HEIGHT / 5);
        panel0.add(buttonRow, BorderLayout.CENTER);

        b1.addActionListener(e -> {
            super.getController().saveState();
        });
    }

}
