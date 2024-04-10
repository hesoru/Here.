package ui.pages;

import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import java.awt.*;

// Creates password-protected login window for attendance application
public class LoginWindow extends Window {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 300;
    public static final String PASSWORD = "demo";

    private static final String INSTRUCTION_TEXT = "Login";
    private JLabel instruction;

    private final JPanel panel0;
    private final GridBagConstraints grid0;

    private JTextField passwordField;

    // REQUIRES: argument must exist (!=null)
    // EFFECTS: Constructs password-protected login window
    public LoginWindow(AttendanceUI controller) {
        super("Login", controller);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        panel0 = new JPanel();
        panel0.setLayout(new GridBagLayout());
        grid0 = new GridBagConstraints();
        grid0.fill = GridBagConstraints.HORIZONTAL;

        placeInstruction();
        placeField();
        placeButton();

        add(panel0);
        pack();
    }

    // MODIFIES: this
    // EFFECTS: Places instruction to login.
    private void placeInstruction() {
        instruction = new JLabel(INSTRUCTION_TEXT, JLabel.CENTER);
        instruction.setVerticalAlignment(JLabel.CENTER);
        instruction.setSize(WIDTH, HEIGHT / 3);
        grid0.gridy = 0;
        grid0.insets = new Insets(10,0,10,0);
        panel0.add(instruction, grid0);
    }

    // MODIFIES: this
    // EFFECTS: Places password field.
    public void placeField() {
        JPanel fieldRow = new JPanel();
        fieldRow.setLayout(new FlowLayout());
        fieldRow.setSize(WIDTH, HEIGHT / 3);

        JLabel label = new JLabel("Password", JLabel.RIGHT);
        label.setHorizontalAlignment(JLabel.CENTER);

        passwordField = new JPasswordField(20);

        fieldRow.add(label);
        fieldRow.add(passwordField);
        grid0.gridy = 1;
        panel0.add(fieldRow, grid0);
    }

    // MODIFIES: this, ChooseDataWindow
    // EFFECTS: Places button to submit password. If password is correct, opens new ChooseDataWindow. If password is
    //          incorrect, changes instruction to tell user that password is incorrect.
    public void placeButton() {
        JButton b1 = new JButton(ButtonNames.SUBMIT.getValue());

        JPanel buttonRow = new JPanel();
        buttonRow.setLayout(new FlowLayout());
        buttonRow.setSize(WIDTH, HEIGHT / 3);
        buttonRow.add(b1);
        grid0.gridy = 2;
        panel0.add(buttonRow, grid0);

        b1.addActionListener(e -> {
            String passInput = passwordField.getText();

            if (passInput.equals(PASSWORD)) {
                new ChooseDataWindow(super.getController());
                this.dispose();
            } else {
                instruction.setText("Password incorrect! Please try again.");
            }
        });
    }
}
