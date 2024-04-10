package ui.pages;

import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Creates window that allows user to add child or caregiver to registry.
public class AddNewPersonWindow extends Window {

    private final JPanel panel0;

    private final GridBagConstraints grid0;
    private GridBagConstraints grid1;

    private int width;
    private int height;

    private JLabel instruction;
    private static final String CHILD_INSTRUCTION_TEXT = "Enter data for new child.";
    private static final String CAREGIVER_INSTRUCTION_TEXT = "Enter data for new caregiver.";
    private static final String AUTHORIZED_CAREGIVER_INSTRUCTION_TEXT = "Enter data for new authorized caregiver.";

    private JTextField childNameField;
    private JTextField caregiverNameField;
    private JTextField caregiverPhoneField;
    private JTextField caregiverEmailField;

    private final DefaultTableModel caregiverRegistrySheetModel;
    private final DefaultTableModel childRegistrySheetModel;

    // REQUIRES: Arguments must exist, and personType must be either "Child" or "Caregiver" only.
    // EFFECTS: Constructs a new window that allows user to add child or caregiver to registry.
    public AddNewPersonWindow(DefaultTableModel childRegistrySheetModel, DefaultTableModel caregiverRegistrySheetModel,
                              AttendanceUI controller, String personType) {
        super("Add New Person to Registry", controller);
        this.childRegistrySheetModel = childRegistrySheetModel;
        this.caregiverRegistrySheetModel = caregiverRegistrySheetModel;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        panel0 = new JPanel();
        panel0.setLayout(new GridBagLayout());
        grid0 = new GridBagConstraints();
        grid0.fill = GridBagConstraints.HORIZONTAL;

        placeContentByPerson(personType);
    }

    // REQUIRES: personType must be either "Child" or "Caregiver" only.
    // MODIFIES: this
    // EFFECTS: Sets window size, places instruction, places fields, and places buttons in window--depending on
    //          person type.
    public void placeContentByPerson(String personType) {
        if (personType.equals("Child")) {
            width = 400;
            height = 200;
            setSize(width, height);
            placeInstruction("Child");
            placeAddChildWindowFields();
            placeAddChildWindowButtons();
        }
        if (personType.equals("Caregiver")) {
            width = 500;
            height = 250;
            setSize(width, height);
            placeInstruction("Caregiver");
            placeAddCaregiverWindowFields();
            placeAddCaregiverWindowButtons();
        }
        add(panel0);
    }

    // REQUIRES: personType must be either "Child" or "Caregiver" only.
    // MODIFIES: this
    // EFFECTS: Places instruction in window--depending on person type.
    public void placeInstruction(String personType) {
        instruction = new JLabel();
        instruction.setHorizontalAlignment(JLabel.CENTER);
        instruction.setVerticalAlignment(JLabel.CENTER);
        instruction.setSize(width, height / 3);
        grid0.gridy = 0;
        grid0.insets = new Insets(10,0,10,0);

        if (personType.equals("Child")) {
            instruction.setText(CHILD_INSTRUCTION_TEXT);
        }
        if (personType.equals("Caregiver")) {
            instruction.setText(CAREGIVER_INSTRUCTION_TEXT);
        }
        if (personType.equals("Authorized Caregiver")) {
            instruction.setText(AUTHORIZED_CAREGIVER_INSTRUCTION_TEXT);
        }

        panel0.add(instruction, grid0);
    }

    // MODIFIES: this
    // EFFECTS: Places fields in window for adding child to registry.
    public void placeAddChildWindowFields() {
        JPanel fieldRow = new JPanel(new GridBagLayout());
        grid1 = new GridBagConstraints();
        grid1.fill = GridBagConstraints.HORIZONTAL;
        fieldRow.setSize(width, height / 3);

        JLabel label1 = new JLabel("Child's full name (First Last):", JLabel.RIGHT);
        grid1.insets = new Insets(0,10,0,5);
        grid1.gridx = 0;
        grid1.gridy = 0;
        fieldRow.add(label1, grid1);

        childNameField = new JTextField(20);
        grid1.gridx = 1;
        grid1.gridwidth = 2;
        grid1.gridy = 0;
        fieldRow.add(childNameField, grid1);

        grid0.gridy = 1;
        panel0.add(fieldRow, grid0);
    }

    // MODIFIES: this, CaregiverSelectionWindow, Child
    // EFFECTS: Places buttons in window for adding child to registry.
    public void placeAddChildWindowButtons() {
        JButton b1 = new JButton(ButtonNames.SUBMIT.getValue());

        JPanel buttonRow = new JPanel(new FlowLayout());
        buttonRow.setSize(width, height / 3);
        buttonRow.add(b1);
        grid0.gridy = 2;
        panel0.add(buttonRow, grid0);

        b1.addActionListener(e -> {
            String childName = childNameField.getText();
            new CaregiverSelectionWindow(childName, childRegistrySheetModel, getController());
        });
    }

    // MODIFIES: this
    // EFFECTS: Places fields in window for adding caregiver to registry.
    @SuppressWarnings("methodlength")
    public void placeAddCaregiverWindowFields() {
        JPanel fieldRow = new JPanel(new GridBagLayout());
        grid1 = new GridBagConstraints();
        grid1.fill = GridBagConstraints.HORIZONTAL;
        fieldRow.setSize(WIDTH, HEIGHT / 2);

        JLabel caregiverNameLabel = new JLabel("Caregiver full name (First Last):", JLabel.RIGHT);
        grid1.insets = new Insets(0,10,0,5);
        grid1.gridx = 0;
        grid1.gridy = 0;
        fieldRow.add(caregiverNameLabel, grid1);

        JLabel caregiverPhoneLabel = new JLabel("Caregiver phone number (0000000000):", JLabel.RIGHT);
        grid1.insets = new Insets(0,10,0,5);
        grid1.gridx = 0;
        grid1.gridy = 1;
        fieldRow.add(caregiverPhoneLabel, grid1);

        JLabel caregiverEmailLabel = new JLabel("Caregiver email (____@___.___):", JLabel.RIGHT);
        grid1.insets = new Insets(0,10,0,5);
        grid1.gridx = 0;
        grid1.gridy = 2;
        fieldRow.add(caregiverEmailLabel, grid1);

        caregiverNameField = new JTextField(20);
        grid1.gridx = 1;
        grid1.gridwidth = 2;
        grid1.gridy = 0;
        fieldRow.add(caregiverNameField, grid1);

        caregiverPhoneField = new JTextField(20);
        grid1.gridx = 1;
        grid1.gridwidth = 2;
        grid1.gridy = 1;
        fieldRow.add(caregiverPhoneField, grid1);

        caregiverEmailField = new JTextField(20);
        grid1.gridx = 1;
        grid1.gridwidth = 2;
        grid1.gridy = 2;
        fieldRow.add(caregiverEmailField, grid1);

        grid0.gridy = 1;
        panel0.add(fieldRow, grid0);
    }

    // MODIFIES: this, Caregiver
    // EFFECTS: Places buttons in window for adding caregiver to registry.
    public void placeAddCaregiverWindowButtons() {
        JButton b1 = new JButton(ButtonNames.SUBMIT.getValue());

        JPanel buttonRow = new JPanel(new FlowLayout());
        buttonRow.setSize(width, height / 3);
        buttonRow.add(b1);
        grid0.gridy = 4;
        panel0.add(buttonRow, grid0);

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
            caregiverRegistrySheetModel.fireTableDataChanged();

            instruction.setText(caregiverName + " added to caregiver registry.");
        });
    }

}
