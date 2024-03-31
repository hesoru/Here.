package ui.pages;

import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import java.awt.*;

public class ChooseDataWindow extends Window {

    public static final int WIDTH = 500;
    public static final int HEIGHT = 150;

    private static final String INSTRUCTION_TEXT = "Select whether to load app data from an existing file or create new app data.";
    private JLabel instruction;

    public ChooseDataWindow(AttendanceUI controller) {
        super("Load Data", controller);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);
        setVisible(true);

        setLayout(new GridLayout(2, 1));

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
        JButton b1 = new JButton(ButtonNames.LOAD.getValue());
        JButton b2 = new JButton(ButtonNames.NEW.getValue());

        JPanel buttonRow = new JPanel();
        buttonRow.setLayout(new FlowLayout());
        buttonRow.add(b1);
        buttonRow.add(b2);
        buttonRow.setSize(WIDTH, HEIGHT / 6);

        super.getController().createJsonWriter();
        super.getController().createJsonReader();

        b1.addActionListener(e -> {
            super.getController().loadState();
            new HomeWindow(super.getController());
            this.dispose();
        });

        b2.addActionListener(e -> {
            new NewDataWindow(super.getController());
            this.dispose();
        });

        this.add(buttonRow);
    }
}
