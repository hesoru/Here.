package ui.pages;

import model.Caregiver;
import model.Child;
import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

// Creates window that allows user to select authorized caregiver picking up a child
public class SelectAuthorizedCaregiverWindow extends Window {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 525;

    private final JPanel panel0;

    private final AttendanceSheetWindow attendanceSheetWindow;

    private JLabel instruction;
    private static final String INSTRUCTION_TEXT = "Select the authorized caregiver picking up the child.";

    private DefaultTableModel authorizedCaregiverSheetModel;
    private JTable authorizedCaregiverSheet;
    private String caregiverToCheckOutName;

    // REQUIRES: Arguments must exist (!=null), rowToRemove must be an existing row index
    // EFFECTS: Constructs window that allows user to select authorized caregiver picking up a child
    public SelectAuthorizedCaregiverWindow(AttendanceSheetWindow attendanceSheetWindow,
                                           AttendanceSheetTab attendanceSheetTab, AttendanceUI controller,
                                           int rowToRemove) {
        super("Select Authorized Caregiver", controller);
        this.attendanceSheetWindow = attendanceSheetWindow;
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);

        panel0 = new JPanel(new BorderLayout());

        placeInstruction();
        placeAuthorizedCaregiverSheet(attendanceSheetTab.getChildToCheckOut());
        placeAuthorizedCaregiverButtons(attendanceSheetTab.getChildToCheckOut(), rowToRemove);

        add(panel0);
    }

    // MODIFIES: this
    // EFFECTS: Places instruction to select authorized caregiver.
    private void placeInstruction() {
        instruction = new JLabel(INSTRUCTION_TEXT, JLabel.CENTER);
        instruction.setVerticalAlignment(JLabel.CENTER);
        instruction.setSize(WIDTH, HEIGHT / 5);
        panel0.add(instruction, BorderLayout.NORTH);
    }

    // REQUIRES: child must exist in child registry
    // MODIFIES: this
    // EFFECTS: Populates authorized caregiver table model for the given child and creates table
    public void placeAuthorizedCaregiverSheet(Child child) {
        authorizedCaregiverSheetModel = new DefaultTableModel(0, 3);
        Object[] columnNames = {"Authorized To Pick Up", "Phone", "Email"};
        authorizedCaregiverSheetModel.setColumnIdentifiers(columnNames);
        for (Caregiver c : child.getAuthorizedToPickUp()) {
            Object[] o = new Object[3];
            o[0] = c.getFullName();
            o[1] = c.getPhoneNum();
            o[2] = c.getEmail();
            authorizedCaregiverSheetModel.addRow(o);
        }
//        attendanceSheetWindow.setAuthorizedCaregiverSheetModel(authorizedCaregiverSheetModel);
        authorizedCaregiverSheet = new JTable(authorizedCaregiverSheetModel) {
            public boolean getScrollableTracksViewportWidth() {
                return true;
            }
        };

        authorizedCaregiverSheet.setAutoCreateRowSorter(true);
        authorizedCaregiverSheet.setRowHeight(30);
        authorizedCaregiverSheet.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(authorizedCaregiverSheet,
                ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panel0.add(scrollPane, BorderLayout.CENTER);
    }

    // REQUIRES: child must exist in the child registry, and rowToRemove must be an existing row index
    // MODIFIES: this, Child, AttendanceSheet
    // EFFECTS: Places button that allows user to select authorized caregiver picking up child: checks out child, and
    //          updates checkedIn and checkedOut attendance sheets.
    public void placeAuthorizedCaregiverButtons(Child child, int rowToRemove) {
        JButton b1 = new JButton(ButtonNames.SELECT_PICKUP.getValue());

        JPanel buttonRow = new JPanel(new FlowLayout());
        buttonRow.add(b1);
        buttonRow.setSize(WIDTH, HEIGHT / 5);
        panel0.add(buttonRow, BorderLayout.SOUTH);

        b1.addActionListener(e -> {
            int selected = authorizedCaregiverSheet.getSelectedRow();

            if (selected != -1) {
                caregiverToCheckOutName = authorizedCaregiverSheetModel.getValueAt(selected, 0).toString();
                attendanceSheetWindow.checkOutChild(child.getFullName(), caregiverToCheckOutName);

                Object[] rowData = new Object[3];
                rowData[0] = child.getFullName();
                rowData[1] = child.getCheckOutTime();
                rowData[2] = caregiverToCheckOutName;

                attendanceSheetWindow.getCheckedOutSheetModel().addRow(rowData);
                attendanceSheetWindow.getCheckedInSheetModel().removeRow(rowToRemove);

                attendanceSheetWindow.getCheckedInSheetModel().fireTableDataChanged();
                attendanceSheetWindow.getCheckedOutSheetModel().fireTableDataChanged();

                JOptionPane.showMessageDialog(null, child.getFullName() + " checked out at "
                        + child.getCheckOutTime() + " by " + caregiverToCheckOutName);
            }
        });
    }

//    public void addNewAuthorizedCaregiverWindow() {
//        JFrame addNewAuthorizedCaregiverWindow = new JFrame("Add New Authorized Caregiver");
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//
//        panel1 = new JPanel();
//        panel1.setLayout(new GridBagLayout());
//        grid0 = new GridBagConstraints();
//        grid0.fill = GridBagConstraints.HORIZONTAL;
//
//        addNewAuthorizedCaregiverWindow.setSize(WIDTH2, HEIGHT2);
//        placeNewAuthorizedCaregiverInstruction();
//        placeNewAuthorizedCaregiverWindowFields();
//        placeNewAuthorizedCaregiverWindowButtons();
//
//        addNewAuthorizedCaregiverWindow.add(panel1);
//    }
//
//    public void placeNewAuthorizedCaregiverInstruction() {
//        instruction = new JLabel(CAREGIVER_INSTRUCTION_TEXT);
//        instruction.setHorizontalAlignment(JLabel.CENTER);
//        instruction.setVerticalAlignment(JLabel.CENTER);
//        instruction.setSize(WIDTH2, HEIGHT2 / 3);
//        grid0.gridy = 0;
//        grid0.insets = new Insets(10,0,10,0);
//        panel1.add(instruction, grid0);
//    }
//
//    public void placeNewAuthorizedCaregiverWindowFields() {
//        JPanel fieldRow = new JPanel(new GridBagLayout());
//        grid1 = new GridBagConstraints();
//        grid1.fill = GridBagConstraints.HORIZONTAL;
//        fieldRow.setSize(WIDTH2, HEIGHT2 / 2);
//
//        JLabel caregiverNameLabel = new JLabel("Caregiver full name (First Last):", JLabel.RIGHT);
//        grid1.insets = new Insets(0,10,0,5);
//        grid1.gridx = 0;
//        grid1.gridy = 0;
//        fieldRow.add(caregiverNameLabel, grid1);
//
//        JLabel caregiverPhoneLabel = new JLabel("Caregiver phone number (0000000000):", JLabel.RIGHT);
//        grid1.insets = new Insets(0,10,0,5);
//        grid1.gridx = 0;
//        grid1.gridy = 1;
//        fieldRow.add(caregiverPhoneLabel, grid1);
//
//        JLabel caregiverEmailLabel = new JLabel("Caregiver email (____@___.___):", JLabel.RIGHT);
//        grid1.insets = new Insets(0,10,0,5);
//        grid1.gridx = 0;
//        grid1.gridy = 2;
//        fieldRow.add(caregiverEmailLabel, grid1);
//
//        caregiverNameField = new JTextField(20);
//        grid1.gridx = 1;
//        grid1.gridwidth = 2;
//        grid1.gridy = 0;
//        fieldRow.add(caregiverNameField, grid1);
//
//        caregiverPhoneField = new JTextField(20);
//        grid1.gridx = 1;
//        grid1.gridwidth = 2;
//        grid1.gridy = 1;
//        fieldRow.add(caregiverPhoneField, grid1);
//
//        caregiverEmailField = new JTextField(20);
//        grid1.gridx = 1;
//        grid1.gridwidth = 2;
//        grid1.gridy = 2;
//        fieldRow.add(caregiverEmailField, grid1);
//
//        grid0.gridy = 1;
//        panel1.add(fieldRow, grid0);
//    }
//
//    public void placeNewAuthorizedCaregiverWindowButtons() {
//        JButton b1 = new JButton(ButtonNames.SUBMIT.getValue());
//
//        JPanel buttonRow = new JPanel(new FlowLayout());
//        buttonRow.setSize(WIDTH2, HEIGHT2 / 3);
//        buttonRow.add(b1);
//        grid0.gridy = 4;
//        panel1.add(buttonRow, grid0);
//
//        b1.addActionListener(e -> {
//            String caregiverName = caregiverNameField.getText();
//            Long caregiverPhone = Long.valueOf(caregiverPhoneField.getText());
//            String caregiverEmail = caregiverEmailField.getText();
//
//            getController().getRegistry().addNewCaregiver(caregiverName, caregiverPhone, caregiverEmail);
//
//            Object[] caregiverData = new Object[3];
//            caregiverData[0] = caregiverName;
//            caregiverData[1] = caregiverPhone;
//            caregiverData[2] = caregiverEmail;
//            authorizedCaregiverSheetModel.addRow(caregiverData);
//            authorizedCaregiverSheetModel.fireTableDataChanged();
//
//            instruction.setText(caregiverName + " added to caregiver registry.");
//        });
//    }

}
