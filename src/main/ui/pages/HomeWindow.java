package ui.pages;

import ui.AttendanceUI;
import ui.ButtonNames;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HomeWindow extends Window {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;

    private JPanel panel0;

    private GridBagConstraints grid0;

    private static final String INSTRUCTION_TEXT = "Open attendance sheet and/or registry.";
    private JLabel instruction;

    public HomeWindow(AttendanceUI controller) {
        super("Home", controller);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        panel0 = new JPanel();
        panel0.setLayout(new GridBagLayout());
        grid0 = new GridBagConstraints();
        grid0.fill = GridBagConstraints.HORIZONTAL;

        placeImage();
        placeInstruction();
        placeButtons();

        add(panel0);
    }

    private void placeImage() {
        ImageIcon image = new ImageIcon("src\\Home2.png");
        JLabel imageLabel = new JLabel(image);
        grid0.gridy = 1;
        panel0.add(imageLabel, grid0);
    }

    private void placeInstruction() {
        instruction = new JLabel(INSTRUCTION_TEXT, JLabel.CENTER);
        instruction.setSize(WIDTH, HEIGHT / 3);
        grid0.gridy = 0;
        grid0.insets = new Insets(10,0,10,0);
        panel0.add(instruction, grid0);
    }

    //EFFECTS: creates buttons
    private void placeButtons() {
        JButton b1 = new JButton(ButtonNames.ATTENDANCE.getValue());
        JButton b2 = new JButton(ButtonNames.REGISTRY.getValue());
        JButton b3 = new JButton(ButtonNames.SAVE.getValue());

        JPanel buttonRow1 = new JPanel();
        buttonRow1.setLayout(new FlowLayout());
        buttonRow1.add(b1);
        buttonRow1.add(b2);
        buttonRow1.setSize(WIDTH, HEIGHT / 6);
        grid0.gridy = 2;
        panel0.add(buttonRow1, grid0);

        JPanel buttonRow2 = new JPanel();
        buttonRow2.setLayout(new FlowLayout());
        buttonRow2.add(b3);
        buttonRow2.setSize(WIDTH, HEIGHT / 6);
        grid0.gridy = 3;
        panel0.add(buttonRow2, grid0);

        b1.addActionListener(e -> {
            new AttendanceSheetWindow(super.getController());
        });

        b2.addActionListener(e -> {
            new RegistryWindow(super.getController());
        });

        b3.addActionListener(e -> {
            super.getController().saveState();
        });
    }
}
