package ui.pages;

import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import java.awt.*;

// Creates window that allows user to name new registry and attendance sheet
public class NewDataWindow extends Window {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 200;

    private final JPanel panel0;
    private final GridBagConstraints grid0;
    private GridBagConstraints grid1;

    private JLabel instruction;
    private static final String INSTRUCTION_TEXT = "Name your new registry and attendance sheet.";

    private JTextField registryField;
    private JTextField attendanceSheetField;

    // REQUIRES: Argument must exist (!=null)
    // EFFECTS: Constructs window that allows user to name new registry and attendance sheet
    public NewDataWindow(AttendanceUI controller) {
        super("New Registry and Attendance Sheet", controller);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        panel0 = new JPanel();
        panel0.setLayout(new GridBagLayout());
        grid0 = new GridBagConstraints();
        grid0.fill = GridBagConstraints.HORIZONTAL;

        placeInstruction();
        placeFields();
        placeButton();

        add(panel0);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: Places instruction to enter new name for registry and attendance sheet.
    private void placeInstruction() {
        instruction = new JLabel(INSTRUCTION_TEXT, JLabel.CENTER);
        instruction.setVerticalAlignment(JLabel.CENTER);
        instruction.setSize(WIDTH, HEIGHT / 3);
        grid0.gridy = 0;
        grid0.insets = new Insets(10,0,10,0);
        panel0.add(instruction, grid0);
    }

    // MODIFIES: this
    // EFFECTS: Places fields to enter new names for registry and attendance sheet.
    @SuppressWarnings("methodlength")
    public void placeFields() {
        JPanel fieldRow = new JPanel();
        fieldRow.setLayout(new GridBagLayout());
        grid1 = new GridBagConstraints();
        grid1.fill = GridBagConstraints.HORIZONTAL;
        fieldRow.setSize(WIDTH, HEIGHT / 3);

        JLabel registryLabel = new JLabel("Registry name:", JLabel.RIGHT);
        grid1.insets = new Insets(0,10,0,5);
        grid1.gridx = 0;
        grid1.gridy = 0;
        fieldRow.add(registryLabel, grid1);

        JLabel attendanceLabel = new JLabel("Attendance sheet name:", JLabel.RIGHT);
        grid1.insets = new Insets(0,10,0,5);
        grid1.gridx = 0;
        grid1.gridy = 1;
        fieldRow.add(attendanceLabel, grid1);

        registryField = new JTextField(20);
        grid1.gridx = 1;
        grid1.gridwidth = 2;
        grid1.gridy = 0;
        fieldRow.add(registryField, grid1);

        attendanceSheetField = new JTextField(20);
        grid1.gridx = 1;
        grid1.gridwidth = 2;
        grid1.gridy = 1;
        fieldRow.add(attendanceSheetField, grid1);

        grid0.gridy = 1;
        panel0.add(fieldRow, grid0);
    }

    // MODIFIES: this, AttendanceSheet, Registry, HomeWindow
    // EFFECTS: Places button to submit names for new attendance sheet and registry. Creates new attendance sheet and
    //          registry using given data from fields, and opens a new HomeWindow.
    public void placeButton() {
        JButton b1 = new JButton(ButtonNames.SUBMIT.getValue());

        JPanel buttonRow = new JPanel();
        buttonRow.setLayout(new FlowLayout());
        buttonRow.setSize(WIDTH, HEIGHT / 3);
        buttonRow.add(b1);
        grid0.gridy = 2;
        panel0.add(buttonRow, grid0);

        b1.addActionListener(e -> {
            String registryName = registryField.getText();
            String attendanceSheetName = attendanceSheetField.getText();
            super.getController().createRegistry(registryName);
            super.getController().createAttendanceSheet(attendanceSheetName);
            new HomeWindow(super.getController());
            this.dispose();
        });
    }
}
