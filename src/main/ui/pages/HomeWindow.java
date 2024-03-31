package ui.pages;

import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import java.awt.*;

public class HomeWindow extends Window {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 150;

    private static final String INSTRUCTION_TEXT = "Open attendance sheet and/or registry.";
    private JLabel instruction;

    public HomeWindow(AttendanceUI controller) {
        super("Home", controller);
        setSize(WIDTH, HEIGHT);

        setLayout(new GridLayout(3, 1));

        placeInstruction();
        placeButtons();
    }

    private void placeInstruction() {
        instruction = new JLabel(INSTRUCTION_TEXT, JLabel.CENTER);
        instruction.setSize(WIDTH, HEIGHT / 3);
        add(instruction);
    }

    //EFFECTS: creates buttons
    private void placeButtons() {
        JButton b1 = new JButton(ButtonNames.ATTENDANCE.getValue());
        JButton b2 = new JButton(ButtonNames.REGISTRY.getValue());
        JButton b3 = new JButton(ButtonNames.SAVE.getValue());

        JPanel buttonRow1 = new JPanel();
        buttonRow1.setBackground(new Color(220, 240, 255));
        buttonRow1.setLayout(new FlowLayout());
        buttonRow1.add(b1);
        buttonRow1.add(b2);
        buttonRow1.setSize(WIDTH, HEIGHT / 6);

        JPanel buttonRow2 = new JPanel();
        buttonRow2.setBackground(new Color(220, 240, 255));
        buttonRow2.setLayout(new FlowLayout());
        buttonRow2.add(b3);
        buttonRow2.setSize(WIDTH, HEIGHT / 6);

        b1.addActionListener(e -> {
            new AttendanceSheetWindow(super.getController());
        });

        b2.addActionListener(e -> {
            new RegistryWindow(super.getController());
        });

        b3.addActionListener(e -> {
            super.getController().saveState();
        });

        add(buttonRow1);
        add(buttonRow2);
    }
}
