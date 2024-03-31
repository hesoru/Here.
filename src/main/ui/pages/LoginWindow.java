package ui.pages;

import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends Window {

    public static final int WIDTH = 300;
    public static final int HEIGHT = 150;
    public static final String PASSWORD = "demo";

    private JPanel panel0;
    private GridBagConstraints grid0;

    private JLabel label;
    private JTextField passwordField;

    public LoginWindow(AttendanceUI controller) {
        super("Login", controller);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        panel0 = new JPanel();
        panel0.setLayout(new GridBagLayout());
        grid0 = new GridBagConstraints();
        grid0.fill = GridBagConstraints.HORIZONTAL;

        placeField();
        placeButton();

        add(panel0);
    }

    public void placeField() {
        JPanel fieldRow = new JPanel();
        fieldRow.setLayout(new FlowLayout());
        fieldRow.setSize(WIDTH, HEIGHT / 2);

        label = new JLabel();
        label.setText("Password");
        label.setHorizontalAlignment(JLabel.CENTER);

        passwordField = new JPasswordField(20);

        fieldRow.add(label);
        fieldRow.add(passwordField);
        panel0.add(fieldRow);
    }

    public void placeButton() {
        JButton b1 = new JButton(ButtonNames.SUBMIT.getValue());

        JPanel buttonRow = new JPanel();
        buttonRow.setLayout(new FlowLayout());
        buttonRow.setSize(WIDTH, HEIGHT / 2);
        buttonRow.add(b1);
        grid0.gridy = 1;
        panel0.add(buttonRow, grid0);

        b1.addActionListener(e -> {
            String passInput = passwordField.getText();

            if (passInput.equals(PASSWORD)) {
                new ChooseDataWindow(super.getController());
                this.dispose();
            } else {
                label.setText("Incorrect!");
            }
        });
    }
}
