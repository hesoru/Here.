package ui.pages;

import model.Caregiver;
import model.Child;
import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RegistryTab extends Tab {

    JPanel panel0;
    GridBagConstraints grid;

    DefaultTableModel childRegistrySheetModel;
    DefaultTableModel caregiverRegistrySheetModel;

    JTable childRegistrySheet;
    JTable caregiverRegistrySheet;

    String INIT_DIALOGUE = "Add a child to the registry.";
    String childToRemoveName;
    String caregiverName;
    String primaryCaregiverName;

    JFrame addWindow;
    JFrame selectCaregiverWindow;

    JTextField childNameField;
    JTextField caregiverNameField;
    JTextField caregiverPhoneField;
    JTextField caregiverEmailField;

    JLabel dialogueAddChild;
    JLabel dialogueAddCaregiver;

    public RegistryTab(AttendanceUI controller, String registryType) {
        super(controller);

        panel0 = new JPanel();
        panel0.setBackground(new Color(220, 240, 255));
        panel0.setLayout(new GridBagLayout());
        grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.HORIZONTAL;

        if (registryType.equals("ChildRegistry")) {
            placeChildRegistrySheet();
            placeChildRegistryButtons();
        }
        if (registryType.equals("CaregiverRegistry")) {
            placeCaregiverRegistrySheet();
            placeCaregiverRegistryButtons();
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
            o[2] = c.getPrimaryCaregiver().getEmail();
            o[3] = c.getPrimaryCaregiver().getPhoneNum();
            childRegistrySheetModel.addRow(o);
        }

        childRegistrySheet = new JTable(childRegistrySheetModel);

        JScrollPane scrollPane = new JScrollPane(childRegistrySheet);
        grid.gridx = 0;
        grid.gridwidth = 5;
        grid.gridy = 0;
        grid.gridheight = 4;
        childRegistrySheet.setFillsViewportHeight(true);
        panel0.add(scrollPane, grid);
    }

    public void placeChildRegistryButtons() {
        JButton b1 = new JButton(ButtonNames.ADD_CHILD.getValue());
        JButton b2 = new JButton(ButtonNames.REMOVE_CHILD.getValue());

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.setBackground(new Color(220, 240, 255));
        buttonRow.setLayout(new FlowLayout());
        grid.gridy = 4;

        b1.addActionListener(e -> {
            addWindow("childRegistrySheetModel");
            childRegistrySheetModel.fireTableDataChanged();
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

        panel0.add(buttonRow, grid);
    }

    public void addWindow(String tableModelName) {
        addWindow = new JFrame("Add New Person to Registry");
        addWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindow.setResizable(false);
        addWindow.setSize(WIDTH, HEIGHT);
        addWindow.setVisible(true);
        addWindow.setBackground(new Color(220, 240, 255));
        ImageIcon icon = new ImageIcon("src\\App_Icon.png");
        addWindow.setIconImage(icon.getImage());

        panel0 = new JPanel();
        panel0.setBackground(new Color(220, 240, 255));
        panel0.setLayout(new GridBagLayout());
        grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.HORIZONTAL;

        if (tableModelName.equals("childRegistrySheetModel")) {
            placeAddChildWindowFields();
            placeAddChildWindowButtons();
        }
        if (tableModelName.equals("caregiverRegistrySheetModel")) {
            placeAddCaregiverWindowFields();
            placeAddCaregiverWindowButtons();
        }

        addWindow.add(panel0);
    }

    public void placeAddChildWindowFields() {
        dialogueAddChild = new JLabel();
        dialogueAddChild.setText("Please enter data for new child.");

        JLabel label1 = new JLabel();
        label1.setText("New child's full name (First Last):");
        grid.gridx = 0;
        grid.gridy = 1;
        panel0.add(label1, grid);

        childNameField = new JTextField(20);
        grid.gridx = 1;
        grid.gridwidth = 2;
        grid.gridy = 1;
        panel0.add(childNameField, grid);
    }

    public void placeAddCaregiverWindowFields() {
        dialogueAddCaregiver = new JLabel();
        dialogueAddCaregiver.setText("Please enter data for new caregiver.");

        JLabel label1 = new JLabel();
        label1.setText("New caregiver's full name (First Last):");
        grid.gridx = 0;
        grid.gridy = 1;
        panel0.add(label1, grid);

        JLabel label2 = new JLabel();
        label2.setText("New caregiver's phone number (XXXXXXXXXX):");
        grid.gridx = 0;
        grid.gridy = 2;
        panel0.add(label2, grid);

        JLabel label3 = new JLabel();
        label3.setText("New caregiver's email (____@___.___):");
        grid.gridx = 0;
        grid.gridy = 3;
        panel0.add(label3, grid);

        caregiverNameField = new JTextField(20);
        grid.gridx = 1;
        grid.gridwidth = 2;
        grid.gridy = 1;
        panel0.add(caregiverNameField, grid);

        caregiverPhoneField = new JTextField(20);
        grid.gridx = 1;
        grid.gridwidth = 2;
        grid.gridy = 2;
        panel0.add(caregiverPhoneField, grid);

        caregiverEmailField = new JTextField(20);
        grid.gridx = 1;
        grid.gridwidth = 2;
        grid.gridy = 3;
        panel0.add(caregiverEmailField, grid);
    }

    public void placeAddChildWindowButtons() {
        JButton b1 = new JButton(ButtonNames.SUBMIT.getValue());

        JPanel buttonRow = new JPanel();
        buttonRow.setBackground(new Color(220, 240, 255));
        buttonRow.setLayout(new FlowLayout());
        buttonRow.add(b1);

        grid.gridy = 2;
        panel0.add(buttonRow, grid);

        b1.addActionListener(e -> {
            String childName = childNameField.getText();
            selectCaregiverWindow();
            Child child = getController().getRegistry().addNewChild(childName, primaryCaregiverName);

            getController().getAttendanceSheet().notCheckedIn(child);
            dialogueAddChild.setText(child.getFullName() + " added to child registry.\n"
                    + child.getFullName() + " is not checked in.\n");
        });
    }

    public void selectCaregiverWindow() {
        selectCaregiverWindow = new JFrame("Select Primary Caregiver");
        selectCaregiverWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectCaregiverWindow.setResizable(false);
        selectCaregiverWindow.setSize(WIDTH, HEIGHT);
        selectCaregiverWindow.setVisible(true);
        selectCaregiverWindow.setBackground(new Color(220, 240, 255));
        ImageIcon icon = new ImageIcon("src\\App_Icon.png");
        selectCaregiverWindow.setIconImage(icon.getImage());

        panel0 = new JPanel();
        panel0.setBackground(new Color(220, 240, 255));
        panel0.setLayout(new GridBagLayout());
        grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.HORIZONTAL;

        placeCaregiverRegistrySheet();
        placeSelectCaregiverWindowButtons();

        selectCaregiverWindow.add(panel0);
    }

    public void placeSelectCaregiverWindowButtons() {
        JButton b1 = new JButton(ButtonNames.ADD_CAREGIVER.getValue());
        JButton b2 = new JButton(ButtonNames.SELECT_CAREGIVER.getValue());

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.setBackground(new Color(220, 240, 255));
        buttonRow.setLayout(new FlowLayout());
        grid.gridy = 4;

        b1.addActionListener(e -> {
            addWindow("caregiverRegistrySheetModel");
            // insert at end of addwindow function?
            caregiverRegistrySheetModel.fireTableDataChanged();
        });

        b2.addActionListener(e -> {
            int selected = caregiverRegistrySheet.getSelectedRow();

            if (selected != -1) {
                primaryCaregiverName = caregiverRegistrySheetModel.getValueAt(selected, 0).toString();
            }
        });
        panel0.add(buttonRow, grid);
    }

    public void placeAddCaregiverWindowButtons() {
        JButton b1 = new JButton(ButtonNames.SUBMIT.getValue());

        JPanel buttonRow = new JPanel();
        buttonRow.setBackground(new Color(220, 240, 255));
        buttonRow.setLayout(new FlowLayout());
        buttonRow.add(b1);

        grid.gridy = 4;
        panel0.add(buttonRow, grid);

        b1.addActionListener(e -> {
            String caregiverName = caregiverNameField.getText();
            Long caregiverPhone = Long.valueOf(caregiverPhoneField.getText());
            String caregiverEmail = caregiverEmailField.getText();

            getController().getRegistry().addNewCaregiver(caregiverName, caregiverPhone, caregiverEmail);

            Object[] caregiverData = new Object[3];
            caregiverData[0] = caregiverName;
            caregiverData[1] = caregiverPhone;
            caregiverData[2] = caregiverEmail;
            caregiverRegistrySheetModel.addRow(caregiverData);

            dialogueAddCaregiver.setText(caregiverName + " added to caregiver registry.");
        });
    }

    public void placeCaregiverRegistrySheet() {
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

        caregiverRegistrySheet = new JTable(caregiverRegistrySheetModel);

        JScrollPane scrollPane = new JScrollPane(caregiverRegistrySheet);
        grid.gridx = 0;
        grid.gridwidth = 5;
        grid.gridy = 0;
        grid.gridheight = 4;
        caregiverRegistrySheet.setFillsViewportHeight(true);
        panel0.add(scrollPane, grid);
    }

    public void placeCaregiverRegistryButtons() {
        JButton b1 = new JButton(ButtonNames.ADD_CAREGIVER.getValue());
        JButton b2 = new JButton(ButtonNames.REMOVE_CAREGIVER.getValue());

        JPanel buttonRow = formatButtonRow(b1);
        buttonRow.add(b2);
        buttonRow.setBackground(new Color(220, 240, 255));
        buttonRow.setLayout(new FlowLayout());
        grid.gridy = 4;

        b1.addActionListener(e -> {
            addWindow("caregiverRegistrySheetModel");
            // insert at end of addwindow function?
            caregiverRegistrySheetModel.fireTableDataChanged();
        });

        b2.addActionListener(e -> {
            int selected = caregiverRegistrySheet.getSelectedRow();

            if (selected != -1) {
                caregiverName = caregiverRegistrySheetModel.getValueAt(selected, 0).toString();
                removeCaregiverFromRegistry();
                caregiverRegistrySheetModel.removeRow(selected);
                caregiverRegistrySheetModel.fireTableDataChanged();
                JOptionPane.showMessageDialog(null, primaryCaregiverName + " removed successfully.");
            }
        });

        panel0.add(buttonRow, grid);
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

}
