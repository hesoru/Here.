package ui.pages;

import model.EventLog;
import ui.AttendanceUI;
import ui.ButtonNames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

// Creates home window, where the user can choose to open the registry, attendance sheet, or save app data.
public class HomeWindow extends Window {

    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;

    private final JPanel panel0;

    private final GridBagConstraints grid0;

    private JLabel instruction;
    private static final String INSTRUCTION_TEXT = "Open attendance sheet and/or registry.";

    // REQUIRES: argument must exist (!=null)
    // EFFECTS: Constructs home window, where the user can choose to open the registry, attendance sheet, or save
    //          app data. Prints event log upon closing window.
    public HomeWindow(AttendanceUI controller) {
        super("Home", controller);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(WIDTH, HEIGHT);

        panel0 = new JPanel();
        panel0.setLayout(new GridBagLayout());
        grid0 = new GridBagConstraints();
        grid0.fill = GridBagConstraints.HORIZONTAL;

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                for (model.Event event : EventLog.getInstance()) {
                    System.out.println(event);
                }
                EventLog.getInstance().clear();
                System.exit(0);
            }
        });

        placeImage();
        placeInstruction();
        placeButtons();

        add(panel0);
    }

    // MODIFIES: this
    // EFFECTS: Places image on home window.
    public void placeImage() {
        ImageIcon image = new ImageIcon("src\\Home2.png");
        JLabel imageLabel = new JLabel(image);
        grid0.gridy = 1;
        panel0.add(imageLabel, grid0);
    }

    // MODIFIES: this
    // EFFECTS: Places instruction to open registry and/or attendance sheet.
    public void placeInstruction() {
        instruction = new JLabel(INSTRUCTION_TEXT, JLabel.CENTER);
        instruction.setSize(WIDTH, HEIGHT / 3);
        grid0.gridy = 0;
        grid0.insets = new Insets(10,0,10,0);
        panel0.add(instruction, grid0);
    }

    // MODIFIES: this, AttendanceSheetWindow, RegistryWindow, JsonWriter
    // EFFECTS: Places buttons to open registry, attendance sheet, and save app data.
    public void placeButtons() {
        JButton b1 = new JButton(ButtonNames.ATTENDANCE.getValue());
        JButton b2 = new JButton(ButtonNames.REGISTRY.getValue());
        JButton b3 = new JButton(ButtonNames.SAVE.getValue());

        JPanel buttonRow1 = new JPanel(new FlowLayout());
        buttonRow1.add(b1);
        buttonRow1.add(b2);
        buttonRow1.setSize(WIDTH, HEIGHT / 6);
        grid0.gridy = 2;
        panel0.add(buttonRow1, grid0);

        JPanel buttonRow2 = new JPanel(new FlowLayout());
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
