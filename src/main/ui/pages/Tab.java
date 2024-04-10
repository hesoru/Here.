package ui.pages;

import ui.AttendanceUI;

import javax.swing.*;
import java.awt.*;

// Abstract class that constructs tabs with an AttendanceUI controller
public abstract class Tab extends JPanel {

    private final AttendanceUI controller;

    // REQUIRES: controller exists (!=null)
    // EFFECTS: Constructs tab with an AttendanceUI controller
    public Tab(AttendanceUI controller) {
        this.controller = controller;
    }

    // MODIFIES: this
    // EFFECTS: Creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    // EFFECTS: returns the AttendanceUI controller for this tab
    public AttendanceUI getController() {
        return controller;
    }
}
