package ui.pages;

import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import java.awt.*;

public class NewDataWindow extends Window {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 200;

    private JPanel panel0;
    private GridBagConstraints grid;

    JTextField registryField;
    JTextField attendanceSheetField;

    public NewDataWindow(AttendanceUI controller) {
        super("Enter New Data", controller);
        setSize(WIDTH, HEIGHT);

        panel0 = new JPanel();
        panel0.setBackground(new Color(220, 240, 255));
        panel0.setLayout(new GridBagLayout());
        grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.HORIZONTAL;

        placeFields();
        placeButton();

        add(panel0);
    }

    public void placeFields() {
        JLabel registryLabel = new JLabel();
        registryLabel.setText("New registry name:");
        grid.gridx = 0;
        grid.gridy = 0;
        panel0.add(registryLabel, grid);

        JLabel attendanceLabel = new JLabel();
        attendanceLabel.setText("New attendance sheet name:");
        grid.gridx = 0;
        grid.gridy = 1;
        panel0.add(attendanceLabel, grid);

        registryField = new JTextField(20);
        grid.gridx = 1;
        grid.gridwidth = 2;
        grid.gridy = 0;
        panel0.add(registryField, grid);

        attendanceSheetField = new JTextField(20);
        grid.gridx = 1;
        grid.gridwidth = 2;
        grid.gridy = 1;
        panel0.add(attendanceSheetField, grid);
    }

    public void placeButton() {
        JButton b1 = new JButton(ButtonNames.SUBMIT.getValue());

        JPanel buttonRow = new JPanel();
        buttonRow.setBackground(new Color(220, 240, 255));
        buttonRow.setLayout(new FlowLayout());
        buttonRow.add(b1);
        grid.gridx = 1;
        grid.gridy = 2;
        panel0.add(b1, grid);

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
