package ui.pages;

import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import java.awt.*;

public class LoginWindow extends Window {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 200;
    public static final String PASSWORD = "demo";

    private JPanel panel0;
    private GridBagConstraints grid;

    private JLabel label;
    private JTextField passwordField;

    public LoginWindow(AttendanceUI controller) {
        super("Login", controller);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        getContentPane().setBackground(new Color(220, 240, 255));
        setLayout(null);
        ImageIcon icon = new ImageIcon("src\\App_Icon.png");
        setIconImage(icon.getImage());

        panel0 = new JPanel();
        panel0.setBackground(new Color(220, 240, 255));
        panel0.setLayout(new GridBagLayout());
        grid = new GridBagConstraints();
        grid.fill = GridBagConstraints.HORIZONTAL;

        placeField();
        placeButton();

        add(panel0);
    }

    public void placeField() {
        label = new JLabel();
        label.setText("Password");

        passwordField = new JPasswordField(20);

        JPanel fieldRow = new JPanel();
        fieldRow.setBackground(new Color(220, 240, 255));
        fieldRow.setLayout(new FlowLayout());
        fieldRow.setSize(WIDTH, HEIGHT / 3);
        fieldRow.add(label);
        fieldRow.add(passwordField);
        grid.gridx = 0;
        grid.gridy = 0;
        panel0.add(fieldRow, grid);
    }

    public void placeButton() {
        JButton b1 = new JButton(ButtonNames.SUBMIT.getValue());

        JPanel buttonRow = new JPanel();
        buttonRow.setBackground(new Color(220, 240, 255));
        buttonRow.setLayout(new FlowLayout());
        buttonRow.setSize(WIDTH, HEIGHT / 3);
        buttonRow.add(b1);
        grid.gridx = 0;
        grid.gridy = 1;
        panel0.add(buttonRow, grid);

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
